<template>
  <div class="startseite">
    <h1>{{ tageszeitBegruessung }}</h1>
    <p>Willkommen auf unserer Webseite! Schön, dass Sie sich für CO2 Interessieren.</p>

    <div class="startseite__drei-folien-wechsler" @click="stopUndResetInterval()">
      <q-btn
        round
        class="drei-folien-wechsler__button"
        icon="chevron_left"
        @click="vorherigesPanel()"
      />

      <div class="drei-folien-wechsler__panel" v-if="aktivesPanel === 1">
        <h2>CO2 und Zukunft</h2>
        <p>
          CO2 ist schlecht. Lesen Sie auf dieser Webseite nach wieso CO2 schlecht ist...
        </p>
      </div>
      <div class="drei-folien-wechsler__panel" v-if="aktivesPanel === 2">
        <h2>Nachhaltigkeit</h2>
        <p>
          Nachhaltigkeit ist gut. Lesen Sie auf dieser Webseite nach wieso Nachhaltigkeit
          gut ist... <router-link to="/nachhaltigkeit">Klicke Hier</router-link>
        </p>
      </div>
      <div class="drei-folien-wechsler__panel" v-if="aktivesPanel === 3">
        <h2>Umweltbewusstsein</h2>
        <p>Umweltbewusstsein ist auch gut. Bitte sein Sie umweltbewusst.</p>
      </div>

      <q-btn
        round
        class="drei-folien-wechsler__button"
        icon="chevron_right"
        @click="naechstesPanel()"
      />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed, onMounted, onUnmounted } from "vue";

export default defineComponent({
  name: "StartSeite",
  setup() {
    const tageszeitBegruessung = computed(() => {
      const stunde = new Date().getHours();
      if (stunde < 12) {
        return "Guten Morgen!";
      } else if (stunde < 18) {
        return "Guten Tag!";
      } else {
        return "Guten Abend!";
      }
    });

    const aktivesPanel = ref(1);
    let intervalId: number | undefined;

    function naechstesPanel() {
      aktivesPanel.value = (aktivesPanel.value % 3) + 1;
    }

    function startInterval() {
      intervalId = setInterval(naechstesPanel, 3000);
    }

    function stopUndResetInterval() {
      clearInterval(intervalId);
      startInterval();
    }

    function vorherigesPanel() {
      stopUndResetInterval();
      if (aktivesPanel.value === 1) {
        aktivesPanel.value = 3;
      } else {
        aktivesPanel.value -= 1;
      }
    }

    onMounted(startInterval);
    onUnmounted(() => clearInterval(intervalId));

    return {
      aktivesPanel,
      vorherigesPanel,
      naechstesPanel,
      stopUndResetInterval,
      tageszeitBegruessung,
    };
  },
});
</script>

<style scoped lang="scss">
.startseite {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 20px;
  width: 100%;

  h1,
  h2 {
    word-break: break-all;
    overflow-wrap: break-word;
  }

  .startseite__drei-folien-wechsler {
    flex: 1;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    padding: 0px 24px;
    border-radius: 6px;
    min-height: 360px;
    max-height: 600px;
    overflow: hidden;
    background-color: white;

    .drei-folien-wechsler__panel {
      overflow: auto;
    }
  }
}
</style>
