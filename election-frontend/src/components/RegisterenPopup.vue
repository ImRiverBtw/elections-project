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
import { ref, computed , inject} from "vue";

export default {
  props: {
    visible: {
      type: Boolean,
      required: true,
    },
  },
  setup(props, { emit }) {
    const sessionService = inject("sessionService")
    const registerUsername = ref("");
    const registerEmail = ref("");
    const registerPassword = ref("");
    const registerConfirmPassword = ref("");
    const emailError = ref(null);
    const usernameError = ref(null);

    const isValidEmail = computed(() => (email) => {
      const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
      return emailRegex.test(email);
    });

    const isValidPassword = computed(() => (password) => {
      const passwordRegex = /^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]{8,}$/;
      return passwordRegex.test(password);
    });

    const close = () => {
      emit("close");
    };

    const submit = async () => {
      emailError.value = null;
      usernameError.value = null;

      if (!isValidEmail.value(registerEmail.value)) {
        alert("Ongeldig emailadres");
        return;
      }

      if (!isValidPassword.value(registerPassword.value)) {
        alert("Wachtwoord moet minimaal 8 karakters bevatten en één cijfer en één speciaal teken");
        return;
      }

      if (registerPassword.value !== registerConfirmPassword.value) {
        alert("De wachtwoorden komen niet overeen");
        return;
      }

      try {
        // Registreren
        const response = await sessionService.asyncRegister(
            registerUsername.value,
            registerEmail.value,
            registerPassword.value
        );

        if (response && response.message === "User registered successfully") {
          const loginResponse = await sessionService.asyncLogIn(
              registerEmail.value,
              registerPassword.value
          );
          console.log("Login succesvol na registratie:", loginResponse);
          emit("close");
        }
      } catch (error) {
        console.error("Fout tijdens registratie of login:", error);

        const errorMessage = error?.message || "Er is een onverwachte fout opgetreden.";

        if (errorMessage.includes("gebruikersnaam")) {
          usernameError.value = "Deze gebruikersnaam is al in gebruik.";
        } else if (errorMessage.includes("e-mail")) {
          emailError.value = "Dit e-mailadres is al in gebruik.";
        } else {
          alert(errorMessage);
        }
      }
    };

    return {
      registerUsername,
      registerEmail,
      registerPassword,
      registerConfirmPassword,
      emailError,
      usernameError,
      isValidEmail,
      isValidPassword,
      close,
      submit,
    };
  },
};
</script>


<style scoped>
.text-error {
  font-size: 12px;
  color: red;
}
</style>
