package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.Affiliation;
import com.election.electionbackend.entity.Constituency;
import com.election.electionbackend.entity.PollingStation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
@Transactional
public class PollingStationRepository {
    @PersistenceContext
    private EntityManager em;  // Injects the EntityManager to interact with the database.

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ConstituencyRepository constituencyRepository;

    /**
     * Finds a PollingStation by its ID.
     *
     * @param id the ID of the PollingStation.
     * @return the PollingStation entity or null if not found.
     */
    public Optional<PollingStation> findById(Long id) {
        PollingStation pollingStation = em.find(PollingStation.class, id);
        return Optional.ofNullable(pollingStation);
    }

    /**
     * Retrieves all PollingStation records from the database.
     *
     * @return a list of all PollingStation entities.
     */
    public List<PollingStation> findAll() {
        return em.createQuery("from PollingStation", PollingStation.class).getResultList();
    }

    /**
     * Finds the affiliation with the highest number of votes in a specific polling station.
     *
     * @param pollingStationId the ID of the polling station.
     * @return an Object array where:
     * - result[0]: the affiliation entity.
     * - result[1]: the total votes for that affiliation.
     */
    public Object[] getBiggestAffiliation(Long pollingStationId) {
        Optional<PollingStation> pollingStation = findById(pollingStationId);
        Query query = em.createQuery(
                "SELECT psc.candidate.affiliation, SUM(psc.votes) " +
                        "FROM PollingStationCandidate psc " +
                        "WHERE psc.pollingStation = :pollingStation " +
                        "GROUP BY psc.candidate.affiliation " +
                        "ORDER BY SUM(psc.votes) DESC"
        );
        query.setParameter("pollingStation", pollingStation);
        query.setMaxResults(1);

        Object[] result = (Object[]) query.getSingleResult();

        logger.info(Arrays.toString(result));
        return result;
    }
//TODO: save method


    public void insertDummyData() {
        String[] pollingStationNames = new String[]{
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
        };
        List<Affiliation> affiliations = em.createQuery("from Affiliation", Affiliation.class).getResultList();

        if (affiliations.isEmpty()) {
            throw new IllegalStateException("No affiliations found in the database");
        }

        Random random = new Random();

        for (String pollingStationName : pollingStationNames) {
            Affiliation affiliation = affiliations.get(random.nextInt(affiliations.size()));

            Constituency constituency = new Constituency("Constituency for " + pollingStationName);
            em.persist(constituency);

            PollingStation pollingStation = new PollingStation(constituency, pollingStationName, affiliation);
            em.persist(pollingStation);
        }
    }
}

