<template>
  <div>
    <!-- Constituency Dropdown -->
    <div class="form-group">
      <label for="constituencySelect" class="label">Selecteer een Gemeente:</label>
      <select
          id="constituencySelect"
          v-model="selectedConstituency"
          @change="fetchVotes"
          class="styled-select"
      >
        <option
            v-for="constituency in constituencies"
            :key="constituency.id"
            :value="constituency.id"
        >
          {{ constituency.name }}
        </option>
      </select>
    </div>

    <!-- Chart Section -->
    <div v-if="chartData.length" class="chart-container">
      <canvas id="votesChart"></canvas>
    </div>
    <div v-else class="no-data">
      <p>Geen data om te weergeven.</p>
    </div>
  </div>
</template>

<script>
import { Chart } from "chart.js";

export default {
  name: "MunicipalityFinderComponent",
  data() {
    return {
      constituencies: [], // List of constituencies fetched from the backend
      selectedConstituency: null, // Selected constituency ID
      chartData: [], // Data to populate the chart
      chartInstance: null, // Chart.js instance
    };
  },
  methods: {
    // Fetch constituencies from the backend
    async fetchConstituencies() {
      try {
        const response = await fetch("http://localhost:8080/electionresult/constituencies");
        if (!response.ok) {
          throw new Error(`Failed to fetch constituencies: ${response.status}`);
        }
        const data = await response.json();
        console.log("Fetched Constituencies:", data);
        this.constituencies = data;
      } catch (error) {
        console.error("Error fetching constituencies:", error);
        alert("Could not fetch constituencies.");
      }
    },

    // Fetch votes for the selected constituency
    async fetchVotes() {
      if (!this.selectedConstituency) {
        alert("Please select a constituency.");
        return;
      }

      try {
        const response = await fetch(
            `http://localhost:8080/electionresult/constituency/${this.selectedConstituency}/votes`
        );
        if (!response.ok) {
          throw new Error(`Failed to fetch votes: ${response.status}`);
        }
        const data = await response.json();
        console.log("Fetched Votes Data:", data);
        this.chartData = data;
        this.$nextTick(() => {
          this.updateChart(data);
        });
      } catch (error) {
        console.error("Error fetching votes:", error);
        alert("Could not fetch votes for the selected constituency.");
      }
    },

    // Update the chart with new data
    updateChart(data) {
      const chartCanvas = document.getElementById("votesChart");
      if (!chartCanvas) {
        console.error("Canvas element not found. Make sure the <canvas> is rendered.");
        return;
      }

      const ctx = chartCanvas.getContext("2d");

      // Clear existing chart if present
      if (this.chartInstance) {
        this.chartInstance.destroy();
      }

      // Prepare labels and dataset for the chart
      const labels = data.map((item) => item.affiliation);
      const votes = data.map((item) => item.votes);

      // Create new Chart.js instance
      this.chartInstance = new Chart(ctx, {
        type: "bar",
        data: {
          labels: labels,
          datasets: [
            {
              label: "Votes",
              data: votes,
              backgroundColor: "#007BFF",
              borderColor: "#0056b3",
              borderWidth: 1,
            },
          ],
        },
        options: {
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
              labels: {
                font: {
                  size: 14,
                },
              },
            },
          },
        },
      });
    },
  },
  async mounted() {
    await this.fetchConstituencies();
  },
};
</script>

<style scoped>
/* General page styling */
body {
  font-family: Arial, sans-serif;
  color: #333;
  margin: 0;
  padding: 0;
}

/* Center and limit the width of the container */
.form-group {
  width: 60%; /* Adjusted size to be larger but not full-width */
  max-width: 500px; /* Slightly bigger than the previous version */
  margin: 40px auto 20px; /* Adds space from the top (40px) */
  text-align: left;
}

/* Label styling */
.label {
  font-size: 16px;
  margin-bottom: 8px;
  display: block;
}

/* Styled dropdown */
.styled-select {
  width: 100%; /* Ensures it fits the container width */
  padding: 12px; /* Slightly more padding for a comfortable click area */
  font-size: 16px; /* Adjusted font size for readability */
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #fff;
  outline: none;
  cursor: pointer;
}

.styled-select:focus {
  border-color: #007bff;
  box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
}

/* Chart container with spacing */
.chart-container {
  width: 60%; /* Matches the dropdown width */
  max-width: 700px; /* Slightly larger for visual balance */
  margin: 30px auto; /* Adds some space from the dropdown */
}

.no-data {
  text-align: center;
  color: #999;
  font-size: 14px;
  margin-top: 20px;
}
</style>
