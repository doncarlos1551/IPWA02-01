<template>
  <router-view />
</template>

<script lang="ts">
import { defineComponent, onMounted } from "vue";

export default defineComponent({
  name: "App",
  setup() {
    onMounted(() => {
      const defaultBaseUrl = "http://localhost:8080/herotozero-backend/api";
      if (!localStorage.getItem("baseUrl")) {
        localStorage.setItem("baseUrl", defaultBaseUrl);
      }
    });
  },
});
</script>
