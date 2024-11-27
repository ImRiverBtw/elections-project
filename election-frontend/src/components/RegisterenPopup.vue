<template>
  <div class="popup" v-if="visible">
    <div class="popup-content">
      <div class="popup-header">
        <h2>Register</h2>
        <button class="Button Close" @click="close">Close</button>
      </div>
      <div class="line"></div>
      <div class="inputBox">

        <div class="inputText username">Gebruikersnaam</div>
        <input class="inputField username" v-model="registerUsername" placeholder="Gebruikersnaam" />
        <small v-if="usernameError" class="text-error">{{ usernameError }}</small>

        <div class="inputText email">Email-addres</div>
        <input class="inputField email" v-model="registerEmail" placeholder="Email-addres" />
        <small v-if="emailError" class="text-error">{{ emailError }}</small>
        <small v-if="registerEmail && !isValidEmail(registerEmail)" class="text-error">Vul een geldig e-mailadres in</small>

        <div class="inputText password">Wachtwoord</div>
        <input class="inputField password" type="password" v-model="registerPassword" placeholder="Wachtwoord" />
        <small v-if="registerPassword && !isValidPassword(registerPassword)" class="text-error">Wachtwoord moet minimaal 8 karakters bevatten en één cijfer en één speciaal teken</small>

        <div class="inputText confirm-password">Confirm Wachtwoord</div>
        <input class="inputField confirm-password" type="password" v-model="registerConfirmPassword" placeholder="Confirm Wachtwoord" />

        <button class="Button Submit Register" @click="submit">Register</button>
      </div>
    </div>
  </div>
</template>

<script>
import { register, login } from '../models/authService.js';

export default {
  props: {
    visible: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      registerUsername: '',
      registerEmail: '',
      registerPassword: '',
      registerConfirmPassword: '',
      emailError: null,
      usernameError: null,
    };
  },
  computed: {
    isValidEmail() {
      return (email) => {
        const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return emailRegex.test(email);
      };
    },
    isValidPassword() {
      return (password) => {
        const passwordRegex = /^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,}$/;
        return passwordRegex.test(password);
      }
    }
  },
  methods: {
    close() {
      this.$emit('close');
    },
    async submit() {
      this.emailError = null;
      this.usernameError = null;

      if (!this.isValidEmail(this.registerEmail)) {
        alert('Ongeldig emailadres');
        return;
      }

      if (!this.isValidPassword(this.registerPassword)) {
        alert('Wachtwoord moet minimaal 8 karakters bevatten en één cijfer en één speciaal teken');
        return;
      }

      if (this.registerPassword !== this.registerConfirmPassword) {
        alert('De wachtwoorden komen niet overeen');
        return;
      }

      try {
        // Registreren
        const response = await register(this.registerUsername, this.registerEmail, this.registerPassword);
        // Controleer of de registratie succesvol is
        if (response && response.message === "User registered successfully") {
          const loginResponse = await login(this.registerEmail, this.registerPassword);
          console.log('Login succesvol na registratie:', loginResponse);
          this.$emit('close');
        }
      } catch (error) {
        console.error('Fout tijdens registratie of login:', error);

        const errorMessage = error?.message || 'Er is een onverwachte fout opgetreden.';

        if (errorMessage.includes('gebruikersnaam')) {
          this.usernameError = 'Deze gebruikersnaam is al in gebruik.';
        } else if (errorMessage.includes('e-mail')) {
          this.emailError = 'Dit e-mailadres is al in gebruik.';
        }
         else {
          alert(errorMessage);
         }
      }
    },
  },
};
</script>

<style scoped>
.text-error {
  font-size: 12px;
  color: red;
}
</style>
