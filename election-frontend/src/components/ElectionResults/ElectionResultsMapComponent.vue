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
      geojson: null,  // GeoJSON data voor de kaart
      gemeentenVotesMap: {}, // Opslag van willekeurige partijen per gemeente
      gekozenGemeentens: new Set(),
      fuse: null,
    };
  },
  created() {
    // Partijen voor de verkiezing
    const partijen = [
      "NSC",
      "PVV",
      "GLPVDA",
      "SGP",
      "VVD",
      "D66"
    ];

    // Lijst van gemeenten (verkorte versie, voeg alle gemeenten toe indien nodig)
    const gemeenten = [
      "Aa en Hunze",
      "Aalsmeer",
      "Aalten",
      "Achtkarspelen",
      "Alblasserdam",
      "Albrandswaard",
      "Alkmaar",
      "Almelo",
      "Almere",
      "Alphen aan den Rijn",
      "Alphen-Chaam",
      "Altena",
      "Ameland",
      "Amersfoort",
      "Amstelveen",
      "Amsterdam",
      "Apeldoorn",
      "Arnhem",
      "Assen",
      "Asten",
      "Baarle-Nassau",
      "Baarn",
      "Barendrecht",
      "Barneveld",
      "Beek",
      "Beekdaelen",
      "Beesel",
      "Berg en Dal",
      "Bergeijk",
      "Bergen (L)",
      "Bergen NH",
      "Bergen op Zoom",
      "Berkelland",
      "Bernheze",
      "Best",
      "Beuningen",
      "Beverwijk",
      "Bladel",
      "Blaricum",
      "Bloemendaal",
      "Bodegraven-Reeuwijk",
      "Boekel",
      "Borger-Odoorn",
      "Borne",
      "Borsele",
      "Boxtel",
      "Breda",
      "Bronckhorst",
      "Brummen",
      "Brunssum",
      "Bunnik",
      "Bunschoten",
      "Buren",
      "Capelle aan den IJssel",
      "Castricum",
      "Coevorden",
      "Cranendonck",
      "Culemborg",
      "Dalfsen",
      "Dantumadiel",
      "De Bilt",
      "De Fryske Marren",
      "De Ronde Venen",
      "De Wolden",
      "Delft",
      "Den Haag",
      "Den Helder",
      "Deurne",
      "Deventer",
      "Diemen",
      "Dijk en Waard",
      "Dinkelland",
      "Doesburg",
      "Doetinchem",
      "Dongen",
      "Dordrecht",
      "Drechterland",
      "Drimmelen",
      "Dronten",
      "Druten",
      "Duiven",
      "Echt-Susteren",
      "Edam-Volendam",
      "Ede",
      "Eemnes",
      "Eemsdelta",
      "Eersel",
      "Eijsden-Margraten",
      "Eindhoven",
      "Elburg",
      "Emmen",
      "Enkhuizen",
      "Enschede",
      "Epe",
      "Ermelo",
      "Etten-Leur",
      "fictief",
      "Geertruidenberg",
      "Geldrop-Mierlo",
      "Gemert-Bakel",
      "Gennep",
      "Gilze en Rijen",
      "Goeree-Overflakkee",
      "Goes",
      "Goirle",
      "Gooise Meren",
      "Gorinchem",
      "Gouda",
      "Groningen",
      "Gulpen-Wittem",
      "Haaksbergen",
      "Haarlem",
      "Haarlemmermeer",
      "Halderberge",
      "Hardenberg",
      "Harderwijk",
      "Hardinxveld-Giessendam",
      "Harlingen",
      "Hattem",
      "Heemskerk",
      "Heemstede",
      "Heerde",
      "Heerenveen",
      "Heerlen",
      "Heeze-Leende",
      "Heiloo",
      "Hellendoorn",
      "Helmond",
      "Hendrik-Ido-Ambacht",
      "Hengelo",
      "'s-Hertogenbosch",
      "Het Hogeland",
      "Heumen",
      "Heusden",
      "Hillegom",
      "Hilvarenbeek",
      "Hilversum",
      "Hoeksche Waard",
      "Hof van Twente",
      "Hollands Kroon",
      "Hoogeveen",
      "Hoorn",
      "Horst aan de Maas",
      "Houten",
      "Huizen",
      "Hulst",
      "IJsselstein",
      "Kaag en Braassem",
      "Kampen",
      "Kapelle",
      "Katwijk",
      "Kerkrade",
      "Koggenland",
      "Krimpen aan den IJssel",
      "Krimpenerwaard",
      "Laarbeek",
      "Land van Cuijk",
      "Landgraaf",
      "Landsmeer",
      "Lansingerland",
      "Laren",
      "Leeuwarden",
      "Leiden",
      "Leiderdorp",
      "Leidschendam-Voorburg",
      "Lelystad",
      "Leudal",
      "Leusden",
      "Lingewaard",
      "Lisse",
      "Lochem",
      "Loon op Zand",
      "Lopik",
      "Losser",
      "Maasdriel",
      "Maasgouw",
      "Maashorst",
      "Maassluis",
      "Maastricht",
      "Medemblik",
      "Meerssen",
      "Meierijstad",
      "Meppel",
      "Middelburg",
      "Midden-Delfland",
      "Midden-Drenthe",
      "Midden-Groningen",
      "Moerdijk",
      "Molenlanden",
      "Montferland",
      "Montfoort",
      "Mook en Middelaar",
      "Neder-Betuwe",
      "Nederweert",
      "Nieuwegein",
      "Nieuwkoop",
      "Nijkerk",
      "Nijmegen",
      "Nissewaard",
      "Noardeast-Fryslân",
      "Noord-Beveland",
      "Noordenveld",
      "Noordoostpolder",
      "Noordwijk",
      "Nuenen, Gerwen en Nederwetten",
      "Nunspeet",
      "Oegstgeest",
      "Oirschot",
      "Oisterwijk",
      "Oldambt",
      "Oldebroek",
      "Oldenzaal",
      "Olst-Wijhe",
      "Ommen",
      "Oost Gelre",
      "Oosterhout",
      "Ooststellingwerf",
      "Oostzaan",
      "Opmeer",
      "Opsterland",
      "Oss",
      "Oude IJsselstreek",
      "Ouder-Amstel",
      "Oudewater",
      "Overbetuwe",
      "Papendrecht",
      "Peel en Maas",
      "Pekela",
      "Pijnacker-Nootdorp",
      "Purmerend",
      "Putten",
      "Raalte",
      "Reimerswaal",
      "Renkum",
      "Renswoude",
      "Reusel-De Mierden",
      "Rheden",
      "Rhenen",
      "Ridderkerk",
      "Rijssen-Holten",
      "Rijswijk",
      "Roerdalen",
      "Roermond",
      "Roosendaal",
      "Rotterdam",
      "Rozendaal",
      "Rucphen",
      "Schagen",
      "Scherpenzeel",
      "Schiedam",
      "Schiermonnikoog",
      "Schouwen-Duiveland",
      "Simpelveld",
      "Sint-Michielsgestel",
      "Sittard-Geleen",
      "Sliedrecht",
      "Sluis",
      "Smallingerland",
      "Soest",
      "Someren",
      "Son en Breugel",
      "Stadskanaal",
      "Staphorst",
      "Stede Broec",
      "Steenbergen",
      "Steenwijkerland",
      "Stein",
      "Stichtse Vecht",
      "Súdwest-Fryslân",
      "Terneuzen",
      "Terschelling",
      "Texel",
      "Teylingen",
      "Tholen",
      "Tiel",
      "Tilburg",
      "Tubbergen",
      "Twenterand",
      "Tynaarlo",
      "Tytsjerksteradiel",
      "Uitgeest",
      "Uithoorn",
      "Urk",
      "Utrecht",
      "Utrechtse Heuvelrug",
      "Vaals",
      "Valkenburg aan de Geul",
      "Valkenswaard",
      "Veendam",
      "Veenendaal",
      "Veere",
      "Veldhoven",
      "Velsen",
      "Venlo",
      "Venray",
      "Vijfheerenlanden",
      "Vlaardingen",
      "Vlieland",
      "Vlissingen",
      "Voerendaal",
      "Voorne aan Zee",
      "Voorschoten",
      "Voorst",
      "Vught",
      "Waadhoeke",
      "Waalre",
      "Waalwijk",
      "Waddinxveen",
      "Wageningen",
      "Wassenaar",
      "Waterland",
      "Weert",
      "West Betuwe",
      "West Maas en Waal",
      "Westerkwartier",
      "Westerveld",
      "Westervoort",
      "Westerwolde",
      "Westland",
      "Weststellingwerf",
      "Wierden",
      "Wijchen",
      "Wijdemeren",
      "Wijk bij Duurstede",
      "Winterswijk",
      "Woensdrecht",
      "Woerden",
      "Wormerland",
      "Woudenberg",
      "Zaanstad",
      "Zaltbommel",
      "Zandvoort",
      "Zeewolde",
      "Zeist",
      "Zevenaar",
      "Zoetermeer",
      "Zoeterwoude",
      "Zuidplas",
      "Zundert",
      "Zutphen",
      "Zwartewaterland",
      "Zwijndrecht",
      "Zwolle"
    ];

    gemeenten.forEach(gemeente => {
      this.gemeentenVotesMap[gemeente] = this.shuffleArray([...partijen])[0];
    });

    const normalizedGemeenten = gemeenten.map(gemeente => ({
      name: this.normalize(gemeente),
      original: gemeente
    }))

    const fuseOptions = {
      keys: ['name'],
      threshold: 0.4 //percentatie hoe erg de namen op elkaar mogelijk lijken.
    };
    this.fuse = new Fuse(normalizedGemeenten, fuseOptions);
  },
  mounted() {
    // boundary van de kaart
    let bounds = [[50.5, 3.2], [53.7, 7.2]];

    let map = L.map('map', {
      center: [52.1, 5.3],  // Centraal op Nederland
      zoom: 7,              // Standaard zoomniveau
      minZoom: 7            // Minimale zoomniveau
    });

    // Stel zichtbare grenzen in op Nederland
    map.setMaxBounds(bounds);

    // Voeg OpenStreetMap tegels toe
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    // Functie om kleur te bepalen op basis van de winnende partij
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

      let normalizedGemeenteNaam = this.normalize(gemeenteNaam)
      let winningParty = this.gemeentenVotesMap[gemeenteNaam] || this.gemeentenVotesMap[normalizedGemeenteNaam];

      if (this.gemeentenVotesMap[gemeenteNaam]) {
        directMatch.push(gemeenteNaam);
      }

      if (!winningParty && !this.gekozenGemeentens.has(gemeenteNaam)) {
        const fuzzyResult = this.fuse.search(this.normalize(gemeenteNaam));

        if (fuzzyResult.length > 0) {
          const bestMatch = fuzzyResult[0].item.original;
          fuzzyMatch.push(gemeenteNaam + " & " + bestMatch);

          winningParty = this.gemeentenVotesMap[bestMatch];
        } else {
          noMatch.push(gemeenteNaam);
        }
      }

      if (winningParty) {
        this.gekozenGemeentens.add(gemeenteNaam);
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
