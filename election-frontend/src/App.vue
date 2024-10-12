<script setup>
// import {RouterLink, RouterView} from 'vue-router'
import ECHeader from "@/components/ECHeader.vue";
import ECFooter from "@/components/ECFooter.vue";
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
