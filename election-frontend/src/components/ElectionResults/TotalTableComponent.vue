<template>
  <h2>Totaaltabel</h2>
  <div class="total-table">
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
        <th>{{ affiliation.name }}</th>
        <td>{{ affiliation.votes }} stemmen <br> {{ getVotePercentage(affiliation) }}%</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

export default {
  name: "TotalTable",
  created() {
    this.fetchAffiliations();
  },
  data() {
    return {
      affiliations: [],
      totalVoteCount: 0,
      selectedSort: "alphabeticalAsc"
    }
  },
  methods: {
    async fetchAffiliations() {
      try {
        const response = await fetch('http://localhost:8080/electionresult/affiliation');
        if (!response.ok) {
          throw new Error(response.status);
        }
        const affiliations = await response.json();
        console.log(affiliations)
        this.affiliations = affiliations.map(aff => ({ ...aff, votes: 0 }));

        await this.fetchVoteCount()
      } catch (error) {
        console.error('Error fetching afiliations: ', error)
      }
    },
    async fetchVoteCount() {
      try {
        const promises = this.affiliations.map(async (affiliation) => {
          const response = await fetch(`http://localhost:8080/electionresult/affiliation/${affiliation.id}/votes`);
          if (!response.ok) {
            throw new Error(response.status);
          }
          const votes = await response.json();
          console.log(votes)
          affiliation.votes = votes;
        })
        await Promise.all(promises);
        this.totalVoteCount = this.affiliations.reduce((total, affiliation) => total + affiliation.votes, 0);
      } catch (error) {
        console.error('Error fetching votes: ', error)
      }
    },
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