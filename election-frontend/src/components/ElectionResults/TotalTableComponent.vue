<template>
  <h2>Totaaltabel</h2>
  <error-component v-if="err" :err="err.message"/>
  <loading v-if="loading"></loading>
  <div v-if="affiliations && !err" class="total-table">
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
          :key="affiliation.name"
      >
        <th>{{ affiliation.name }}</th>
        <td>{{ affiliation.votes }} stemmen <br> {{ getVotePercentage(affiliation) }}%</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import Loading from "@/components/Status/Loading.vue";
import ErrorComponent from "@/components/Status/ErrorComponent.vue";
import {useAffiliations} from "@/Composables/useAffiliations.js";
import {onMounted, provide, ref} from "vue";
export default {
  name: "TotalTable",
  components: {Loading, ErrorComponent},
  setup() {
    const {affiliations, err, loading, fetchAffiliationResults} = useAffiliations();
    provide("err", err)

    let totalVoteCount = ref(0);
    onMounted(async () => {
      await fetchAffiliationResults();

      //calculate total number of votes
      totalVoteCount.value = affiliations.value.reduce((totalVotes, affiliation) => {
        return totalVotes + affiliation.votes; // Accumulate votes
      }, 0);
    })
    return {affiliations, err, loading, totalVoteCount}
  },
  data() {
    return {
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
      if (a.name < b.name)
        return -1;
      if (a.name > b.name)
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
h2 {
  margin-bottom: 18px;
}

.sorting-selector {
  text-align: right;
  margin: 8px;
}

.header {
  background-color: #B9C5E9;
  padding: 18px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  font-weight: bold;
  margin-bottom: 4px;
  border-radius: 2px;
}

.total-table {
  filter: drop-shadow(0 0 0.15rem lightgrey);
  background-color: white;
  border-radius: 2px;

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