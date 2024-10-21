<template>
  <div>
    <h2>Uitslagenkaart</h2>
    <div class="content">
      <div id="map"></div>
    </div>
  </div>
</template>

<script>
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

export default {
  data() {
    return {
      geojson: null  // Declareer geojson in de data() functie
    };
  },
  mounted() {
    // Definieer de grenzen (bounding box) voor Nederland
    let bounds = [[50.5, 3.2], [53.7, 7.2]];

    // Maak de Leaflet kaart aan, gecentreerd op Nederland
    let map = L.map('map', {
      center: [52.1, 5.3],  // Center on the Netherlands
      zoom: 7,              // Default zoom level
      minZoom: 7            // Restrict zoom in to 2 levels above the default
    });

    // Stel de zichtbare grenzen in op Nederland
    map.setMaxBounds(bounds);

    // Voeg OpenStreetMap tegels toe
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    // Functie om de kleur te bepalen op basis van de winnende partij
    function getColor(party) {
      return party === 'Partij A' ? '#FF0000' :  // Rood voor Partij A
          party === 'Partij B' ? '#0000FF' :  // Blauw voor Partij B
              party === 'Partij C' ? '#00FF00' :  // Groen voor Partij C
                  '#CCCCCC';  // Grijs als default
    }

    // Stijl voor elke gemeente
    function style(feature) {
      const randomParties = ['Partij A', 'Partij B', 'Partij C'];
      const winningParty = randomParties[Math.floor(Math.random() * randomParties.length)];
      feature.properties.winning_party = winningParty;  // Voeg de partij toe aan de feature

      return {
        fillColor: getColor(winningParty),
        weight: 2,
        opacity: 1,
        color: 'white',
        dashArray: '3',
        fillOpacity: 0.7
      };
    }

    const highlightFeature = (e) => {
      var layer = e.target;

      layer.setStyle({
        weight: 5,
        color: '#666',
        dashArray: '',
        fillOpacity: 0.9
      });

      if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
        layer.bringToFront();
      }
    };

    const resetHighlight = (e) => {
      this.geojson.resetStyle(e.target);
    };

    const onEachFeature = (feature, layer) => {
      layer.on({
        mouseover: highlightFeature,
        mouseout: resetHighlight
      });
      layer.bindPopup('<strong>' + feature.properties.name + '</strong><br>grootste partij: ' + feature.properties.winning_party);
    };

    fetch('https://www.webuildinternet.com/articles/2015-07-19-geojson-data-of-the-netherlands/townships.geojson')
        .then(response => response.json())
        .then(data => {
          this.geojson = L.geoJson(data, {
            style: style,
            onEachFeature: onEachFeature
          }).addTo(map);
        })
        .catch(error => console.error('Error fetching GeoJSON data:', error));
  }
}
</script>

<style scoped>
#map {
  height: 600px;
  width: 100%;
}
</style>
