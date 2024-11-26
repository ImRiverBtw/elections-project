<template>
  <div class="popup" v-if="visible">
    <div class="popup-content">
      <div class="popup-header">
        <h2>Login</h2>
        <button class="Button Close" @click="close">Close</button>
      </div>
      <div class="line"></div>
      <div class="inputBox">
        <div class="inputText email">Email-addres</div>
        <input class="inputField email" v-model="loginEmail" placeholder="Email-addres" />

        <div class="inputText password">Wachtwoord</div>
        <input class="inputField password" v-model="loginPassword" placeholder="Wachtwoord" />

        <button class="Button submitLogin" @click="submit">Login</button>
      </div>
    </div>
  </div>
</template>

<script>
import { login } from '../models/authService.js';

export default {
  props: {
    visible: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      loginEmail: '',
      loginPassword: '',
    };
  },
  methods: {
    close() {
      this.$emit('close');
    },
    async submit() {
      try {
        const loginResponse  = await login(this.loginEmail, this.loginPassword);
        console.log("Ingelogd succesful: ", loginResponse);
        this.close();
      } catch (error) {
        console.log('Login mislukt: ' + error);
      }
    },
  },
};
</script>
