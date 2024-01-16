<template>
  <div class="settings-komponente">
    <q-input
      v-model="neueBaseUrl"
      label="Base URL"
      @keyup.enter="changeBaseUrl(neueBaseUrl)"
    />
    <q-btn
      label="Speichern"
      color="primary"
      @click="changeBaseUrl(neueBaseUrl)"
    />
    <span
      >Teste die Verbindung zum Backend!
      <a @click="publicTest()">Klicke Hier</a></span
    >
  </div>
</template>

<script lang="ts">
import { ref } from 'vue';
import { useApiService } from '@/composables/useApiService';

export default {
  name: 'SettingsKomponente',

  setup() {
    const { changeBaseUrl, publicTest } = useApiService();
    const neueBaseUrl = ref<string>(localStorage.getItem('baseUrl') || '');

    return {
      changeBaseUrl,
      publicTest,
      neueBaseUrl,
    };
  },
};
</script>

<style scoped lang="scss">
.settings-komponente {
  display: flex;
  flex-direction: column;
}
</style>
