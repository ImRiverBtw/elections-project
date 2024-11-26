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

        <a href=""><div class="forgotPassword">Forgot password?</div> </a>

        <button class="Button submitLogin" @click="submit">Login</button>
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
      loginEmail: '',
      loginPassword: '',
    };
  },
  methods: {
    //Close te popup
    close() {
      this.$emit('close');
    },
    async submit() {
      //verzamelen van gegevens van de inputs
      const loginData = {
        email: this.loginEmail,
        password: this.loginPassword,
      };

      try {
        //verzamelde gegevens naar de backend sturen.
        const response = await fetch('http://localhost:8080/userdata/login', {
          method: 'POST',
          headers: {'Content-Type': 'application/json'},
          body: JSON.stringify(loginData),
        });

        const result = await response.text();
        console.log(result);
      } catch (error) {
        console.error('Error during login:', error);
    }
    },
  },
};
</script>
