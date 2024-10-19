<template>

  <div class="explanationTop">

    <h2 class="explanationTitle"> {{ title }} </h2>
    <hr class="title-seperator">

  </div>

  <div class="explanationCardWrapper">

<!--    Info wordt uit steps array gehaald in data() -->

    <div class="explanationCard" id="explanationCard1">
      <img src="../assets/img/explanation-1.svg" alt="Image 1" class="box-image">
      <h2 class="box-title"> {{ steps[0].title }}</h2>
      <p class="box-description"> {{ steps[0].description }}</p>
    </div>

    <div class="card-seperator">
      <div class="vlTop1"></div>
      <div class="vlBreak"></div>
      <div class="vlBottom1"></div>
    </div>

    <hr class="title-seperator1">

    <div class="explanationCard" id="explanationCard2">
      <img src="../assets/img/explanation-2.svg" alt="Image 1" class="box-image">
      <h2 class="box-title"> {{ steps[1].title }}</h2>
      <p class="box-description"> {{ steps[1].description }}</p>
    </div>

    <div class="card-seperator">
      <div class="vlTop2"></div>
      <div class="vlBreak"></div>
      <div class="vlBottom2"></div>
    </div>

    <hr class="title-seperator1">

    <div class="explanationCard" id="explanationCard3">
      <img src="../assets/img/explanation-3.svg" alt="Image 1" class="box-image">
      <h2 class="box-title"> {{ steps[2].title }}</h2>
      <p class="box-description"> {{ steps[2].description }}</p>
    </div>

  </div>

  <div class="explanationBottom">

    <hr class="page-seperator">

  </div>

</template>

<script>
export default {

  data() {

    return {

      title: "Van stembiljet naar kabinet",

      steps: [
          {
            id: 1,
            title: "Campagne & Stemmen",
            description: "Politieke partijen voeren campagne om hun programma’s te presenteren en kiezers te " +
                "overtuigen. Op de verkiezingsdag gaan stemgerechtigde burgers naar een stembureau om hun stem uit " +
                "te brengen op hun favoriete partij.",
          },

          {
            id: 2,
            title: "Uitslag & Zetelverdeling",
            description: "Na sluiting van de stembureaus worden de stemmen handmatig geteld. Op basis van het aantal " +
                "behaalde stemmen worden de 150 zetels in de Tweede Kamer verdeeld, zodat elke partij evenredig " +
                "vertegenwoordigd is.",
          },

          {
            id: 3,
            title: "Regeringsformatie",
            description: "Omdat geen enkele partij meestal een meerderheid behaalt, gaan partijen onderhandelen om " +
                "een coalitie te vormen. Ze maken samen een regeerakkoord, dat de basis vormt voor het beleid van " +
                "de nieuwe regering.",
          },

      ]
    };
  },

  methods: {
  },

}

</script>


<style scoped>

.explanationTop {
  margin-top: 3%;
}

.explanationTitle {
  font-size: 1.5rem;
  line-height: 2.25rem;
  font-weight: 400;
}

.title-seperator {
  border: none;
  height: 3px;
  background-color: #004494;
  width: 16%;
  border-radius: 100px;
}

.title-seperator1 {
  border: none;
  height: 3px;
  background-color: #004494;
  width: 16%;
  border-radius: 100px;
  display: none;
}

.explanationCardWrapper {
  margin-top: 3%;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  flex-wrap: wrap;
  //border: 1px solid blue;
}

.explanationCard {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 30%;
  padding: 20px;
  box-sizing: border-box;
  text-align: center;
  //background-color: #ff0000;

  //border: 1px solid red;

}

.box-image {
  width: 290px;
  height: 190px;
  object-fit: contain;
  margin-bottom: 20%;
}

.box-title {
  font-size: 18px;
  margin-bottom: 7%;
}


.box-description {
  font-size: 16px;
}

.card-seperator{
  display: block;
//border: 1px solid red;

}

.vlTop1 {
  border-left: 3px solid #ffcc00;
  height: 130px;
  border-radius: 20px;
}
.vlBottom1 {
  border-left: 3px solid #004594;
  height: 250px;
  border-radius: 20px;
}
.vlBreak {
  border-left: 3px solid #FCFCFC;
  height: 120px;
  border-radius: 20px;
}

.vlTop2 {
  border-left: 3px solid #004594;
  height: 250px;
  border-radius: 20px;
}

.vlBottom2 {
  border-left: 3px solid #ffcc00;
  height: 130px;
  border-radius: 20px;
}

.explanationBottom {
  display: flex;
  justify-content: center;
}

.page-seperator {
  border: none;
  height: 3px;
  background-color: #ffcc00;
  width: 20%;
  border-radius: 20px;
  margin-right: -30px;
}

/* Media query for smaller screens */
@media (max-width: 900px) {
  .explanationCard {
    width: 100%;
  }

  .title-seperator1 {
    display: block;
    margin: 0 auto;
    text-align: center
  }

  .card-seperator {
    display: none;
  }



}


</style>