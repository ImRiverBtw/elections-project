<template>
  <!-- Main heading for the total table -->
  <h2>Totaaltabel</h2>

  <!-- Render error component if an error occurs -->
  <error-component v-if="err" :err="err.message" />

  <!-- Show loading indicator while data is being fetched -->
  <loading v-if="loading" />

  <!-- Render the total table if affiliations data is available and there is no error -->
  <div v-if="affiliations && !err" class="total-table">

    <!-- Header section displaying the title and total vote count -->
    <div class="header">
      <p>Partij</p>
      <p class="right">Totaal aantal Stemmen: {{ totalVoteCount }}</p>
    </div>

    <!-- Sorting selector to allow sorting by different criteria -->
    <div class="sorting-selector">
      <label for="sort-selector">Sorteer op: </label>
      <select id="sort-selector" v-model="selectedSort">
        <option value="alphabeticalAsc">Alphabetisch (A-Z)</option>
        <option value="alphabeticalDesc">Alphabetisch (Z-A)</option>
        <option value="votesAsc">Stemmen (hoog - laag)</option>
        <option value="votesDesc">Stemmen (laag - hoog)</option>
      </select>
    </div>

    <!-- Table displaying affiliation data -->
    <table>
      <tbody>
      <!-- Loop through the sorted affiliations and display each one -->
      <tr v-for="affiliation in sortedAffiliations" :key="affiliation.name">
        <!-- Affiliation name -->
        <th>{{ affiliation.name }}</th>
        <!-- Affiliation votes and percentage of total votes -->
        <td>
          {{ affiliation.votes }} stemmen <br />
          {{ getVotePercentage(affiliation) }}%
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import Loading from "@/components/Status/Loading.vue"; // Loading indicator component
import ErrorComponent from "@/components/Status/ErrorComponent.vue"; // Error display component
import { useAffiliationResult } from "@/composables/useAffiliationResult.js"; // Custom composable to fetch affiliation data
import { onMounted, provide, ref } from "vue"; // Vue Composition API utilities

export default {
  name: "TotalTable", // Component name
  components: { Loading, ErrorComponent }, // Register child components
  setup() {
    const { affiliations, err, loading, fetchAffiliationResults } = useAffiliationResult(); // Fetch reactive affiliation data, errors, and loading state
    provide("err", err); // Provide the error object to child components for global error handling

    let totalVoteCount = ref(0); // Reactive variable to store total vote count

    /**
     * Lifecycle hook to fetch affiliation data on component mount
     */
    onMounted(async () => {
      await fetchAffiliationResults('vote'); // Fetch data from the API

      // Calculate the total number of votes by summing votes of all affiliations
      totalVoteCount.value = affiliations.value.reduce((totalVotes, affiliation) => {
        return totalVotes + affiliation.votes;
      }, 0);
    });

    return { affiliations, err, loading, totalVoteCount }; // Expose data and functions to the template
  },
  data() {
    return {
      selectedSort: "alphabeticalAsc", // Default sorting method
    };
  },
  methods: {

    getVotePercentage(affiliation) {
      if (this.totalVoteCount === 0) {
        return 0; // Avoid division by zero
      }
      return ((affiliation.votes / this.totalVoteCount) * 100).toFixed(2);
    },


    compareAlphabetical(a, b) {
      if (a.name < b.name) return -1;
      if (a.name > b.name) return 1;
      return 0;
    },

    compareVotes(a, b) {
      if (a.votes < b.votes) return -1;
      if (a.votes > b.votes) return 1;
      return 0;
    },
  },
  computed: {
    /**
     * Computed property to return sorted affiliations based on the selected sorting method
     * @returns {Array} - Sorted array of affiliations
     */
    sortedAffiliations() {
      const affiliationsCopy = [...this.affiliations]; // Create a shallow copy to avoid mutating original data
      switch (this.selectedSort) {
        case "alphabeticalAsc":
          return affiliationsCopy.sort(this.compareAlphabetical); // Sort alphabetically (A-Z)

        case "alphabeticalDesc":
          return affiliationsCopy.sort(this.compareAlphabetical).reverse(); // Sort alphabetically (Z-A)

        case "votesAsc":
          return affiliationsCopy.sort(this.compareVotes).reverse(); // Sort by votes (high to low)

        case "votesDesc":
          return affiliationsCopy.sort(this.compareVotes); // Sort by votes (low to high)

        default:
          return affiliationsCopy; // Default case (no sorting)
      }
    },
  },
};
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
