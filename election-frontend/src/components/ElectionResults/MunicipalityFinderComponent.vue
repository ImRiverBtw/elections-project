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
        <li
            v-for="(municipality, index) in filteredMunicipalities"
            :key="index"
            @click="selectMunicipality(municipality)"
        >
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
      <p>{{ electionData.current.voters }} kiesgerechtigden</p>
      <p>opkomst <strong>{{ electionData.current.turnout }}</strong></p>
      <div v-if="chartData && chartData.labels.length > 0">
        <BarChart :data="chartData" :options="chartOptions" />
      </div>
      <div v-else class="info-box">
        <i class="fa fa-info-circle"></i>
        <span>No data available to display the chart.</span>
      </div>
    </div>

    <!-- Table for Alle Cijfers Selection -->
    <div v-if="selectedMunicipality && selected === 'all'" class="table-container">
      <h3>Uitslag gemeente {{ selectedMunicipality }}</h3>
      <div class="election-summary">
        <div class="current-election">
          <span>{{ electionData.current.turnout }} opkomst</span>
          <span>{{ electionData.current.voters }} kiesgerechtigden</span>
        </div>
        <div class="election-results">
          <div
              v-for="(result, index) in electionData.results"
              :key="index"
              class="party-result"
          >
            <span>{{ result.party }}</span>
            <span>{{ result.votes }} stemmen</span>
            <span>{{ result.percentage }}%</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  BarElement,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(BarElement, CategoryScale, LinearScale, Tooltip, Legend);

export default {
  name: "MunicipalityFinder",
  components: {
    BarChart: Bar,
  },
  data() {
    return {
      selected: "top10",
      selectedMunicipality: null,
      searchQuery: "",
      municipalities: [], // Populated from the backend
      filteredMunicipalities: [],
      chartData: {
        labels: [], // Default structure
        datasets: [
          {
            label: "Votes",
            backgroundColor: "#d20f0f",
            data: [],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            beginAtZero: true,
          },
        },
        plugins: {
          legend: {
            position: "top",
          },
        },
      },
      electionData: {
        current: { turnout: "", voters: "" },
        results: [],
      },
    };
  },
  async mounted() {
    try {
      console.log("Fetching municipalities...");
      const response = await fetch("http://localhost:8080/api/municipalities");
      console.log("Response:", response);

      if (!response.ok) {
        throw new Error(`Error fetching municipalities: ${response.status}`);
      }

      const contentType = response.headers.get("content-type");
      if (!contentType || !contentType.includes("application/json")) {
        throw new Error("Invalid JSON response from backend.");
      }

      const data = await response.json();
      console.log("Municipality Data:", data);

      this.municipalities = data;
      this.filteredMunicipalities = this.municipalities;
    } catch (error) {
      console.error("Error fetching municipalities:", error);
      alert("An error occurred while fetching municipalities.");
    }
  },
  methods: {
    async selectMunicipality(municipality) {
      this.selectedMunicipality = municipality;
      this.filteredMunicipalities = [];
      this.searchQuery = municipality;
      await this.fetchElectionData(municipality);
    },
    async fetchElectionData(municipality) {
      try {
        console.log(`Fetching election data for: ${municipality}`);
        const response = await fetch(
            `http://localhost:8080/api/municipalities/results/${municipality}`
        );
        console.log("Response:", response);

        if (!response.ok) {
          throw new Error(
              `Error fetching election data: ${response.statusText}`
          );
        }

        const data = await response.json();
        console.log("Election Data:", data);

        if (!data || data.length === 0) {
          console.warn(`No election data available for ${municipality}.`);
          alert(`No election data available for ${municipality}.`);
          this.chartData.labels = [];
          this.chartData.datasets[0].data = [];
          return;
        }

        // Update electionData
        this.electionData.current.voters = data.reduce(
            (sum, result) => sum + result.votes,
            0
        );
        this.electionData.results = data;

        // Update chartData
        this.chartData.labels = data.map((result) => result.party);
        this.chartData.datasets[0].data = data.map((result) => result.votes);
        console.log("Updated Chart Data:", this.chartData);
      } catch (error) {
        console.error("Error fetching election data:", error);
        alert("An error occurred while fetching election data.");
      }
    },
    filterMunicipalities() {
      this.filteredMunicipalities = this.searchQuery
          ? this.municipalities.filter((m) =>
              m.toLowerCase().includes(this.searchQuery.toLowerCase())
          )
          : [];
    },
    clearSearch() {
      this.searchQuery = "";
      this.filteredMunicipalities = [];
    },
    selectOption(option) {
      this.selected = option;
    },
  },
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
  margin-top: 15px;
  background-color: #f1f1f1;
  padding: 10px;
  border-radius: 5px;
}

.more-info p {
  margin: 0;
}
</style>
