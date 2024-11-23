<template>
  <div class="popup" v-if="visible">
    <div class="popup-content">
      <div class="popup-header">
        <h2>Register</h2>
        <button class="Button Close" @click="close">Close</button>
      </div>
      <div class="line"></div>
      <div class="inputBox">
        <div v-if="error" class="password-error">
          <p>{{ passwordError }}</p>
          <p>{{ usernameError }}</p>
        </div>

        <div class="inputText username">Gebruikersname</div>
        <input class="inputField username" v-model="registerUsername" placeholder="Gebruikersname" />

        <div class="inputText email">Email-addres</div>
        <input class="inputField email" v-model="registerEmail" placeholder="Email-addres" />

        <div class="inputText password">Wachtwoord</div>
        <input class="inputField password" v-model="registerPassword" placeholder="Wachtwoord" />

        <div class="inputText confirm-password">Confirm Wachtwoord</div>
        <input class="inputField confirm-password" v-model="registerConfirmPassword" placeholder="Confirm Wachtwoord" />

        <button class="Button submitRegister" @click="submit">Register</button>
      </div>
    </div>
  </div>
</template>

<script>
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
      passwordError: '',
      usernameError: '',
      error: false,
    };
  },
  methods: {
    close() {
      this.$emit('close');
    },
    async submit() {
      if (this.registerPassword === this.registerConfirmPassword) {
        const registerData = {
          username: this.registerUsername,
          email: this.registerEmail,
          password: this.registerPassword,
        };

        try {
          const response = await fetch('http://localhost:8080/userdata/register', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(registerData),
          });

          const result = await response.text();
          console.log(result);
        } catch (error) {
          console.error('Error during registration:', error);
        }
      } else {
        this.passwordError = 'De wachtwoorden zijn niet hetzelfde';
        this.error = true;
      }
    },
  },
};
</script>
