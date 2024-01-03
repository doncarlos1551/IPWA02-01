<template>
  <div
    class="globale-navigation"
    :class="mobileAnsicht ? 'globale-navigation--mobil' : ''"
  >
    <q-btn v-if="mobileAnsicht" @click="menueUmschalten"><q-icon name="menu" /></q-btn>
    <div
      v-if="istMenueSichtbar"
      class="globale-navigation__eintraege"
      :class="mobileAnsicht ? 'globale-navigation__eintraege--mobil' : ''"
    >
      <span v-for="item in navigationsEintraege" :key="item.path">
        <router-link :to="item.path">{{ item.name }}</router-link>
      </span>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";

export default defineComponent({
  name: "GlobaleNavigation",
  props: {
    mobileAnsicht: {
      type: Boolean,
      required: true,
    },
  },
  setup() {
    const istMenueSichtbar = ref<boolean>(true);

    const navigationsEintraege = [
      { name: "Startseite", path: "/" },
      { name: "Über Emissionen", path: "/emission" },
      { name: "Über Nachhaltigkeit", path: "/nachhaltigkeit" },
    ];

    function menueUmschalten() {
      istMenueSichtbar.value = !istMenueSichtbar.value;
    }

    return {
      istMenueSichtbar,
      navigationsEintraege,
      menueUmschalten,
    };
  },
});
</script>

<style scoped lang="scss">
.globale-navigation {
  display: flex;
  flex-direction: column;
  &--mobil {
    width: 100%;
  }

  .globale-navigation__eintraege {
    display: flex;
    flex-direction: row;
    gap: 12px;
    padding: 12px 6px;
    align-items: center;

    &--mobil {
      flex-direction: column;
    }
  }
}
</style>
