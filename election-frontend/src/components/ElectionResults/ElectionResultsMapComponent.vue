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
import Fuse from 'fuse.js';

export default {
  data() {
    return {
      geojson: null,
      gemeentenVotesMap: {},
      gekozenGemeentens: new Set(),
      fuse: null,
      data: null
    };
  },
  created() {
    // Ophalen van de verkiezingsresultaten
    fetch('http://localhost:8080/electionresult/townships')
        .then(response => response.json())
        .then(jsonData => {
          jsonData.forEach(gemeente => {
            const partijNaam = gemeente.affiliationName || gemeente.affiliation?.name || 'Onbekend';
            this.gemeentenVotesMap[gemeente.name] = partijNaam;
          });

          const normalizedGemeenten = jsonData.map(gemeente => ({
            name: this.normalize(gemeente.name),
            original: gemeente.name
          }));

          console.log("Election Results Map:", this.gemeentenVotesMap);

          const fuseOptions = {
            keys: ['name'],
            threshold: 0.4
          };

          this.fuse = new Fuse(normalizedGemeenten, fuseOptions);
        })
        .catch(error => {
          console.error('Error fetching election results:', error);
        });
  },
  mounted() {
    let bounds = [[50.5, 3.2], [53.7, 7.2]];

    let map = L.map('map', {
      center: [52.1, 5.3],
      zoom: 7,
      minZoom: 7
    });

    map.setMaxBounds(bounds);

    // Voeg OpenStreetMap tegels toe
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    function getColor(party) {
      return party === 'NSC' ? '#FFDD00' :   // Geel voor NSC
          party === 'PVV' ? '#1E90FF' :   // Blauw voor PVV
              party === 'GLPVDA' ? '#FF0000' :   // Rood voor GLPVDA
                  party === 'SGP' ? '#FF6600' :   // Oranje voor SGP
                      party === 'VVD' ? '#0000FF' :   // Donkerblauw voor VVD
                          party === 'D66' ? '#00FF00' :   // Groen voor D66
                              '#CCCCCC';  // Grijs als default
    }

    let directMatch = [];
    let noMatch = [];
    let fuzzyMatch = [];

    const style = (feature) => {
      let gemeenteNaam = feature.properties.name;


      let winningParty = this.gemeentenVotesMap[gemeenteNaam] || this.gemeentenVotesMap[this.normalize(gemeenteNaam)];

      if (!winningParty && !this.gekozenGemeentens.has(gemeenteNaam)) {
        const fuzzyResult = this.fuse.search(this.normalize(gemeenteNaam));

        if (fuzzyResult.length > 0) {
          const bestMatch = fuzzyResult[0].item.original;
          winningParty = this.gemeentenVotesMap[bestMatch];
        }
      }

      feature.properties.winning_party = winningParty || 'Onbekend';

      return {
        fillColor: getColor(winningParty),
        weight: 2,
        opacity: 1,
        color: 'white',
        dashArray: '3',
        fillOpacity: 0.7
      };
    };

    console.log("direct match ", directMatch);
    console.log("no match ", noMatch);
    console.log("fuzzy match", fuzzyMatch);

    const highlightFeature = (e) => {
      let layer = e.target;
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
      layer.bindPopup('<strong>' + feature.properties.name + '</strong><br>Grootste partij: ' + feature.properties.winning_party);
    };

    // Haal de GeoJSON data op en voeg deze toe aan de kaart
    fetch('https://www.webuildinternet.com/articles/2015-07-19-geojson-data-of-the-netherlands/townships.geojson')
        .then(response => response.json())
        .then(data => {
          this.geojson = L.geoJson(data, {
            style: style,
            onEachFeature: onEachFeature
          }).addTo(map);
        })
        .catch(error => console.error('Error fetching GeoJSON data:', error));
  },
  methods: {
    // Hulpmethode om een array te schudden
    shuffleArray(array) {
      for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]];
      }
      return array;
    },
    normalize(name) {
      const normalized = name.toLowerCase().replace(/[^a-z0-9]/g, '')
      if (normalized) {
        return normalized;
      }
    }
  }
};
</script>

<style scoped>
#map {
  height: 600px;
  width: 100%;
}
</style>
