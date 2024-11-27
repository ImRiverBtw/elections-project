<script setup>
// import {RouterLink, RouterView} from 'vue-router'
import ECHeader from "@/components/ECHeader.vue";
import ECFooter from "@/components/ECFooter.vue";
import { ref, onMounted } from 'vue';

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

// Automatically set dark mode based on device settings if no user preference is saved
onMounted(() => {
  if (localStorage.getItem('darkMode') === null) {
    const prefersDarkScheme = window.matchMedia('(prefers-color-scheme: dark)').matches;
    if (prefersDarkScheme) {
      document.body.classList.add('dark-mode');
      isDarkMode.value = true;
    }
  }
});

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
  <ECHeader />
  <div class="container">
    <RouterView/>
  </div>
  <ECFooter>
    <div class="container">
      <RouterView/>
    </div>
  </ECFooter>
</template>

<style scoped>

</style>
