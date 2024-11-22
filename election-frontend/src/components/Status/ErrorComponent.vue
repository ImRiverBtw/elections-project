

<template>
  <h3>Er is iets misgegaan!</h3>
  <p>{{err}}</p>
  <p>probeer het later nog eens.</p>
</template>

<script>
export default {
  name: "ErrorComponent",
  props: {
    err: {
      type: [String, Object],
      required: false,
      default: null,
    }
  }
}
</script>
<style scoped>

</style>