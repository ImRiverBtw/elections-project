<script setup>
import { ref, defineProps, defineEmits } from 'vue';

// Props
defineProps({
  visible: {
    type: Boolean,
    required: true,
  },
});

// Emits
const emit = defineEmits(['close']); // Define the 'close' event

// Reactive data for email input
const email = ref('');

// Function to send the reset password request
const submit = async () => {
  try {
    // Call the backend's forgot-password endpoint
    const response = await fetch('http://localhost:8080/userdata/forgot-password', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email: email.value }), // Send only the email
    });

    if (response.ok) {
      alert('Password reset email sent! Please check your inbox.');
      emit('close'); // Close the popup
    } else {
      const error = await response.text();
      alert('Failed to send reset email: ' + error);
    }
  } catch (error) {
    console.error('Error:', error);
    alert('An error occurred while sending the reset email. Please try again.');
  }
};
</script>

<template>
  <div v-if="visible" class="popup">
    <div class="popup-content">
      <div class="popup-header">
        <h2>Forgot Password</h2>
        <button class="Button Close" @click="emit('close')">Close</button>
      </div>
      <div class="line"></div>
      <div class="inputBox">
        <label class="inputText email">Enter your email to reset your password</label>
        <input
            class="inputField email"
            v-model="email"
            type="email"
            placeholder="Email address"
        />
        <button class="Button submitLogin" @click="submit">Send Email</button>
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
