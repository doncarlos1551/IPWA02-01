<template>
  <div class="emissionen-seite">
    <h3>Emissionen</h3>
    <p>
      Hier finden Sie informationen über Emissionen. Der unten aufgelisteten
      Tabelle entnehmen Sie die CO2-Ausstoßwerte ausgewählter Unternehmen in
      1000kg/Jahr.
    </p>
    <hr />
    <q-tabs
      v-model="tabellenTab"
      class="bg-primary text-dark shadow-2"
      align="justify"
    >
      <q-tab name="unternehmen" label="Unternehmen" />
      <q-tab name="land" label="Land" />
    </q-tabs>

    <q-tab-panels v-model="tabellenTab" style="background-color: transparent">
      <q-tab-panel name="unternehmen">
        <UnternehmenCo2Tabelle
          :emissions-daten="unternehmenEmissionsDaten"
          :adminMode="decodedJwt && decodedJwt.groups?.includes('Admin')"
          :validateMode="false"
          :editMode="false"
        />
      </q-tab-panel>
      <q-tab-panel name="land">
        <LandCo2Tabelle :emissions-daten="landEmissionsDaten" />
      </q-tab-panel>
    </q-tab-panels>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref } from 'vue';
import {
  EmissionsDatenResponse,
  LandResponse,
  useApiService,
} from '@/composables/useApiService';

import { UnternehmenEmission } from '@/components/UnternehmenCo2Tabelle.vue';
import { LandEmission } from '@/components/LandCo2Tabelle.vue';

import UnternehmenCo2Tabelle from 'components/UnternehmenCo2Tabelle.vue';
import LandCo2Tabelle from 'components/LandCo2Tabelle.vue';
import {
  convertEmissionsDatenResponseZuUnternehmenEmission,
  convertLandResponseZuLandEmission,
} from '@/utils/dataUtils';

export default defineComponent({
  name: 'EmissionsdatenSeite',
  components: {
    UnternehmenCo2Tabelle,
    LandCo2Tabelle,
  },
  setup() {
    const { decodedJwt, alleEmissionsDaten, alleLaender } = useApiService();

    const tabellenTab = ref<'unternehmen' | 'land'>('unternehmen');
    const unternehmenEmissionsDaten = ref<UnternehmenEmission[]>([
      {
        id: 1,
        unternehmen: 'FliegendeFrösche GmbH',
        land: 'Mexiko',
        jahr: 2023,
        co2: 93,
      },
      {
        id: 2,
        unternehmen: 'TurboTreter AG',
        land: 'Indien',
        jahr: 2023,
        co2: 252,
        validiert: true,
      },
      {
        id: 3,
        unternehmen: 'QuirligeQuallen UG',
        land: 'Frankreich',
        jahr: 2023,
        co2: 241,
      },
      {
        id: 4,
        unternehmen: 'FantasieFirma24.com',
        land: 'Deutschland',
        jahr: 2023,
        co2: 111,
      },
      {
        id: 5,
        unternehmen: 'BlinkendeBirnen Ltd.',
        land: 'Ägypten',
        jahr: 2023,
        co2: 120,
      },
      {
        id: 6,
        unternehmen: 'SuperSupermarkt OHG',
        land: 'USA',
        jahr: 2023,
        co2: 150,
      },
      {
        id: 7,
        unternehmen: 'WackelndeWürmer e.V.',
        land: 'Brasilien',
        jahr: 2023,
        co2: 67,
      },
      {
        id: 8,
        unternehmen: 'MaxMustermannEnterprises',
        land: 'Andorra',
        jahr: 2023,
        co2: 202,
      },
      {
        id: 20,
        unternehmen: 'SpringendeSuppenkellen Corp',
        land: 'Äthiopien',
        jahr: 2023,
        co2: 225,
      },
      {
        id: 10,
        unternehmen: 'TanzendeTassen KG',
        land: 'Südkorea',
        jahr: 2023,
        co2: 132,
      },
      {
        id: 11,
        unternehmen: 'PfeifendePfannen Inc.',
        land: 'Spanien',
        jahr: 2023,
        co2: 103,
      },
      {
        id: 12,
        unternehmen: 'ZappelndeZwiebeln eG',
        land: 'Belgien',
        jahr: 2023,
        co2: 143,
      },
      {
        id: 14,
        unternehmen: 'HuepfendeHummeln GmbH',
        land: 'Neuseeland',
        jahr: 2023,
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

    onMounted(async () => {
      const tempLandEmissionsDaten = (await alleLaender()) as LandResponse[];
      if (tempLandEmissionsDaten) {
        landEmissionsDaten.value = tempLandEmissionsDaten.map((land) =>
          convertLandResponseZuLandEmission(land)
        );
      }
      const tempUnternehmenEmissionsDaten =
        (await alleEmissionsDaten()) as EmissionsDatenResponse[];
      if (tempUnternehmenEmissionsDaten) {
        unternehmenEmissionsDaten.value = tempUnternehmenEmissionsDaten.map(
          (daten) =>
            convertEmissionsDatenResponseZuUnternehmenEmission(
              daten,
              landEmissionsDaten.value
            )
        );
      }
    });

    return {
      decodedJwt,
      tabellenTab,
      unternehmenEmissionsDaten,
      landEmissionsDaten,
    };
  },
});
</script>

<style scoped lang="scss">
.emissionen-seite {
}
</style>
