<template>

  <div class="top">

    <h2 class="topTitle"> Meerderheidszoeker </h2>
    <hr class="title-seperator">

    <p v-if="affiliations && !err" class="topInstruction"> Klik op de partijen en bekijk de mogelijke formaties om een
      kabinet <br>
      te vormen met een meerderheid in de tweede kamer</p>
  </div>
  <error-component v-if="err" :err="err.message"/>
  <loading v-if="loading"></loading>

  <div v-if="affiliations && !err" class="content">

    <div class="graphWrapper">

      <div class="chart-container">
        <svg :width=400 :height=200 viewBox="0 0 200 100">
          <!-- Background semi-circle -->
          <path
              d="M10,100 A90,90 0 0,1 190,100"
              fill="#d4dfef"
          ></path>
          <!-- Dynamic sections for selected affiliations -->
          <path
              v-for="(affiliation, index) in this.selectedParties"
              :key="affiliation.name"
              :d="calculateArcPath(index)"
              :fill="assignColorToAffiliation(affiliation.name)"
          ></path>
          <!-- Majority line -->
          <line x1="100" y1="100" x2="100" y2="5" stroke="black" stroke-width="0.25"></line>
        </svg>
      </div>


      <h2 id="seatsCounter"> {{ this.totalAccumulatedSeats }} Zetels</h2>

    </div>

    <div class="graphFooter">

      <p id="graphFooterTitle"> Formatie: </p>

      <div class="activatedPartiesWrapper">
        <!--          makes a "tag" for every affiliation in activatedParties array -->
        <p class="activatedAffiliation" v-for="affiliation in selectedParties"
           :key="affiliation.id"
           :style="{ backgroundColor: assignColorToAffiliation(affiliation.name) }"
        > {{ affiliation.name }} ({{ affiliation.seatCount }}) </p>

      </div>

    </div>

    <div class="bottomWrapper">

      <div class="top" id="bottomWrapperTop">
        <h2 class="topTitle"> Partijen </h2>
        <hr class="title-seperator" id="title-seperator1">
      </div>

      <div class="tagsWrapper">

        <!--        makes a affiliation tag for every affiliation in the affiliations array and binds click method to it when it is clicked -->
        <PartyTag class="partyTag"
                  v-for="affiliation in affiliations"
                  @click="handlePartyTagClick(affiliation);"
                  :class="{active: affiliation.selected}"
        ><p class="affiliationName"> {{ affiliation.name }} </p>
        </PartyTag>

        <button class="resetButton" @click="handleResetButtonClick"> Reset</button>

      </div>
    </div>
  </div>

</template>

<script>
import PartyTag from "@/components/PartyTag.vue";
import { useAffiliationResult } from "@/composables/useAffiliationResult.js";
import {onMounted, provide, ref} from "vue";
import Loading from "@/components/Status/Loading.vue";
import ErrorComponent from "@/components/Status/ErrorComponent.vue";

export default {
  name: "MajorityCalulator",
  components: {Loading, ErrorComponent, PartyTag},
  setup() {
    const {affiliations, err, loading, fetchAffiliationResults} = useAffiliationResult();
    provide("err", err)

    onMounted(async () => {
      await fetchAffiliationResults('seat');
      affiliations.value = affiliations.value.filter(affiliation => affiliation.seatCount > 0);
    })
    return {affiliations, err, loading}
  },
  data() {
    return {
      totalAccumulatedSeats: 0,
      selectedParties: [],
      defaultTagColor: "#d4dfef",
      partyColors: {}
    }
  },

  methods: {
    handlePartyTagClick(affiliation) {
      if (this.selectedParties.includes(affiliation)) {
        this.deselectAffiliation(affiliation);
      } else {
        this.selectAffiliation(affiliation);
      }
      this.partyColors[affiliation.name] = this.generateRandomColor();
    },

    selectAffiliation(affiliation) {
      this.totalAccumulatedSeats += affiliation.seatCount;
      this.selectedParties.push(affiliation);
      affiliation.selected = !affiliation.selected;
    },

    deselectAffiliation(affiliation) {
      this.totalAccumulatedSeats -= affiliation.seatCount;
      this.selectedParties = this.selectedParties.filter(
          activeAffiliation => activeAffiliation.id !== affiliation.id
      );
      affiliation.selected = !affiliation.selected;
    },

    handleResetButtonClick() {
      this.selectedParties = [];
      this.totalAccumulatedSeats = 0;
      this.partyColors = {};

      for (let i = 0; i < this.affiliations.length; i++) {
        this.affiliations[i].selected = false;
      }
    },

    calculateArcPath(index) {
      const startAngle = this.getStartAngle(index);
      const endAngle = this.getEndAngle(index);
      const start = this.polarToCartesian(100, 100, 90, startAngle);
      const end = this.polarToCartesian(100, 100, 90, endAngle);
      const largeArcFlag = endAngle - startAngle <= 180 ? "0" : "1";
      return `M ${start.x} ${start.y} A 90 90 0 ${largeArcFlag} 1 ${end.x} ${end.y} L 100 100 Z`;
    },

    getStartAngle(index) {
      const totalSeats = this.affiliations.reduce((sum, affiliation) => sum + affiliation.seatCount, 0);
      const anglePerSeat = 180 / totalSeats;
      return -90 +
          this.selectedParties
              .slice(0, index)
              .reduce((sum, affiliation) => sum + affiliation.seatCount * anglePerSeat, 0);
    },

    getEndAngle(index) {
      const totalSeats = this.affiliations.reduce((sum, affiliation) => sum + affiliation.seatCount, 0);
      const anglePerSeat = 180 / totalSeats;
      return this.getStartAngle(index) + this.selectedParties[index].seatCount * anglePerSeat;
    },

    polarToCartesian(centerX, centerY, radius, angleInDegrees) {
      const angleInRadians = (angleInDegrees - 90) * (Math.PI / 180.0);
      return {
        x: centerX + radius * Math.cos(angleInRadians),
        y: centerY + radius * Math.sin(angleInRadians),
      };
    },

    assignColorToAffiliation(affiliationName) {
      if (!this.partyColors[affiliationName]) {
        this.partyColors[affiliationName] = this.generateRandomColor();
      }
      return this.partyColors[affiliationName];
    },

    generateRandomColor() {
      return `#${Math.floor(Math.random() * 16777215).toString(16)}`;
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
  height: 220px;
  width: 100%;
  text-align: center;
}

#seatsCounter {
  font-weight: normal;
}

.graphFooter {
  width: 100%;
}

#graphFooterTitle {
  display: inline-block;
  font-weight: 400;
  margin-top: 30px;
  margin-bottom: 11px;
}

.activatedPartiesWrapper {
  display: inline-block;
}

.activatedAffiliation {
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

.bottomWrapper {
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
}

.active {
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

  .resetButton {
    width: 90px;
  }

  .tagsWrapper {
    width: 40%;
    display: grid;
    grid-template-columns: auto auto auto;
  }
}

#seatsCounter {
  margin-top: 1%;
}
</style>