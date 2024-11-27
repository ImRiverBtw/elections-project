<template>
  <!-- Main heading for the seat distribution chart -->
  <h2>Zetelverdeling</h2>

  <!-- Render the chart if there are no errors and affiliations data exists -->
  <div v-if="affiliations && !err" class="content">
    <canvas ref="barChart" id="myChart"></canvas>
  </div>

  <!-- Show error component if there is an error -->
  <error-component v-if="err" :err="err.message" />

  <!-- Show loading indicator while data is being fetched -->
  <loading v-if="loading"></loading>
</template>

<script>
import {Chart} from "chart.js/auto"; // Import Chart.js library
import {useAffiliations} from "@/composables/useAffiliations.js"; // Custom composable for fetching affiliation data
import ErrorComponent from "@/components/Status/ErrorComponent.vue"; // Error handling component
import Loading from "@/components/Status/Loading.vue"; // Loading indicator component
import {nextTick, onMounted, provide, ref} from "vue"; // Vue Composition API utilities

export default {
  name: "SeatDistribution", // Component name for debugging and reusability
  components: {Loading, ErrorComponent}, // Register child components
  setup() {
    // Destructure reactive variables and functions from the custom composable
    const {affiliations, err, loading, fetchAffiliationResults} = useAffiliations();

    // Ref for the canvas element to render the chart
    const barChart = ref(null);

    // Provide the error object to be available for child components
    provide("err", err);

    /**
     * Function to create and configure the bar chart using Chart.js
     */
    const createChart = () => {
      // Get the 2D rendering context for the canvas
      const ctx = barChart.value.getContext("2d");

      // Extract labels and seat count data from affiliations
      const labels = affiliations.value.map(affiliation => affiliation.name);
      const electionData = affiliations.value.map(affiliation => affiliation.seatCount);

      // Define the chart data and styling
      const data = {
        labels: labels, // X-axis labels
        datasets: [{
          label: "Aantal zetels 2023", // Dataset label
          data: electionData, // Y-axis data
          borderWidth: 1,
          borderColor: '#B9C5E9',
          borderRadius: 2,
          backgroundColor: 'rgba(185, 197, 233, 0.5)',
        }]
      };

      // Define chart configuration options
      const config = {
        type: "bar", // Chart type
        data: data,
        options: {
          plugins: {
            legend: {
              labels: {
                font: {
                  size: 19, // Font size for legend labels
                  weight: 'bold', // Font weight for legend labels
                }
              }
            }
          },
          scales: {
            // Configure Y-axis to start at zero
            y: {
              beginAtZero: true,
            }
          }
        }
      };

      // Create a new Chart instance
      new Chart(ctx, config);
    };

    /**
     * Lifecycle hook that runs after the component is mounted
     */
    onMounted(async () => {
      // Fetch affiliation data from the API
      await fetchAffiliationResults();

      // Ensure Chart.js is available globally (for plugins)
      window.Chart = Chart;

      // Dynamically import the Chart.js labels plugin
      await import("chart.js-plugin-labels-dv");

      // Check if data is available and there are no errors before creating the chart
      if (affiliations.value.length > 0 && !err.value) {
        console.log(affiliations.value.map(affiliation => affiliation.seatCount)); // Debug: log seat counts
        await nextTick(); // Wait for the DOM to update
        createChart(); // Create the chart
      }
    });

    // Expose variables and methods to the template
    return {affiliations, err, loading, barChart};
  },
  methods: {}
};
</script>

<style scoped>
h2 {
  margin-bottom: 18px;
}

canvas {
  padding: 8px;
  filter: drop-shadow(0 0 0.15rem lightgrey);
  background-color: white;
  border-radius: 2px;
}
</style>
