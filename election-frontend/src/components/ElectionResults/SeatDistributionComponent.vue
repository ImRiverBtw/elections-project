<template>
  <h2>Zetelverdeling</h2>
  <div v-if="affiliations && !err" class="content">
    <canvas ref="barChart" id="myChart"></canvas>
  </div>
  <error-component v-if="err" :err="err.message"/>
  <loading v-if="loading"></loading>
</template>

<script>
import {Chart} from "chart.js/auto";
import {useAffiliations} from "@/Composables/useAffiliations.js";
import ErrorComponent from "@/components/Status/ErrorComponent.vue";
import Loading from "@/components/Status/Loading.vue";
import {nextTick, onMounted, provide, ref} from "vue";

export default {
  name: "SeatDistribution",
  components: {Loading, ErrorComponent},
  setup() {
    const {affiliations, err, loading, fetchAffiliationResults} = useAffiliations();
    const barChart = ref(null);
    provide("err", err)

    const createChart = () => {
        const ctx = barChart.value.getContext("2d");
        const labels = affiliations.value.map(affiliation => affiliation.name);
        const electionData = affiliations.value.map(affiliation => affiliation.seatCount);

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

    onMounted(async () => {
      await fetchAffiliationResults();
      window.Chart = Chart;
      await import("chart.js-plugin-labels-dv");
      if (affiliations.value.length > 0 && !err.value) {
        console.log(affiliations.value.map(affiliation => affiliation.seatCount))
        await nextTick()
        createChart();
      }
    })
    return {affiliations, err, loading, barChart}
  },
  methods: {

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