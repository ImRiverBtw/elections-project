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
        <input class="inputField email" v-model="loginEmail" placeholder="Email-addres"/>
        <small v-if="emailError" class="text-error">{{ emailError }}</small>

        <div class="inputText password">Wachtwoord</div>
        <input class="inputField password" type="password" v-model="loginPassword" placeholder="Wachtwoord"/>

        <a href="#" @click.prevent="$emit('forgot-password')">Wachtwoord vergeten?</a>

        <button class="Button submitLogin" @click="submit">Login</button>
      </div>
    </div>
  </div>
</template>

<script>
import {inject, nextTick, ref} from "vue";
import router from "@/router/index.js";

export default {
  props: {
    visible: {
      type: Boolean,
      required: true,
    },
  },
  setup(props, {emit}) {
    const sessionService = inject('sessionService');
    const loginEmail = ref("");
    const loginPassword = ref("");
    const emailError = ref(null);

    const submit = async () => {
      emailError.value = null;
      try {
        await sessionService.asyncLogIn(loginEmail.value, loginPassword.value);
        close()
      } catch (error) {
        if (error.message.includes('Invalid email or password')) {
          emailError.value = 'E-mailadres of wachtwoord is onjuist.';
        } else {
          emailError.value = 'Er is een probleem opgetreden. Probeer het later opnieuw.';
          console.log(error)
        }
      }
      await nextTick();
    };
    const close = () => {
      router.push("/profile")
      emit("close"); // This emits the "close" event
    };

    return {
      loginEmail,
      loginPassword,
      emailError,
      submit,
      close,
    }
  },
};
</script>

<style>
.text-error {
  font-size: 12px;
  color: red;
}
</style>
