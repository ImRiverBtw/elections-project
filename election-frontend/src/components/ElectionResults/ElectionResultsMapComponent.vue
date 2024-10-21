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
    //De ruimte die de kaart kan bewegen
    const bounds = [
      [3.1, 50.5],  // Southwest coordinates (iets zuidelijker en westelijker)
      [7.5, 53.7]   // Northeast coordinates (iets noordelijker en oostelijker)
    ];

    this.map = new mapboxgl.Map({
      container: this.$refs.mapContainer,
      style: 'mapbox://styles/ddw2212/cm24jsqe100dy01r21eum3t5c',
      center: [5.2, 52.2],
      zoom: 6,
      maxBounds: bounds
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

      this.map.on('click', 'townships', (e) =>{
        this.getMunicipalityData(e);
      })

      this.getAllMunicipalityNames();
    });
  },
  methods: {
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
    },

    getAllMunicipalityNames() {
      const features = this.map.queryRenderedFeatures({ layers: ['townships'] }); // Specificeer de laag

      if (features.length > 0) {
        // Filter de namen die je zoekt, bijvoorbeeld "Texel"
        const municipalityNames = features.map(feature => feature.properties.name);

        // Zoek naar een specifieke naam, bijvoorbeeld "Texel"
        //const specificMunicipality = municipalityNames.find(name => name === 'Texel');

        // Als je alle namen wilt loggen
        this.updateMunicipalityColors(municipalityNames);
      } else {
        console.log('Geen gemeenten gevonden.');
      }
    },
    updateMunicipalityColors(municipalityNames) {
      console.log('Alle gemeenten:', municipalityNames);

      // Bouw de conditionele array
      const colorCases = ['case'];

      // Voeg kleuren toe voor gemeenten met een 'a' in de naam
      municipalityNames.forEach((name) => {
        // Controleer of de gemeente naam een 'a' bevat
        if (name.toLowerCase().includes('a')) {
          colorCases.push(['==', ['get', 'name'], name]); // Voeg de gemeente naam toe
          colorCases.push('hsla(115, 100%, 50%, 0.7)'); // Groene kleur voor gemeenten met 'a'
        } else if (name.toLowerCase().includes('r')){
          colorCases.push(['==', ['get', 'name'], name]); // Voeg de gemeente naam toe
          colorCases.push('hsla(0, 100%, 50%, 0.7)');
        }
      });

      // Voeg de default kleur toe voor andere gemeenten
      colorCases.push('hsla(0, 0%, 50%, 0.7)'); // Default kleur voor andere gemeenten

      // Stel de kleuren in met de gehele case
      this.map.setPaintProperty('townships', 'fill-color', colorCases);
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
