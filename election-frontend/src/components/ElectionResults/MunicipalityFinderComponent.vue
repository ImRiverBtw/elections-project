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
        const response = await fetch("http://localhost:8080/electionresult/municipalities");
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
            `http://localhost:8080/electionresult/municipalities/${this.selectedConstituency}/votes`
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
      const labels = data.map((item) => item.name);
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
/* General styling for the dark theme */
body {
  font-family: Arial, sans-serif;
  background-color: #1b1b1b; /* Dark background */
  color: #f1f1f1; /* Light text for readability */
  margin: 0;
  padding: 0;
}

/* Header styling */
h2 {
  color: #f1f1f1; /* Bright white for header */
  font-size: 20px;
  text-align: left;
  margin: 20px 0;
  padding-left: 20px;
}

/* Form group container */
.form-group {
  background-color: #262626; /* Slightly lighter dark background */
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  margin: 20px auto;
  width: 90%;
  max-width: 800px;
}

/* Label styling */
.label {
  display: block;
  font-size: 16px;
  margin-bottom: 10px;
  color: #f1f1f1;
}

/* Styled dropdown */
.styled-select {
  background-color: #333333; /* Dark background for dropdown */
  color: #f1f1f1;
  border: 1px solid #444;
  border-radius: 5px;
  padding: 10px;
  width: 100%;
  font-size: 14px;
  outline: none;
  transition: border-color 0.3s ease, box-shadow 0.3s ease;
}

.styled-select:focus {
  border-color: #007bff;
  box-shadow: 0 0 8px rgba(0, 123, 255, 0.5);
}

/* Chart container */
.chart-container {
  background-color: #262626;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  margin: 20px auto;
  width: 90%;
  max-width: 800px;
}

canvas {
  background-color: transparent; /* Transparent background for the chart */
  border-radius: 5px;
}

/* No data message styling */
.no-data {
  text-align: center;
  color: #aaa; /* Muted text color */
  font-size: 14px;
  margin-top: 20px;
}

/* Buttons */
button {
  border: none;
  border-radius: 20px;
  padding: 8px 20px;
  font-size: 14px;
  cursor: pointer;
  margin: 10px 5px;
  color: #fff;
}

button.top-10 {
  background-color: #e74c3c; /* Bright red for the 'top-10' button */
}

button.all-data {
  background-color: #555; /* Neutral gray for the 'all data' button */
}

/* Additional links or info box */
.info-box {
  background-color: #262626;
  padding: 10px;
  border-radius: 8px;
  color: #aaa;
  font-size: 14px;
  margin: 20px auto;
  width: 90%;
  max-width: 800px;
  text-align: left;
}

/* Media query for responsiveness */
@media (max-width: 768px) {
  .form-group, .chart-container, .info-box {
    width: 95%;
  }

  h2 {
    font-size: 18px;
    padding-left: 10px;
  }
}

</style>
