<template>
  <router-view />
  <q-dialog v-model="zeigeBaseUrlHinweis">
    <q-card style="width: 450px; max-width: 80vw">
      <q-card-section class="row items-center q-pb-none">
        <div class="text-h6">Achtung! BaseUrl richtig Setzen</div>
        <q-space></q-space>
        <q-btn icon="close" flat round dense v-close-popup></q-btn>
      </q-card-section>

      <q-card-section>
        Bitte setzen Sie die BaseURL richtig. Die BaseURL wird standardmäßig auf
        'http://localhost:8080/herotozero-backend/api' gesetzt. Stellen Sie
        sicher, dass der entsprechende Backend-Server auf port 8080 läuft.
        Passen sie über das Zahnrad-Symbol <q-icon name="settings" /> oben
        rechts die BaseURL an falls sie abweichen sollte.
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';

export default defineComponent({
  name: 'App',
  setup() {
    const zeigeBaseUrlHinweis = ref<boolean>(false);
    onMounted(() => {
      const defaultBaseUrl = 'http://localhost:8080/herotozero-backend/api';
      if (!localStorage.getItem('baseUrl')) {
        zeigeBaseUrlHinweis.value = true;
        localStorage.setItem('baseUrl', defaultBaseUrl);
      }
    });
    return { zeigeBaseUrlHinweis };
  },
});
</script>
