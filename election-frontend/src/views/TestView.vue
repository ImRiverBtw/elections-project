<template>
  <div class="about">
    <h1>This is the test page</h1>
    <button class="Button Login" @click="showLoginPopup = true">Login</button>
    <button class="Button Register" @click="showRegisterPopup = true">Register</button>

    <LoginPopup
        :visible="showLoginPopup"
        @close="showLoginPopup = false"
        @forgot-password="showForgotPasswordPopup = true"
    />

    <ForgotPasswordPopup :visible="showForgotPasswordPopup" @close="showForgotPasswordPopup = false" />

    <RegisterPopup :visible="showRegisterPopup" @close="showRegisterPopup = false" />
  </div>
</template>

<script>
import LoginPopup from '../components/LoginPopup.vue';
import RegisterPopup from '../components/RegisterenPopup.vue';
import ForgotPasswordPopup from '../components/WachtwoordvvPopup.vue';


export default {
  components: {
    LoginPopup,
    RegisterPopup,
    ForgotPasswordPopup
  },
  data() {
    return {
      showLoginPopup: false,
      showRegisterPopup: false,
      showForgotPasswordPopup: false,
    };
  },
};
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

.Button.Submit {
  margin-top: 1rem;
}
</style>
