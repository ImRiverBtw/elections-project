package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class TownshipRepository {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    ConstituencyRepository constituencyRepository;

    @Autowired
    private AffiliationRepository affiliationRepository;

    public Township findById(Long id) {
        return em.find(Township.class, id);
    }

    public List<Township> findAll() {
        return em.createQuery("from Township", Township.class).getResultList();
    }

    public void insertDummyData() {
        String[] affiliationNames = {"PVV", "GLPVDA", "VVD", "NSC", "D66", "BBB", "CDA", "SP", "FVD", "PVDD"};
        String[] townshipsName = new String[]{
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

        for (String name : affiliationNames) {
            em.persist(new Affiliation(name));
        }

        for (String townshipName : townshipsName) {
            Constituency constituency = new Constituency("Constituency for " + townshipName);
            em.persist(constituency);

            Township township = new Township(constituency, townshipName);
            em.persist(township);

            long candidateNumber = 1L; // Candidate numbering starts at 1
            for (String affiliationName : affiliationNames) {
                Affiliation affiliation = em.createQuery(
                                "SELECT a FROM Affiliation a WHERE a.name = :name", Affiliation.class)
                        .setParameter("name", affiliationName)
                        .getSingleResult();

                Candidate candidate = new Candidate(candidateNumber++, "Candidate of " + affiliationName, affiliation);
                em.persist(candidate);

                int votes = (int) (Math.random() * 1000);
                TownshipCandidate townshipCandidate = new TownshipCandidate(township, candidate, votes);
                em.persist(townshipCandidate);
            }

            String largestParty = township.getLargestParty();
            System.out.println("Largest party in " + townshipName + ": " + largestParty);
        }
    }
}
