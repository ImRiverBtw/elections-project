<template>

  <div class="top">

    <h2 class="topTitle"> Meerderheidszoeker </h2>
    <hr class="title-seperator">

    <p class="topInstruction"> Klik op de partijen en bekijk de mogelijke formaties om een kabinet <br>
      te vormen met een meerderheid in de tweede kamer</p>
  </div>

  <div class="content">

    <div class="graphWrapper">

      <div class="chart-container">
        <svg :width=400 :height=200 viewBox="0 0 200 100">
          <!-- Background semi-circle -->
          <path
              d="M10,100 A90,90 0 0,1 190,100"
              fill="#333"
          ></path>
          <!-- Dynamic sections for selected parties -->
          <path
              v-for="(party, index) in this.selectedParties"
              :key="party.name"
              :d="calculateArcPath(index)"
              :fill="party.color"
          ></path>
          <!-- Majority line -->
          <line x1="100" y1="100" x2="100" y2="10" stroke="white" stroke-width="1"></line>
        </svg>
        </div>


      <h2 id="seatsCounter"> {{ this.totalAccumulatedSeats }} Zetels</h2>

    </div>

    <div class="graphFooter">

        <p id="graphFooterTitle"> Formatie: </p>

        <div class="activatedPartiesWrapper">
<!--          makes a "tag" for every party in activatedParties array -->
          <p class="activatedParty" v-for="party in selectedParties"
             :key="party.id"
             :style="{ backgroundColor: party.color }"
          > {{ party.name }} ({{ party.seatAmount }}) </p>

        </div>

    </div>

    <div class="bottomWrapper">

      <div class="top" id="bottomWrapperTop">
        <h2 class="topTitle"> Partijen </h2>
        <hr class="title-seperator" id="title-seperator1">
      </div>

      <div class="tagsWrapper">

<!--        makes a party tag for every party in the parties array and binds click method to it when it is clicked -->
        <PartyTag class="partyTag"
                  v-for="party in parties"
                  @click="handlePartyTagClick(party);"
                  :class="{active: party.selected}"
        > <p class="partyName"> {{ party.name }} </p>
        </PartyTag>

        <button class="resetButton" @click="handleResetButtonClick"> Reset </button>

      </div>
    </div>
  </div>

</template>

<script>
import PartyTag from "@/components/PartyTag.vue";

export default {
  name: "MajorityCalulator",
  components: {PartyTag},

  async created() {
    await this.fetchAffiliations();
  },

  data() {

    return {

      // maxSeats: 150,

      totalAccumulatedSeats: 0,

      selectedParties: [],

      defaultTagColor: "#d4dfef",

      parties: [],

    }
  },

  methods: {

    // method that handles clicking on a tag by the user //
    handlePartyTagClick(party) {

      // check if the party should be selected or deselected //
      if (this.selectedParties.includes(party)) {

        this.deselectParty(party)

      } else {

        this.selectParty(party)
      }
    },

    // method that selects the clicked party //
    selectParty(party) {
      console.log(party)

      // add up the seat amount of the selected party to the total accumulated seats //
      this.totalAccumulatedSeats = this.totalAccumulatedSeats + party.seatAmount;

      // put the party in the selected parties array //
      this.selectedParties.push(party)

      // change the "selected" attribute of the party (to change the background of the tag) //
      party.selected = !party.selected;
    },

    // method that deselects the clicked party //
    deselectParty(party) {

      // subtract the seat amount of the selected party from the total accumulated seats //
      this.totalAccumulatedSeats = this.totalAccumulatedSeats - party.seatAmount;

      // remove the party from the selected parties array //
      this.selectedParties = this.selectedParties.filter(
          activeParty => activeParty.id !== party.id
      );

      // change the "selected" attribute of the party (to change the background of the tag) //
      party.selected = !party.selected;

    },

    handleResetButtonClick() {

      this.selectedParties = [];
      this.totalAccumulatedSeats = 0;

      for (let i = 0; i < this.parties.length; i++) {

        this.parties[i].selected = false;
      }
    },

    // Get a list of parties with their name and id
    async fetchAffiliations() {
      try {
        const response = await fetch('http://localhost:8080/electionresult/affiliation');
        if (!response.ok) {
          throw new Error(response.status);
        }
        const affiliations = await response.json();
        this.affiliations = affiliations.map(aff => ({ ...aff, seats: 0 }));

        // get the amount of seats per party //
        for (let i = 0; i < this.affiliations.length; i++) {

          let party = {
            id: this.affiliations[i].id,
            name: this.affiliations[i].name,
            seatAmount: await this.fetchSeatCountById(this.affiliations[i].id),
            selected: false,
            color: this.assignColorToParty(this.affiliations[i].name),
          };

          this.parties.push(party);
        }

      } catch (error) {
        console.error('Error fetching affiliations: ', error);
      }
    },

    async fetchSeatCountById(id) {
      try {
        const response = await fetch(`http://localhost:8080/electionresults/affiliation/${id}/seats`);
        if (!response.ok) {
          throw new Error(response.status);
        }

        return await response.json();

      } catch (error) {
        console.error('Error fetching seats: ', error);

      }
    },

    calculateArcPath(index) {
      const totalSeats = this.parties.reduce((sum, party) => sum + party.seatAmount, 0);
      const anglePerSeat = 180 / totalSeats;
      const startAngle = this.getStartAngle(index);
      const endAngle = this.getEndAngle(index);
      const start = this.polarToCartesian(100, 100, 90, startAngle);
      const end = this.polarToCartesian(100, 100, 90, endAngle);
      const largeArcFlag = endAngle - startAngle <= 180 ? "0" : "1";
      return `M ${start.x} ${start.y} A 90 90 0 ${largeArcFlag} 1 ${end.x} ${end.y} L 100 100 Z`;
    },

    getStartAngle(index) {
      const totalSeats = this.parties.reduce((sum, party) => sum + party.seatAmount, 0);
      const anglePerSeat = 180 / totalSeats;
      return -90 +
          this.selectedParties
              .slice(0, index)
              .reduce((sum, party) => sum + party.seatAmount * anglePerSeat, 0);
    },

    getEndAngle(index) {
      const totalSeats = this.parties.reduce((sum, party) => sum + party.seatAmount, 0);
      const anglePerSeat = 180 / totalSeats;
      return this.getStartAngle(index) + this.selectedParties[index].seatAmount * anglePerSeat;
    },

    polarToCartesian(centerX, centerY, radius, angleInDegrees) {
      const angleInRadians = (angleInDegrees - 90) * (Math.PI / 180.0);
      return {
        x: centerX + radius * Math.cos(angleInRadians),
        y: centerY + radius * Math.sin(angleInRadians),
      };
  },

  assignColorToParty (partyName){

    switch(partyName) {
      case "PVV":
        return "#5fefde"
      case "GLPVDA":
        return "#e02121";
      case "VVD":
        return "#f18f35"
      case "NSC":
        return "#f6cb3d";
      case "D66":
        return "#17af17"
      case "BBB":
        return "#97e972"
      case "CDA":
        return "#d9ffca";
      case "SP":
        return "#9347e7"
      case "FVD":
        return "#ffa395"
      case "PVDD":
        return "#debbbb"
      case "DENK":
        return "#f6f6f6"
      case "CU":
        return "#a7bfd7"
      case "SGP":
        return "#ff6800"
      case "VOLT":
        return "#fffd72"
      case "JA21":
        return "#dbcafa"

      default:
        return "white"
    }
  }



},

}

