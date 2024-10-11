<template>
  <div class="electionresults-container">
    <div class="content">
      <h1>Verkiezingsuitslagen 2023</h1>
      <router-view />
    </div>
    <div class="election-results-nav">
      <h3>Op deze pagina:</h3>
      <router-link to="/electionresults/resultsmap" exact-active-class="active">Uitslagenkaart</router-link>
      <router-link to="/electionresults/majoritycalculator" exact-active-class="active">Meerderheidszoeker</router-link>
      <router-link to="/electionresults/municipalityfinder" exact-active-class="active">Zoek je gemeente</router-link>
      <router-link to="/electionresults/seatdistribution" exact-active-class="active">Zetelverdeling</router-link>
      <router-link to="/electionresults/totaltable" exact-active-class="active">Totaaltabel</router-link>
    </div>
  </div>
</template>
<script>
export default {
  name: "ElectionResults",
  data(){
    return{

    }
  }
}
</script>
<style scoped>
.electionresults-container{
  display: flex;
  flex-direction: row;
}

.content{
  max-width: 1100px;
  width: 100%;
  box-sizing: border-box;
  margin-right: 8px;
}
h3{
  padding-bottom: 18px;
}
.election-results-nav{
  display: flex;
  flex-direction: column;
  margin-left: auto;
}
.election-results-nav a {
  font-size: 16px;
  padding: 18px;
  position: relative;
  transition: background-color 0.3s ease;
  text-decoration: none;
  color: black;
}
.election-results-nav a::before {
  content: "";
  position: absolute;
  left: -3px;
  top: 0;
  height: 100%;
  width: 3px;
  background-color: #004494
}
.election-results-nav a.active {
  background-color: #CCD5EF;
}
.election-results-nav a.active::before {
  background-color: #FFCC00;
}
</style>