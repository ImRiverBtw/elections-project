<template>

  <div class="componentDivider">
    <p> Ontdek meer: </p>
  </div>

  <div class="redirectTop">

    <h2 class="redirectTopTitle"> Verkiezingsuitslagen </h2>
    <hr class="title-seperator">

    <p class="redirectParagraph"> Bekijk hier de uitslagen van de tweede kamer verkiezingen van 2023 </p>
  </div>

  <div class="redirectCardsWrapper">

<!--    Voor elke item in de array wordt er een "kaart" gemaakt -->
    <redirect-card class="redirectCard" v-for="x in electionResultsComponents" >

      <p class="redirectCardTitle">{{x.name}}</p>
      <hr class="title-seperator">
      <img class="card-image" v-bind:src="x.imgPath" alt="picture" @click="redirectRouter(x.routeUrl)">

    </redirect-card>

    </div>

  <div class="redirectTop">

    <h2 class="redirectTopTitle"> Forum </h2>
    <hr class="title-seperator" id="ts-forum">

    <p class="redirectParagraph"> Bekijk en neem deel aan discussies om een proef <br> te krijgen van de meningen onder de kiezers
    </p>

  </div>

  <div class="redirectCardsWrapper">

    <redirect-card class="redirectCard" v-for="x in forumComponents">

      <p class="redirectCardTitle" @click="redirectRouter(x.routeUrl)" >{{x.name}}</p>
      <hr class="title-seperator">
      <img class="card-image" v-bind:src="x.imgPath" alt="picture" @click="redirectRouter(x.routeUrl)">

    </redirect-card>

  </div>

</template>

<script>

import RedirectCard from "@/components/RedirectCard.vue";

export default {

  components: {RedirectCard},

  data() {
    return {

      electionResultsComponents: [
        {name: 'Uitslagenkaart', imgPath: 'src/assets/img/redirect-electionresults-1.png', routeUrl: '/electionresults/resultsmap' },
        {name: 'Zoek je gemeente', imgPath: 'src/assets/img/redirect-electionresults-2.svg', routeUrl: '/electionresults/municipalityfinder'},
        {name: 'Totaaltabel', imgPath: 'src/assets/img/redirect-electionresults-3.png', routeUrl: '/electionresults/totaltable'},
        {name: 'Zetelverdeling', imgPath: 'src/assets/img/redirect-electionresults-4.png', routeUrl: '/electionresults/seatdistribution'},
        {name: 'Meerderheidszoeker', imgPath: 'src/assets/img/redirect-electionresults-4.png', routeUrl: '/electionresults/majoritycalculator',
        },
      ],

      forumComponents: [
        {name: 'Alle posts', imgPath: 'src/assets/img/redirect-forum-1.svg', routeUrl: '/test' },
        {name: 'Actieve posts', imgPath: 'src/assets/img/redirect-forum-2.svg', routeUrl: '/test'},
        ],
    }
  },

  methods: {

    redirectRouter(routeUrl) {
      this.$router.push(routeUrl)
    },

  }
};

</script>

<style scoped>

.redirectTopTitle {
  font-size: 1.5rem;
  line-height: 2.25rem;
  font-weight: 400;
}

.title-seperator {
  border: none;
  height: 3px;
  background-color: #004494;
  width: 15%;
  border-radius: 100px;
}

.redirectParagraph {
  margin-top: 1%;
  font-size: 16px;
  font-weight: 400;
}

.redirectCardsWrapper {
  padding: 20px;
  display: flex;
  flex-wrap: wrap;
  max-width: 1000px;
}

.card-image{
  padding: 10px;
  border-radius: 20px;
  width: 290px;
  height: 190px;
  object-fit: cover;
  margin-top: 3%;
  transition: background-color linear 0.3s;
}

.card-image:hover{
  cursor: pointer;
  background-color: #E7EDFA;
}

.card-image {
  width: 250px;
  height: 190px;
  object-fit: contain;
}

.componentDivider {
  text-align: center;
  font-size: 22px;
  margin-top: 2%;
}


</style>