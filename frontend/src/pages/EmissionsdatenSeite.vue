<template>
  <div class="emissionen-seite">
    <h3>Emissionen</h3>
    <p>
      Hier finden Sie informationen über Emissionen. Der unten aufgelisteten Tabelle
      entnehmen Sie die CO2-Ausstoßwerte ausgewählter Unternehmen in 1000kg/Jahr.
    </p>
    <hr />
    <EmissionsdatenTabelle />
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";

import EmissionsdatenTabelle from "components/EmissionsdatenTabelle.vue";

export default defineComponent({
  name: "EmissionsdatenSeite",
  components: {
    EmissionsdatenTabelle,
  },
  setup() {
    return {};
  },
});
</script>

<style scoped lang="scss">
.emissionen-seite {
}
</style>
