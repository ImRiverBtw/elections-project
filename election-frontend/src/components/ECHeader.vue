<script setup>
import { ref } from 'vue';


// Reactive variable for dark mode state
const isDarkMode = ref(localStorage.getItem('darkMode') === 'true');

// Function to toggle dark mode
const toggleDarkMode = () => {
  isDarkMode.value = !isDarkMode.value;

  if (isDarkMode.value) {
    document.body.classList.add('dark-mode'); // Add a class to the <body>
    localStorage.setItem('darkMode', 'true'); // Save the preference
  } else {
    document.body.classList.remove('dark-mode');
    localStorage.setItem('darkMode', 'false');
  }
};

// Apply dark mode on page load if saved preference is dark mode
if (isDarkMode.value) {
  document.body.classList.add('dark-mode');
}

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
      <button class="dark-mode-toggle" @click="toggleDarkMode">
        {{ isDarkMode ? 'Light Mode' : 'Dark Mode' }}
      </button>
    </div>
  </div>

  <div class="topnav" id="myTopnav">
    <div class="containernav">
      <RouterLink to="/">Home</RouterLink>
      <RouterLink to="/electionresults/resultsmap">Verkiezingsuitslagen</RouterLink>
      <RouterLink to="/forum">Forum</RouterLink>
      <RouterLink to="/login">Login/resgister</RouterLink>
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

.dark-mode-toggle {
  background-color: #004494;
  color: white;
  border: none;
  padding: 15px 20px;
  cursor: pointer;
  position: absolute;
  right: 0;
  top: 120px;
}

.dark-mode-toggle:hover {
  background-color: #80bfff;
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