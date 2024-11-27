<template>

  <div>

    <slot></slot>

  </div>

</template>

<script setup>

</script>

<style scoped>

div {
  display: inline-block;
  padding: 10px;
  border-radius: 10px;
  background: #d4dfef;
  margin: 2px;
  text-align: center;
  font-size: 16px;
  background: linear-gradient(to right, #d4dfef 75%, #d4dfef 75%);
}

div:hover {
  cursor: pointer;
  background: #f5c90e;
}
</style>