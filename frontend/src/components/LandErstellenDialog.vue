<template>
  <q-dialog v-model="zeigeDialog">
    <q-card>
      <q-card-section>
        <div class="text-h6">Neues Land</div>
      </q-card-section>

      <q-card-section>
        <q-input v-model="neuesLandName" label="Land" />
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

export default defineComponent({
  name: 'LandErstellenDialog',
  props: {
    modelValue: {
      type: Boolean,
      default: false,
    },
  },
  emits: {
    'update:modelValue': null,
    verstecken: null,
    create: (payload: string): boolean => {
      return typeof payload === 'string';
    },
  },
  setup(props, { emit }) {
    const { modelValue } = toRefs(props);
    const zeigeDialog = ref(modelValue.value);
    const neuesLandName = ref<string>('');

    function verstecken() {
      zeigeDialog.value = false;
      emit('verstecken');
    }

    const senden = async () => {
      emit('create', neuesLandName.value);
    };

    watch(modelValue, (newValue) => {
      zeigeDialog.value = newValue;
    });

    watch(zeigeDialog, (newValue) => {
      emit('update:modelValue', newValue);
    });

    return { zeigeDialog, neuesLandName, verstecken, senden };
  },
});
</script>
