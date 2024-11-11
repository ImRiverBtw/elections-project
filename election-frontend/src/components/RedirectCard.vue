<template>

  <div>

    <slot></slot>

  </div>

</template>

<script setup>

</script>

<style scoped>

hr {
  border: none;
  height: 3px;
  background-color: #004494;
  width: 30%;
  border-radius: 100px;
  margin-top: 1%;
}

img {
  padding: 10px;
  border-radius: 20px;
  width: 290px;
  height: 190px;
  object-fit: cover;
  margin-top: 3%;
  transition: background-color linear 0.3s;
}

div{
  flex-basis: 300px;
  width: 500px;
  margin-top: 20px;
  box-sizing: border-box;
  font-size: 1.5rem;
}



</style>