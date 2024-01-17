<template>
  <div class="emissionen-seite">
    <h3>Emissionen</h3>
    <p>
      Hier finden Sie informationen über Emissionen. Der unten aufgelisteten
      Tabelle entnehmen Sie die CO2-Ausstoßwerte ausgewählter Unternehmen in
      1000kg/Jahr.
    </p>
    <hr />
    <UnternehmenCo2Tabelle :emissions-daten="unternehmenEmissionsDaten" />
    <LandCo2Tabelle :emissions-daten="landEmissionsDaten" />
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';

import { UnternehmenEmission } from '@/components/UnternehmenCo2Tabelle.vue';
import { LandEmission } from '@/components/LandCo2Tabelle.vue';

import UnternehmenCo2Tabelle from 'components/UnternehmenCo2Tabelle.vue';
import LandCo2Tabelle from 'components/LandCo2Tabelle.vue';

export default defineComponent({
  name: 'EmissionsdatenSeite',
  components: {
    UnternehmenCo2Tabelle,
    LandCo2Tabelle,
  },
  setup() {
    // Bei den hier dargestellten Daten sind die Namen der Unternehmen frei erfunden, genauso wie die co2-Ausstoßwerte.
    const unternehmenEmissionsDaten = ref<UnternehmenEmission[]>([
      { id: 1, unternehmen: 'FliegendeFrösche GmbH', land: 'Mexiko', co2: 93 },
      {
        id: 2,
        unternehmen: 'TurboTreter AG',
        land: 'Indien',
        co2: 252,
        validiert: true,
      },
      {
        id: 3,
        unternehmen: 'QuirligeQuallen UG',
        land: 'Frankreich',
        co2: 241,
      },
      {
        id: 4,
        unternehmen: 'FantasieFirma24.com',
        land: 'Deutschland',
        co2: 111,
      },
      { id: 5, unternehmen: 'BlinkendeBirnen Ltd.', land: 'Ägypten', co2: 120 },
      { id: 6, unternehmen: 'SuperSupermarkt OHG', land: 'USA', co2: 150 },
      {
        id: 7,
        unternehmen: 'WackelndeWürmer e.V.',
        land: 'Brasilien',
        co2: 67,
      },
      {
        id: 8,
        unternehmen: 'MaxMustermannEnterprises',
        land: 'Andorra',
        co2: 202,
      },
      {
        id: 20,
        unternehmen: 'SpringendeSuppenkellen Corp',
        land: 'Äthiopien',
        co2: 225,
      },
      { id: 10, unternehmen: 'TanzendeTassen KG', land: 'Südkorea', co2: 132 },
      {
        id: 11,
        unternehmen: 'PfeifendePfannen Inc.',
        land: 'Spanien',
        co2: 103,
      },
      {
        id: 12,
        unternehmen: 'ZappelndeZwiebeln eG',
        land: 'Belgien',
        co2: 143,
      },
      {
        id: 14,
        unternehmen: 'HuepfendeHummeln GmbH',
        land: 'Neuseeland',
        co2: 212,
      },
    ]);

    const landEmissionsDaten = ref<LandEmission[]>([
      { id: 1, land: 'Mexiko', co2: 93 },
      { id: 2, land: 'Indien', co2: 252 },
      {
        id: 3,
        land: 'Frankreich',
        co2: 241,
      },
      {
        id: 4,
        land: 'Deutschland',
        co2: 111,
      },
      { id: 5, land: 'Ägypten', co2: 120 },
      { id: 6, land: 'USA', co2: 150 },
      {
        id: 7,
        land: 'Brasilien',
        co2: 67,
      },
      {
        id: 8,
        land: 'Andorra',
        co2: 202,
      },
      {
        id: 20,
        land: 'Äthiopien',
        co2: 225,
      },
      { id: 10, land: 'Südkorea', co2: 132 },
      {
        id: 11,
        land: 'Spanien',
        co2: 103,
      },
      {
        id: 12,
        land: 'Belgien',
        co2: 143,
      },
      {
        id: 14,
        land: 'Neuseeland',
        co2: 212,
      },
    ]);

    return { unternehmenEmissionsDaten, landEmissionsDaten };
  },
});
</script>

<style scoped lang="scss">
.emissionen-seite {
}
</style>
