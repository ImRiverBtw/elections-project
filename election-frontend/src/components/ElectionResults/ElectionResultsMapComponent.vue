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
      map: null,
      geojson: null,
      rawGeoJson: null,
      gemeentenVotesMap: {},
      gekozenGemeentens: new Set(),
      fuse: null
    };
  },
  created() {
    // Ophalen van de verkiezingsresultaten
    fetch('http://localhost:8080/electionresult/pollingStation')
        .then(response => response.json())
        .then(jsonData => {
          console.log('Fetched election results:', jsonData);
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
          this.addGeoJsonToMap(); // Update de kaart
        })
        .catch(error => {
          console.error('Error fetching election results:', error);
          this.fuse = new Fuse([], {}); // Lege Fuse-instantie voor veiligheid
        });
  },
  mounted() {
    let bounds = [[50.5, 3.2], [53.7, 7.2]];

    this.map = L.map('map', {
      center: [52.1, 5.3],
      zoom: 7,
      minZoom: 7
    });

    this.map.setMaxBounds(bounds);

    // Voeg OpenStreetMap tegels toe
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(this.map);

    // Haal de GeoJSON data op
    fetch('https://www.webuildinternet.com/articles/2015-07-19-geojson-data-of-the-netherlands/townships.geojson')
        .then(response => response.json())
        .then(data => {
          this.rawGeoJson = data;
          this.addGeoJsonToMap();
        })
        .catch(error => console.error('Error fetching GeoJSON data:', error));
  },
  methods: {
    addGeoJsonToMap() {
      if (!this.rawGeoJson || !this.fuse || Object.keys(this.gemeentenVotesMap).length === 0) {
        return;
      }

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
          fillColor: this.getColor(winningParty),
          weight: 2,
          opacity: 1,
          color: 'white',
          dashArray: '3',
          fillOpacity: 0.7
        };
      };

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

      if (this.geojson) {
        this.map.removeLayer(this.geojson);
      }

      this.geojson = L.geoJson(this.rawGeoJson, {
        style: style,
        onEachFeature: onEachFeature
      }).addTo(this.map);
    },
    getColor(party) {
      return party === 'NSC' ? '#FFDD00' :
          party === 'PVV' ? '#1E90FF' :
              party === 'GLPVDA' ? '#FF0000' :
                  party === 'SP' ? '#FF3300' :
                      party === 'VVD' ? '#003399' :
                          party === 'D66' ? '#32CD32' :
                              party === 'BBB' ? '#4CAF50' :
                                  party === 'CDA' ? '#006400' :
                                      party === 'FVD' ? '#800020' :
                                          party === 'PVDD' ? '#00A86B' :
                                              '#CCCCCC'; // Grijs als default
    },
    normalize(name) {
      return name.toLowerCase().replace(/[^a-z0-9]/g, '');
    }
  },
  watch: {
    fuse: 'addGeoJsonToMap',
    gemeentenVotesMap: 'addGeoJsonToMap'
  },
  beforeDestroy() {
    if (this.geojson) {
      this.map.removeLayer(this.geojson);
    }
    if (this.map) {
      this.map.remove();
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
