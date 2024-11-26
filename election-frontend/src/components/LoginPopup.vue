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
        <small v-if="emailError" class="text-error">{{ emailError }}</small>

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
      emailError: null,
    };
  },
  methods: {
    close() {
      this.$emit('close');
    },
    async submit() {
      this.emailError = null;

      try {
        console.log("Ingelogd succesful");
        this.close();
      } catch (error) {
        if (error.message.includes('Invalid email or password')) {
          this.emailError = 'E-mailadres of wachtwoord is onjuist.';
        } else {
          this.emailError = 'Er is een probleem opgetreden. Probeer het later opnieuw.';
        }
      }
    },
  },
};
</script>

<style>
.text-error {
  font-size: 12px;
  color: red;
}
</style>
