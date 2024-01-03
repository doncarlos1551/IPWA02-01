<template>
  <div class="error-nicht-gefunden">
    <h1>404</h1>
    <h4>Seite konnte nicht gefunden werden.</h4>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "ErrorNichtGefunden",
});
</script>

<style scoped lang="scss">
.error-nicht-gefunden {
  margin-left: 24px;
}
</style>
