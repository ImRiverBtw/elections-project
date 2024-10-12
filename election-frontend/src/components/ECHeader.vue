<script setup>
function myFunction() {
  var x = document.getElementById("myTopnav");
  if (x.className === "topnav") {
    x.className += " responsive";
  } else {
    x.className = "topnav";
  }
}
</script>

<template>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

  <div class="header-top">
    <div class="container">
      <img alt="Vue logo" src="../assets/img/logo-ec-big.svg">
      <img class="imgright" alt="image right" src="../assets/img/right_header_image.svg">
    </div>
  </div>

  <div class="topnav" id="myTopnav">
    <div class="containernav">
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/electionresults">Verkiezingsuitslagen</RouterLink>
      <RouterLink to="/test">Forum</RouterLink>
      <a href="javascript:void(0);" class="icon" @click="myFunction">
        <i class="fa fa-bars"></i>
      </a>
    </div>
  </div>
</template>

<style scoped>
.containernav {
  max-width: 1100px;
  margin: 0 auto;
}
.topnav {
  overflow: hidden;
  background-color: #004494;
}

.container{
  display: flex;
  justify-content: space-between;
  position: relative;
}
.imgright{
  height: 127px;
  position: absolute;
  top: 0;
  right: 0;
  z-index: -1;

}

.topnav a {
  float: left;
  display: block;
  color: #f2f2f2;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
}

.topnav a:hover {
  background-color: #B9C5E9;
  color: #26324B;
}

.topnav a.router-link-active {
  background-color: #B9C5E9;
  color: white;
}

.topnav .icon {
  display: none;
}

@media screen and (max-width: 600px) {
  .topnav a:not(:first-child) {
    display: none;
  }

  .topnav a.icon {
    float: right;
    display: block;
  }
}

@media screen and (max-width: 600px) {
  .topnav.responsive {
    position: relative;
  }

  .topnav.responsive .icon {
    position: absolute;
    right: 0;
    top: 0;
  }

  .topnav.responsive a {
    float: none;
    display: block;
    text-align: left;
  }
}
</style>