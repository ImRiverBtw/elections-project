<template>
  <div class="municipality-finder">
    <h2>Zoek je Gemeente</h2>
    <div class="search-box">
      <div class="search-input">
        <i class="fa fa-search"></i>
        <input
            type="text"
            placeholder="Zoek uw gemeente"
            v-model="searchQuery"
            @input="filterMunicipalities"
        />
        <i class="fa fa-times-circle" @click="clearSearch"></i>
      </div>
      <ul v-if="filteredMunicipalities.length" class="municipality-list">
        <li v-for="(municipality, index) in filteredMunicipalities" :key="index" @click="selectMunicipality(municipality)">
          {{ municipality }}
        </li>
      </ul>
      <div class="buttons">
        <button
            :class="['btn', { 'btn-active': selected === 'top10' }]"
            @click="selectOption('top10')"
        >
          top-10
        </button>
        <button
            :class="['btn', { 'btn-active': selected === 'all' }]"
            @click="selectOption('all')"
        >
          alle cijfers
        </button>
      </div>
      <div v-if="!selectedMunicipality" class="info-box">
        <i class="fa fa-info-circle"></i>
        <span>Kies eerst een gemeente!</span>
      </div>
    </div>

    <!-- Chart for Top-10 Selection -->
    <div v-if="selectedMunicipality && selected === 'top10'" class="chart-container">
      <h3>Uitslag gemeente {{ selectedMunicipality }}</h3>
      <p>595.930 kiesgerechtigden</p>
      <p>opkomst <strong>71,0%</strong></p>
      <BarChart :data="chartData" :options="chartOptions" />
    </div>

    <!-- Table for Alle Cijfers Selection -->
    <div v-if="selectedMunicipality && selected === 'all'" class="table-container">
      <h3>Uitslag gemeente {{ selectedMunicipality }}</h3>
      <div class="election-summary">
        <div class="current-election">
          <span>{{ electionData.current.turnout }} opkomst</span>
          <span>{{ electionData.current.voters }} kiesgerechtigden</span>
        </div>
        <div class="previous-election">
          <span>{{ electionData.previous.turnout }} opkomst</span>
          <span>{{ electionData.previous.voters }} kiesgerechtigden</span>
        </div>
      </div>
      <div class="election-results">
        <div v-for="(result, index) in electionData.results" :key="index" class="party-result">
          <span>{{ result.party }}</span>
          <span>{{ result.votes }} stemmen</span>
          <span>{{ result.percentage }}%</span>
          <span :class="result.change > 0 ? 'increase' : 'decrease'">
            {{ result.change > 0 ? '+' : '' }}{{ result.change }}%
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Bar } from 'vue-chartjs';
import { Chart as ChartJS, BarElement, CategoryScale, LinearScale, Tooltip, Legend } from 'chart.js';

ChartJS.register(BarElement, CategoryScale, LinearScale, Tooltip, Legend);

export default {
  name: "MunicipalityFinder",
  components: {
    BarChart: Bar
  },
  data() {
    return {
      selected: 'top10',
      selectedMunicipality: null,
      searchQuery: '',
      municipalities: ["Amsterdam", "Rotterdam", "Den Haag", "Utrecht", "Eindhoven", "Tilburg"],
      filteredMunicipalities: [],
      chartData: {
        labels: ["GLPVDA", "VVD", "D66", "PVV", "DENK", "NSC", "PVDD", "VOLT", "SP", "BIJ1"],
        datasets: [
          {label: '2021', backgroundColor: '#b3b3b3', data: [17.6, 13.0, 22.7, 5.1, 6.6, 0.0, 7.0, 5.9, 4.9, 5.7]},
          {label: '2023', backgroundColor: '#d20f0f', data: [33.8, 11.8, 9.9, 9.6, 7.5, 6.6, 4.8, 3.8, 3.2, 2.3]}
        ]
      },
      chartOptions: {
        responsive: true,
        scales: {
          y: {beginAtZero: true, max: 40}
        }
      },
      electionData: {
        current: {turnout: "71,0%", voters: "595.930"},
        previous: {turnout: "75,9%", voters: "597.205"},
        results: [
          {party: "GLPVDA", votes: "141.976", percentage: "33,8", change: 16.2},
          {party: "VVD", votes: "49.517", percentage: "11,8", change: -1.2},
          {party: "D66", votes: "41.418", percentage: "9,9", change: -12.8},
          {party: "PVV", votes: "40.470", percentage: "9,6", change: 4.5},
          {party: "DENK", votes: "31.272", percentage: "7,5", change: 1.4}
        ]
      }
    };
  },
  methods: {
    selectOption(option) {
      this.selected = option;
    },
    filterMunicipalities() {
      this.filteredMunicipalities = this.searchQuery
          ? this.municipalities.filter(m => m.toLowerCase().includes(this.searchQuery.toLowerCase()))
          : [];
    },
    clearSearch() {
      this.searchQuery = '';
      this.filteredMunicipalities = [];
    },
    selectMunicipality(municipality) {
      this.selectedMunicipality = municipality;
      this.filteredMunicipalities = [];
      this.searchQuery = municipality;
    }
  }
};
</script>

<style scoped>
.municipality-finder {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 10px;
  border: 1px solid #e1e6f3;
}

.municipality-finder h2 {
  font-size: 24px;
  color: #004494;
  margin-bottom: 15px;
}

.search-box {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.search-input {
  display: flex;
  align-items: center;
  border: 1px solid #d3d3d3;
  border-radius: 5px;
  padding: 5px 10px;
  background-color: white;
}

.search-input i {
  color: #004494;
}

.search-input input {
  flex: 1;
  border: none;
  outline: none;
  padding: 5px;
}

.search-input i.fa-times-circle {
  cursor: pointer;
  color: #aaa;
}

.municipality-list {
  list-style-type: none;
  margin: 0;
  padding: 0;
  max-height: 150px;
  overflow-y: auto;
}

.municipality-list li {
  padding: 5px 10px;
  cursor: pointer;
}

.municipality-list li:hover {
  background-color: #e1e6f3;
}

.buttons {
  display: flex;
  gap: 10px;
}

.btn {
  border: none;
  padding: 8px 12px;
  border-radius: 5px;
  cursor: pointer;
  font-weight: bold;
}

.btn-active {
  background-color: #004494;
  color: white;
}

.info-box {
  display: flex;
  align-items: center;
  background-color: #eee;
  padding: 10px;
  border-radius: 5px;
  color: #555;
  font-size: 14px;
}

.info-box i {
  margin-right: 10px;
  color: #333;
}

.chart-container {
  margin-top: 20px;
}

.table-container {
  margin-top: 20px;
}

.election-summary {
  display: flex;
  justify-content: space-between;
  padding: 10px;
  background-color: #f5f5f5;
  border-radius: 5px;
  margin-bottom: 15px;
}

.election-summary div {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.election-results {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.party-result {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #e0e0e0;
  padding: 10px 0;
}

.increase {
  color: blue;
}

.decrease {
  color: red;
}

.sort-dropdown {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 10px;
}

.sort-dropdown label {
  margin-right: 5px;
}

.sort-dropdown select {
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #d3d3d3;
}

.more-info {
  margin-top: 20px;
  cursor: pointer;
}

.more-info a {
  color: #d20f0f;
  text-decoration: none;
  font-weight: bold;
  cursor: pointer;
}

.info-text {
  margin-top: 10px;
  color: #333;
  font-size: 14px;
}
</style>
