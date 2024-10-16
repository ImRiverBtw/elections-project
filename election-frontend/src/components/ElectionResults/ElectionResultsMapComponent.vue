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
      hoveredLayerId: null // Houdt de ID van de laag die wordt gehovert
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
          features: [] // Zorg ervoor dat je hier data toevoegt
        }
      });

      // Voeg de laag toe voor gemeenten
      this.map.addLayer({
        'id': 'townships',
        'type': 'fill',
        'source': 'townships',
        'layout': {},
        'paint': {
          'fill-color': 'hsla(0, 0%, 0%, 0.1)', // Standaard kleur voor alle gemeenten
          'fill-opacity': 0.5   // Standaard doorzichtigheid
        }
      });

      // Zodra de kaart geladen is, pas specifieke gemeente kleuren aan
      this.updateMunicipalityColors();
    });
  },
  methods: {
    updateMunicipalityColors() {
      // Stel de kleur per gemeente in, gebruikmakend van de 'setPaintProperty' methode
      this.map.setPaintProperty('townships', 'fill-color', [
        'case',
        ['==', ['get', 'name'], 'Amsterdam'], '#FF0000', // Rode kleur voor Amsterdam
        ['==', ['get', 'name'], 'Rotterdam'], '#00FF00', // Groene kleur voor Rotterdam
        'hsla(0, 0%, 0%, 0.1)' // Default kleur voor andere gemeenten
      ]);
    },
    getMunicipalityData(event) {
      const features = this.map.queryRenderedFeatures(event.point, {
        layers: ['townships']
      });

      if (features.length > 0) {
        const municipality = features[0].properties.name; // Controleer of 'name' de juiste eigenschap is
        console.log('Gemeente naam:', municipality);
      } else {
        console.log('Geen gemeente gevonden op deze locatie');
      }
    }
  }
}
</script>

<style scoped>
.map-container {
  height: 500px;
  width: 100%;
}
</style>
