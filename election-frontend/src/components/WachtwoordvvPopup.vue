<script setup>
import { ref, defineProps, emit } from 'vue';

// Props
defineProps({
  visible: {
    type: Boolean,
    required: true,
  },
});

// Emit close event
const close = () => {
  emit('close');
};

// Reactive data for login form
const loginEmail = ref('');
const loginPassword = ref('');

// Submit function to handle form submission
const submit = async () => {
  const loginData = {
    email: loginEmail.value,
    password: loginPassword.value,
  };

  try {
    const response = await fetch('http://localhost:8080/userdata/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(loginData),
    });

    const result = await response.text();
    console.log(result);
  } catch (error) {
    console.error(error);
  }
};
</script>

<template>
  <div v-if="visible" class="popup">
    <div class="popup-content">
      <div class="popup-header">
        <h2>Wachtwoord vergeten</h2>
        <button class="Button Close" @click="close">Close</button>
      </div>
      <div class="line"></div>
      <div class="inputBox">
        <label class="inputText email">Email-addres</label>
        <input
            class="inputField email"
            v-model="loginEmail"
            placeholder="Email-addres"
        />

        <label class="inputText password">Wachtwoord</label>
        <input
            class="inputField password"
            type="password"
            v-model="loginPassword"
            placeholder="Wachtwoord"
        />

        <button class="Button submitLogin" @click="submit">Login</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
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
  z-index: 1000;
}

.popup-content {
  width: 30rem;
  background-color: white;
  padding: 20px;
  border-radius: 16px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  border: 2px solid #231f20;
}

.popup-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.line {
  background-color: #231f20;
  height: 1px;
  width: calc(100% + 40px);
  margin: 0 -20px;
}

.inputBox {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
}

.inputText {
  margin-bottom: 10px;
  font-size: 14px;
}

.inputField {
  width: 100%;
  padding: 10px;
  margin-bottom: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
}

.Button {
  background-color: #004494;
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.Button.Close {
  background: transparent;
  border: none;
  font-size: 16px;
  color: #333333;
}

.Button:hover {
  background-color: #ccd5ef;
  color: #231f20;
}
</style>
