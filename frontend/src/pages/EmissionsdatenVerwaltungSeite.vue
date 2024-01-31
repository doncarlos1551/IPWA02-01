<template>
  <div class="emissionen-verwaltung-seite">
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
        <q-btn
          label="Neuer Datensatz"
          color="primary"
          @click="zeigeEmissionErstellenDialog = true"
        />
        <q-toggle v-model="editEmissionTabelle" label="Editieren"></q-toggle>
        <UnternehmenCo2Tabelle
          :emissions-daten="unternehmenEmissionsDaten"
          :adminMode="decodedJwt && decodedJwt.groups?.includes('Admin')"
          :validateMode="true"
          :editMode="editEmissionTabelle"
          @delete="onDeleteUnternehmenEmission"
          @update="onUpdateUnternehmenEmission"
          @validate="onValidateUnternehmenEmission"
        />
      </q-tab-panel>
      <q-tab-panel name="land">
        <q-btn
          label="Neues Land"
          color="primary"
          @click="zeigeLandErstellenDialog = true"
        />
        <q-toggle v-model="editLandTabelle" label="Editieren"></q-toggle>
        <LandCo2Tabelle
          :emissions-daten="landEmissionsDaten"
          :editMode="editLandTabelle"
          @delete="onDeleteLandEmission"
          @update="onUpdateLandEmission"
        />
      </q-tab-panel>
    </q-tab-panels>
  </div>
  <EmissionErstellenDialog
    v-model="zeigeEmissionErstellenDialog"
    @create="onCreateUnternehmenEmission"
  />
  <LandErstellenDialog
    v-model="zeigeLandErstellenDialog"
    @create="onCreateLandEmission"
  />
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

import EmissionErstellenDialog from 'components/EmissionErstellenDialog.vue';
import LandErstellenDialog from 'components/LandErstellenDialog.vue';
import UnternehmenCo2Tabelle from 'components/UnternehmenCo2Tabelle.vue';
import LandCo2Tabelle from 'components/LandCo2Tabelle.vue';
import {
  convertEmissionsDatenResponseZuUnternehmenEmission,
  convertLandEmissionZuLandRequest,
  convertLandResponseZuLandEmission,
  convertUnternehmenEmissionZuEmissionsDatenRequest,
} from '@/utils/dataUtils';

export default defineComponent({
  name: 'EmissionsdatenVerwaltungSeite',
  components: {
    UnternehmenCo2Tabelle,
    LandCo2Tabelle,
    EmissionErstellenDialog,
    LandErstellenDialog,
  },
  setup() {
    const {
      decodedJwt,
      alleLaender,
      neuesLand,
      landLoeschen,
      landAktualisieren,
      alleEmissionsDaten,
      neueEmissionsDaten,
      emissionsDatenLoeschen,
      emissionsDatenAktualisieren,
      emissionsDatenValidieren,
    } = useApiService();

    const zeigeEmissionErstellenDialog = ref<boolean>(false);
    const zeigeLandErstellenDialog = ref<boolean>(false);
    const editEmissionTabelle = ref<boolean>(false);
    const editLandTabelle = ref<boolean>(false);
    const tabellenTab = ref<'unternehmen' | 'land'>('unternehmen');

    const unternehmenEmissionsDaten = ref<UnternehmenEmission[]>([]);
    const landEmissionsDaten = ref<LandEmission[]>([]);

    // === LAND FUNKTIONEN ===
    async function onCreateLandEmission(landName: string) {
      await neuesLand({ name: landName });
      await updateLandEmissionsDaten();
      zeigeLandErstellenDialog.value = false;
    }
    async function onDeleteLandEmission(id: number) {
      await landLoeschen(id);
      await updateLandEmissionsDaten();
    }
    async function onUpdateLandEmission(emission: LandEmission) {
      await landAktualisieren(
        emission.id,
        convertLandEmissionZuLandRequest(emission)
      );
      await updateLandEmissionsDaten();
    }

    const updateLandEmissionsDaten = async () => {
      const tempLandEmissionsDaten = (await alleLaender()) as LandResponse[];
      if (tempLandEmissionsDaten) {
        landEmissionsDaten.value = tempLandEmissionsDaten.map((land) =>
          convertLandResponseZuLandEmission(land)
        );
      }
    };

    // === UNTERNEHMEN FUNKTIONEN ===
    async function onCreateUnternehmenEmission(emission: UnternehmenEmission) {
      await neueEmissionsDaten(
        convertUnternehmenEmissionZuEmissionsDatenRequest(
          emission,
          landEmissionsDaten.value
        )
      );
      await updateUnternehmenEmissionsDaten();
      zeigeEmissionErstellenDialog.value = false;
    }

    async function onDeleteUnternehmenEmission(id: number) {
      await emissionsDatenLoeschen(id);
      await updateUnternehmenEmissionsDaten();
    }

    async function onUpdateUnternehmenEmission(emission: UnternehmenEmission) {
      await emissionsDatenAktualisieren(
        emission.id,
        convertUnternehmenEmissionZuEmissionsDatenRequest(
          emission,
          landEmissionsDaten.value
        )
      );
      await updateUnternehmenEmissionsDaten();
    }

    async function onValidateUnternehmenEmission(id: number) {
      await emissionsDatenValidieren(id);
      await updateUnternehmenEmissionsDaten();
    }

    const updateUnternehmenEmissionsDaten = async () => {
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
    };

    // === VUE-LIFE-CYCLE HOOKS ===

    onMounted(async () => {
      await updateLandEmissionsDaten();
      await updateUnternehmenEmissionsDaten();
    });

    return {
      decodedJwt,
      zeigeEmissionErstellenDialog,
      zeigeLandErstellenDialog,
      editEmissionTabelle,
      editLandTabelle,
      tabellenTab,
      unternehmenEmissionsDaten,
      landEmissionsDaten,
      onCreateLandEmission,
      onDeleteLandEmission,
      onUpdateLandEmission,
      onCreateUnternehmenEmission,
      onDeleteUnternehmenEmission,
      onUpdateUnternehmenEmission,
      onValidateUnternehmenEmission,
    };
  },
});
</script>

<style scoped lang="scss">
.emissionen-verwaltung-seite {
}
</style>
