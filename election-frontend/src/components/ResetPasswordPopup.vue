<script setup>
import { ref } from 'vue';

// Reactive data for password inputs
const newPassword = ref('');
const confirmPassword = ref('');
const token = new URLSearchParams(window.location.search).get('token'); // Extract token from URL

// Check if token exists
if (!token) {
  alert('Invalid or missing token.');
  window.location.href = '/'; // Redirect to home or an appropriate page
}

// Function to reset the password
const submit = async () => {
  if (newPassword.value !== confirmPassword.value) {
    alert('Passwords do not match.');
    return;
  }

  try {
    const response = await fetch('http://localhost:8080/userdata/reset-password', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json', // Include Accept header
      },
      body: JSON.stringify({ token, newPassword: newPassword.value }),
    });

    if (!response.ok) {
      const error = await response.text();
      console.error('Failed:', error);
      alert(`Failed to reset password: ${error}`);
    } else {
      alert('Password reset successfully!');
      window.location.href = '/'; // Redirect to the home or login page
    }
  } catch (error) {
    console.error('Unexpected error:', error);
    alert('An unexpected error occurred. Please try again.');
  }

};
</script>

<template>
  <div class="popup">
    <div class="popup-content">
      <div class="popup-header">
        <h2>Reset Password</h2>
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
/* Popup styles */
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

.Button:hover {
  background-color: #ccd5ef;
  color: #231f20;
}
</style>