</script>

<style scoped>

.top {
  margin-top: 10px;
  padding: 10px;
}
.topTitle {
  font-size: 1.5rem;
  line-height: 2.25rem;
  font-weight: 400;
}
.title-seperator {
  border: none;
  height: 3px;
  background-color: #004494;
  width: 16%;
  border-radius: 100px;
}
.topInstruction {
  margin-top: 10px;
  font-size: 16px;
  font-weight: 400;
}


.content {
  padding: 20px;
  display: flex;
  flex-wrap: wrap;
}


.graphWrapper {
  //border: 1px solid red;
  height: 220px;
  width: 100%;
  text-align: center;
}
#seatsCounter {
  font-weight: normal;
}


.graphFooter {
  width: 100%;
  //border: 1px solid red;

}
#graphFooterTitle {
  display: inline-block;
  font-weight: 400;
  //border: 1px solid red;
  margin-top: 30px;
  margin-bottom: 11px;
  //border: 1px solid red;

}
.activatedPartiesWrapper {
  display: inline-block;

}
.activatedParty {
  display: inline-block;
  padding: 10px;
  margin: 2px;
  background-color: #FFCC00;
  border-radius: 30px;
  text-align: center;
  font-size: 12px;
  font-weight: bold;
  color: black;
}


.bottomWrapper {;
//border: 1px solid red;
  width: 55%;
  margin: 0 auto;
}
#bottomWrapperTop {
  margin-top: 15px;
  padding: 0;
}
#title-seperator1 {
  width: 9%;
}


.tagsWrapper {
  min-width: 250px;
  padding: 10px;
  //border: 1px solid red;
}
.active{
  background: #FFCC00;
}
.resetButton {
  display: inline-block;
  padding: 10px;
  border-radius: 10px;
  background: #004494;
  margin: 2px;
  width: 95px;
  text-align: center;
  font-size: 16px;
  border: none;
  color: #f5c90e;
  font-weight: bold;
}
.resetButton:hover {
  cursor: pointer;
  background: #f5c90e;
  color: #004494;
}


@media (max-width: 900px) {
  .graphFooter {
    width: 40%;
    display: inline-block;
  }

  .partyTag {
    width: 70px;
  }
  .resetButton {
    width: 90px;
  }
  .tagsWrapper{
    width: 40%;
  }

}

#seatsCounter{
  margin-top: 1%;
  //border: 1px solid red;
}


</style>