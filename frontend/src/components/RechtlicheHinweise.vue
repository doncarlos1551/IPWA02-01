<template>
  <div
    class="rechtliche-hinweise"
    :class="mobileAnsicht ? 'rechtliche-hinweise--mobil' : ''"
  >
    <p class="rechtliche-hinweise__ueberschrift">
      ©{{ aktuellesJahr }} {{ firmenName }}. Alle Rechte vorbehalten.
      <!-- Der Firmenname ist im aktuellen Stand ausgedacht -->
    </p>
    <div
      class="rechtliche-hinweise__wichtige-links"
      :class="mobileAnsicht ? 'rechtliche-hinweise__wichtige-links--mobil' : ''"
    >
      <a href="/datenschutz">Datenschutzrichtlinie</a>
      <span v-if="!mobileAnsicht">|</span>
      <a href="/nutzungsbedingungen">Nutzungsbedingungen</a>
      <span v-if="!mobileAnsicht">|</span>
      <a href="/impressum">Impressum</a>
    </div>
    <p>
      Kontakt: <a :href="'mailto:' + firmenEmail">{{ firmenEmail }}</a>
    </p>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import { aktuellesJahr, firmenInformationen } from "src/config";

export default defineComponent({
  name: "RechtlicheHinweise",
  props: {
    mobileAnsicht: {
      type: Boolean,
      required: true,
    },
  },
  setup() {
    return {
      aktuellesJahr,
      firmenName: firmenInformationen.firmenName,
      firmenEmail: firmenInformationen.firmenEmail,
    };
  },
});
</script>

<style scoped lang="scss">
.rechtliche-hinweise {
  display: flex;
  flex-direction: column;
  &--mobil {
    align-items: center;
  }
  .rechtliche-hinweise__wichtige-links {
    display: flex;
    gap: 12px;
    &--mobil {
      flex-direction: column;
      justify-content: flex-start;
      align-items: center;
      gap: 0px;
    }
  }
}
</style>
