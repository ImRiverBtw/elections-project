<template>
  <h2>Totaaltabel</h2>
  <div class="content">
    <div class=" header">
      <p>Partij</p>
      <p class="right">Totaal aantal Stemmen: {{ totalVoteCount }}</p>
    </div>

    <div class="sorting-selector">
      <label for="sort-selector">Sorteer op: </label>
      <select id="sort-selector" v-model="selectedSort">
        <option value="alphabeticalAsc">Alphabetisch (A-Z)</option>
        <option value="alphabeticalDesc">Alphabetisch (Z-A)</option>
        <option value="votesAsc">Stemmen (hoog - laag)</option>
        <option value="votesDesc">Stemmen (laag - hoog)</option>
      </select>
    </div>

    <table>
      <tbody>
      <tr
          v-for="affiliation in sortedAffiliations"
          :key="affiliation.shortName"
      >
        <th>{{ affiliation.longName }}</th>
        <td>{{ affiliation.votes }} stemmen <br> {{ getVotePercentage(affiliation) }}%</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import {Affiliation} from "@/models/affiliation.js";

export default {
  name: "TotalTable",
  created() {
    //Possibly Provide/Inject from ElectionResults
    const affiliationList = [
      "NSC",
      "PVV",
      "GLPVDA",
      "BBB",
      "BVN",
      "DENK",
      "NLPLAN",
      "SvN",
      "Lef",
      "PvdS",
      "PPvB",
      "PDG",
      "LP",
      "Splinter",
      "Bij1",
      "50Plus",
      "Volt",
      "CU",
      "PvD",
      "Ja21",
      "FvD",
      "SP",
      "CDA",
      "VVD",
      "D66"
    ]
    //fill affiliations array with data from Affiliation model
    for (let i = 0; i < affiliationList.length; i++) {
      let currentAffiliation = Affiliation.createParty(affiliationList[i])
      this.affiliations.push(currentAffiliation)
      //keep track of the total number of votes
      this.totalVoteCount += currentAffiliation.votes
    }
  },
  data() {
    return {
      affiliations: [],
      totalVoteCount: 0,
      selectedSort: "alphabeticalAsc"
    }
  },
  methods: {
    getVotePercentage(affiliation) {
      if (this.totalVoteCount === 0) {
        return 0;
      }
      return ((affiliation.votes / this.totalVoteCount) * 100).toFixed(2);
    },
    compareAlphabetical(a, b) {
      if (a.longName < b.longName)
        return -1;
      if (a.longName > b.longName)
        return 1;
      return 0;
    },
    compareVotes(a, b) {
      if (a.votes < b.votes)
        return -1;
      if (a.votes > b.votes)
        return 1;
      return 0;
    }
  },
  computed: {
    sortedAffiliations() {
      const affiliationsCopy = [...this.affiliations];
      switch (this.selectedSort) {
        case "alphabeticalAsc":
          return affiliationsCopy.sort(this.compareAlphabetical);

        case "alphabeticalDesc":
          return affiliationsCopy.sort(this.compareAlphabetical).reverse();

        case "votesAsc":
          return affiliationsCopy.sort(this.compareVotes).reverse();

        case "votesDesc":
          return affiliationsCopy.sort(this.compareVotes);

        default:
          return affiliationsCopy
      }
    }
  }
}
</script>

<style scoped>
h2{
  margin-bottom: 18px;
}
.content {
  box-sizing: border-box;
  max-width: 1100px;
  width: 100%;
  border: 1px solid gray;
  border-radius: 8px;
  padding: 8px;
}
.sorting-selector{
  text-align: right;
  margin: 8px;
}
.header {
  background-color: #B9C5E9;
  border-radius: 8px;
  padding: 18px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  font-weight: bold;
  margin-bottom: 4px;
}

table {
  box-sizing: border-box;
  width: 100%;
  border-collapse: collapse;
}
tbody tr {
  border-top: 1px solid gray;
}
th {
  text-align: left;
  padding: 18px;
}
td {
  text-align: right;
  padding: 18px;
}
</style>