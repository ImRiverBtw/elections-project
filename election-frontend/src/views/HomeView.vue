<script setup>

import WelcomeComponent from "@/components/WelcomeComponent.vue";
import ElectionExplanationComponent from "@/components/ElectionExplainComponent.vue";
import WelcomeRedirectComponent from "@/components/RedirectComponent.vue";

</script>

<template>
  <main>

<!--    component met titel en beschrijving -->
    <welcome-component> </welcome-component>

<!--    component over hoe de verkiezingen werken-->
    <election-explanation-component> </election-explanation-component>

<!--    Component met alle "kaarten" van functionaliteiten waar de gebruiker op kan klikken om van component te veranderen -->
    <welcome-redirect-component></welcome-redirect-component>
  </main>
</template>
