<!--
Todo:
      1: Visuele grafiek maken
      2: Resetbutton bug fixen - FIXED
      3: responsive maken voor telefoon - FIXED


-->

<template>

  <div class="top">

    <h2 class="topTitle"> Meerderheidszoeker </h2>
    <hr class="title-seperator">

    <p class="topInstruction"> Klik op de partijen en bekijk de mogelijke formaties om een kabinet <br>
      te vormen met een meerderheid in de tweede kamer</p>
  </div>

  <div class="content">

    <div class="graphWrapper">

      <p class="graph"> Grafiek placeholder</p>
      <h2 id="seatsCounter"> ({{ this.totalAccumulatedSeats }} Zetels)</h2>

    </div>

    <div class="graphFooter">

        <p id="graphFooterTitle"> Formatie: </p>

        <div class="activatedPartiesWrapper">
<!--          makes a "tag" for every party in activatedParties array -->
          <p class="activatedParty" v-for="party in selectedParties" :key="party.id"> {{ party.name }} </p>

<!--          <p class="activatedParty" v-for="party in parties.filter(p => p.selected)" :key="party.id"> {{ party.name }} </p>-->
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
                  @click="handlePartyTagClick(party)"
                  :class="{active: party.selected}"
        > <p class="partyName"> {{ party.name }} </p>
        </PartyTag>

<!--        moet een bug fixen waarbij het niet de kleur van de tag veranderd-->
        <button class="resetButton" @click="handleResetButtonClick"> Reset </button>

      </div>
    </div>
  </div>

</template>

<script >
import PartyTag from "@/components/PartyTag.vue";

export default {
  name: "MajorityCalulator",
  components: {PartyTag},

  data() {

    return {

      maxSeats: 150,

      totalAccumulatedSeats: 0,

      selectedParties: [],

      defaultTagColor: "#d4dfef",

      parties: [

        { id: "1", name: "PVV", seatAmount: 37, selected: false, color: "" },
        { id: "2", name: "GL/PVDA", seatAmount: 25, selected: false, color: "" },
        { id: "3", name: "VVD", seatAmount: 24, selected: false, color: "" },
        { id: "4", name: "NSC", seatAmount: 20, selected: false, color: "" },
        { id: "5", name: "D66", seatAmount: 9, selected: false, color: "" },
        { id: "6", name: "BBB", seatAmount: 7, selected: false, color: "" },
        { id: "7", name: "CDA", seatAmount: 5, selected: false, color: "" },
        { id: "8", name: "SP", seatAmount: 5, selected: false, color: "" },
        { id: "9", name: "DENK", seatAmount: 3, selected: false, color: "" },
        { id: "10", name: "PVDD", seatAmount: 3, selected: false, color: "" },
        { id: "11", name: "FVD", seatAmount: 3, selected: false, color: "" },
        { id: "12", name: "SGP", seatAmount: 3, selected: false, color: "" },
        { id: "13", name: "CU", seatAmount: 3, selected: false, color: "" },
        { id: "14", name: "VOLT", seatAmount: 2, selected: false, color: "" },
        { id: "15", name: "JA21", seatAmount: 1, selected: false, color: "" },
      ]
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

      console.log("test")

      this.selectedParties = [];
      this.totalAccumulatedSeats = 0;

      console.log("beforeloop");

      for (let i = 0; i < this.parties.length; i++) {

        console.log("loop")
        this.parties[i].selected = false;

      }

    }

  },

  watch: {
    },

  computed: {
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
  border: 1px solid red;
  height: 350px;
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
  margin-top: 11px;
  margin-bottom: 11px;

}
.activatedPartiesWrapper {
  display: inline-block;
  //border: 1px solid red;
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
}


.bottomWrapper {;
//border: 1px solid red;
  width: 55%;
  margin: 0 auto;
}
#bottomWrapperTop {
  margin-top: 0;
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


</style>