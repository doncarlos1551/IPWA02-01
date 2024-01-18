<template>
  <q-dialog v-model="zeigeDialog">
    <q-card>
      <q-card-section>
        <div class="text-h6">Neue Emissionsdaten</div>
      </q-card-section>

      <q-card-section>
        <q-input v-model="emissionsDaten.land" label="Land" />
        <q-input v-model="emissionsDaten.unternehmen" label="Unternehmen" />
        <q-input v-model="emissionsDaten.jahr" label="Jahr" type="number" />
        <q-input
          v-model="emissionsDaten.co2"
          label="CO2 Emissionen"
          type="number"
        />
        <q-toggle
          v-if="decodedJwt && decodedJwt.groups?.includes('Admin')"
          v-model="emissionsDaten.validiert"
          label="Validiert"
        />
      </q-card-section>

      <q-card-actions align="right">
        <q-btn flat label="Abbrechen" color="negative" @click="verstecken()" />
        <q-btn flat label="Senden" color="positive" @click="senden" />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script lang="ts">
import { defineComponent, ref, toRefs, watch } from 'vue';
import {
  UnternehmenEmission,
  isUnternehmenEmission,
} from './UnternehmenCo2Tabelle.vue';
import { useApiService } from '@/composables/useApiService';

export default defineComponent({
  name: 'EmissionErstellenDialog',
  props: {
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: {
    'update:modelValue': null,
    verstecken: null,
    create: (payload: UnternehmenEmission): boolean => {
      return isUnternehmenEmission(payload);
    },
  },
  setup(props, { emit }) {
    const { modelValue } = toRefs(props);
    const { decodedJwt } = useApiService();
    const zeigeDialog = ref(modelValue.value);
    const emissionsDaten = ref<UnternehmenEmission>({
      id: -1,
      land: '',
      unternehmen: '',
      jahr: 0,
      co2: 0,
      validiert: false,
    });

    function verstecken() {
      zeigeDialog.value = false;
      emit('verstecken');
    }

    const senden = async () => {
      // Unschön aber schneller fix! Quasar input schreibt string, auch wenn typ number!
      emissionsDaten.value.co2 = parseInt('' + emissionsDaten.value.co2);
      emissionsDaten.value.jahr = parseInt('' + emissionsDaten.value.jahr);
      emit('create', emissionsDaten.value);
    };

    watch(modelValue, (newValue) => {
      zeigeDialog.value = newValue;
    });

    watch(zeigeDialog, (newValue) => {
      emit('update:modelValue', newValue);
    });

    return { decodedJwt, zeigeDialog, emissionsDaten, verstecken, senden };
  },
});
</script>
