<template>
  <div>
    <h2>Uitslagenkaart</h2>
    <div class="content">
      <div ref="mapContainer" class="map-container"></div>
    </div>
  </div>
</template>

<script>
import mapboxgl from 'mapbox-gl';

export default {
  name: "ElectionResultsMap",
  data() {
    return {
      map: null, // Voeg een map eigenschap toe aan de data
    };
  },
  mounted() {
    mapboxgl.accessToken = 'pk.eyJ1IjoiZGR3MjIxMiIsImEiOiJjbTI0amc4bnEwZjQ1MmtzNmNreWhoaTV3In0.fWrzBJ3_mczo0s4NzjvHxA';

    this.map = new mapboxgl.Map({
      container: this.$refs.mapContainer,
      style: 'mapbox://styles/ddw2212/cm24jsqe100dy01r21eum3t5c',
      center: [5.2, 52.2],
      zoom: 6
    });

    this.map.on('load', () => {
      // Voeg een bron (source) toe voor de gemeenten (townships)
      this.map.addSource('townships', {
        type: 'geojson',
        data: {
          type: 'FeatureCollection',
          features: []
        }
      });

      // Voeg een lege laag toe die we later zullen updaten
      this.map.addLayer({
        'id': 'townships-layer',
        'type': 'fill',
        'source': 'townships',
        'layout': {},
        'paint': {
          'fill-color': '#088', // Initieel statische kleur
          'fill-opacity': 0.5   // Initieel doorzichtigheid
        }
      });

      // Voorbeeldmethode om de stijl van de laag aan te passen
      this.updateLayerStyle('#FF5733'); // Voorbeeld: oranje kleur
    });
  },
  methods: {
    // Methode om de stijl van de laag dynamisch aan te passen
    updateLayerStyle(color) {
      this.map.setPaintProperty('townships', 'fill-color', color);
    }
  }
}
</script>

<style scoped>
.map-container {
  height: 500px;
  width: 200%;
}
</style>
