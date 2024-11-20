<template>
  <h2>Zetelverdeling</h2>
  <div class="content">
    <canvas ref="barChart" id="myChart"></canvas>
  </div>
</template>

<script>
import {Chart} from "chart.js/auto";
import * as helpers from "chart.js/helpers";

export default {
  name: "SeatDistribution",
  async created() {
    await this.fetchAffiliations();
    window.Chart = Chart;
    Chart.helpers = helpers;
    await import("chart.js-plugin-labels-dv");
  },
  data() {
    return {
      affiliations: [],
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
        this.affiliations = affiliations.map(aff => ({...aff, seats: 0}));

        await this.fetchSeatCount()
        this.createChart()
      } catch (error) {
        console.error('Error fetching afiliations: ', error)
      }
    },
    async fetchSeatCount() {
      try {
        const promises = this.affiliations.map(async (affiliation) => {
          const response = await fetch(`http://localhost:8080/electionresults/affiliation/${affiliation.id}/seats`);
          if (!response.ok) {
            throw new Error(response.status);
          }
          const seats = await response.json();
          affiliation.seats = seats;
        })
        await Promise.all(promises);
        this.trimAndSort()
      } catch (error) {
        console.error('Error fetching seats: ', error)
      }
    },
    trimAndSort() {
      this.affiliations = this.affiliations
          .filter(affiliation => affiliation.seats > 0)
          .sort((a, b) => b.seats - a.seats);
    },
    createChart() {
      const ctx = this.$refs.barChart.getContext("2d");
      const labels = this.affiliations.map(affiliation => affiliation.name);
      const electionData = this.affiliations.map(affiliation => affiliation.seats);

      const data = {
        labels: labels,
        datasets: [{
          label: "Aantal zetels 2023",
          data: electionData,
          borderWidth: 1,
          borderColor: '#B9C5E9',
          borderRadius: 2,
          backgroundColor: 'rgba(185, 197, 233, 0.5)',
        }]
      }

      const config = {
        type: "bar",
        data: data,
        options: {
          plugins: {
            afterDraw: {
            },
            legend: {
              labels: {
                font: {
                  size: 19,
                  weight: 'bold',
                }
              }
            }
          },
          scales: {
            y: {
              beginAtZero: true,
            }
          }
        }
      }
      new Chart(ctx, config)
    }
  }
}
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