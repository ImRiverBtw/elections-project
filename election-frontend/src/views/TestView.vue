<template>
  <div class="about">
    <h1>This is the test page</h1>
    <button class="Button Login" @click="loginButtonClick">Login</button>
    <button class="Button Register" @click="RegisterButtonClick">Register</button>
  </div>

  <div v-if="showLoginPopup" class="popup">
    <div class="popup-content">
      <div class="popup-header">
        <h2>Login</h2>
        <button class="Button Close" @click="closePopup">Close</button>
      </div>
      <div class="line"></div>

      <div class="inputBox">

        <div class="inputText email">Email-addres</div>
        <input class="inputField email" v-model="loginEmail" placeholder="Email-addres"/>

        <div class="inputText password">Wachtwoord</div>
        <input class="inputField password" v-model="loginPassword" placeholder="Wachtwoord"/>

        <button class="Button submitLogin" @click="submitLogin">Login</button>
      </div>
    </div>
  </div>

  <div v-if="showRegisterPopup" class="popup">
    <div class="popup-content">
      <div class="popup-header">
        <h2>Register</h2>
        <button class="Button Close" @click="closePopup">Close</button>
      </div>
      <div class="line"></div>

      <div class="inputBox">
        <div v-if="Error" class="password-error">
          <p>{{passwordError}}</p>
          <p>{{usernameError}}</p>
        </div>

        <div class="inputText username">Gebruikersname</div>
        <input class="inputField usernaem" v-model="registerUsername" placeholder="Gebruikersname" />

        <div class="inputText email">Email-addres</div>
        <input class="inputField email" v-model="registerEmail" placeholder="Email-addres"/>

        <div class="inputText password">Wachtwoord</div>
        <input class="inputField password" v-model="registerPassword" placeholder="Wachtwoord"/>

        <div class="inputText confirm-password">Confirm Wachtwoord</div>
        <input class="inputField confirm-password" v-model="registerConfirmPassword" placeholder="Confirm Wachtwoord"/>

        <button class="Button submitLogin" @click="sumbitRegister">Register</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showLoginPopup: false,
      showRegisterPopup: false,
      Error: false,

      loginEmail: '',
      loginPassword: '',

      registerUsername: '',
      registerEmail: '',
      registerPassword: '',
      registerConfirmPassword: '',

      passwordError: "",
      usernameError: ""
    };
  },
  methods: {
    loginButtonClick() {
      this.showLoginPopup = true;
      this.showRegisterPopup = false;
    },
    RegisterButtonClick() {
      this.showLoginPopup = false;
      this.showRegisterPopup = true;
    },
    closePopup() {
      this.showLoginPopup = false;
      this.showRegisterPopup = false;
    },

    submitLogin() {
      //TODO error voor niet correct email en password
      console.log('Login email: ', this.loginEmail);
      console.log('Login password: ', this.loginPassword);
    },

    sumbitRegister() {
      if(this.registerPassword === this.registerConfirmPassword) {
        this.usernameError = "Deze gebruikers naam is al in gebruik";
        this.passwordError = "";
      }
      //TODO error maken voor dubbel username.
      else {
        this.passwordError = "De wachtwoorden zijn niet het zelfde"
        this.Error = true;
      }
    }
  }
}
</script>

<style>
.about {
  text-align: center;
  margin-top: 20px;
}

.popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.popup-content {
  width: 30rem;
  background-color: white;
  padding: 20px;
  border-radius: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  border: 2px solid #231F20;

  h2 {
    text-align: left;
    padding-bottom: 10px;
  }

  .line {
    background-color: #231F20;
    height: 1px;
    width: calc(100% + 40px);
    margin: 0 -20px;
  }

  .inputBox {
    padding-left: 1rem;
    padding-right: 1rem;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .inputText {
    align-self: flex-start;
    padding-top: 0.8rem;

  }

  .inputField {
    width: 100%;
    height: 2.4rem;
    border-radius: 10px;
    background-color: #E3E3E3;
    padding-left: 10px;
    box-sizing: border-box;
  }

  ::placeholder {
    color: #333333;
  }

  .password-error {
    width: 100%;
    margin-top: 0.8rem;
    border: 2px solid red;
    color: red;

    p {
      padding-bottom: 10px;
    }
  }

  .submitLogin {
    align-items: center;
    margin-top: 2rem;
  }
}

.popup-header {
  display: flex;
  justify-content: space-between;
  align-items: center;

  h2 {
    margin: 0;
  }
}

.Button {
  background-color: #004494;
  padding: 0.5rem 1rem;
  border-radius: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  border: 2px solid #231F20;
  color: white;
}

.Button:hover {
  background-color: #CCD5EF;
  color: #231F20;
}

.Button.Close {
  background-color: transparent;
  border: none;
  font-size: 16px;
  color: #333333;
  cursor: pointer;
  box-shadow: none;
}

.Button.Close:hover {
  color: #004494;
}
</style>