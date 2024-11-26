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

// Reactive data for password inputs
const newPassword = ref('');
const confirmPassword = ref('');

// Function to reset the password
const submit = async () => {
  const token = new URLSearchParams(window.location.search).get('token'); // Token from URL
  if (!token) {
    alert('Invalid token.');
    return;
  }

  if (newPassword.value !== confirmPassword.value) {
    alert('Passwords do not match.');
    return;
  }

  try {
    const response = await fetch('http://localhost:8080/userdata/reset-password', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ token, newPassword: newPassword.value }),
    });

    if (response.ok) {
      alert('Password reset successfully!');
      emit('close'); // Emit the 'close' event to parent
    } else {
      alert('Failed to reset password.');
    }
  } catch (error) {
    console.error(error);
    alert('An error occurred. Please try again.');
  }
};
</script>

<template>
  <div v-if="visible" class="popup">
    <div class="popup-content">
      <div class="popup-header">
        <h2>Reset Password</h2>
        <button class="Button Close" @click="emit('close')">Close</button>
      </div>
      <div class="line"></div>
      <div class="inputBox">
        <label class="inputText">New Password</label>
        <input
            class="inputField"
            v-model="newPassword"
            type="password"
            placeholder="Enter new password"
        />
        <label class="inputText">Confirm Password</label>
        <input
            class="inputField"
            v-model="confirmPassword"
            type="password"
            placeholder="Confirm new password"
        />
        <button class="Button submitLogin" @click="submit">Reset Password</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Reuse your current popup styles */
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
