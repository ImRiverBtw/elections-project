<script setup>
import {inject, nextTick} from "vue";
import router from "@/router/index.js";

const sessionService = inject("sessionService")

const username = sessionService.getUsername()
const isAdmin = sessionService.isAdmin()
const logout = async () => {
  await sessionService.logout()
  await router.push("/login");
}
</script>

<template>
<h1>HELLO PROFILE?!?!?!?!</h1>
  <p>hello {{username}}</p>
  <p>Admin: {{isAdmin}}</p>
  <button @click="logout">Log out</button>
</template>

<style scoped>

</style>