<template>
  <div class="emissions-date-tabelle">
    <div class="emissions-date-tabelle__header">
      <q-input
        v-model="filterUnternehmen"
        placeholder="Nach Unternehmen filtern"
      />
      <q-input v-model="filterLand" placeholder="Nach Land filtern" />
    </div>

    <table class="emissions-date-tabelle__tabelle">
      <thead>
        <tr>
          <th @click="sortiereTabelle('id')">
            Index<q-icon
              v-if="sortierSchluessel === 'id'"
              :name="sortierIcon"
            />
          </th>
          <th @click="sortiereTabelle('unternehmen')">
            Unternehmen<q-icon
              v-if="sortierSchluessel === 'unternehmen'"
              :name="sortierIcon"
            />
          </th>
          <th @click="sortiereTabelle('land')">
            Land<q-icon
              v-if="sortierSchluessel === 'land'"
              :name="sortierIcon"
            />
          </th>
          <th @click="sortiereTabelle('co2')">
            CO2-Wert<q-icon
              v-if="sortierSchluessel === 'co2'"
              :name="sortierIcon"
            />
          </th>
          <th v-if="adminMode">Validiert</th>
          <th v-if="editMode">Aktionen</th>
          <th v-if="validateMode && !editMode">Validierung</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(eintrag, index) in gefilterteDaten" :key="index">
          <td>{{ eintrag.id }}</td>

          <template v-if="editId && editId === eintrag.id && editEmission">
            <td>
              <q-input v-model="editEmission.unternehmen" filled />
            </td>
            <td>
              <q-input v-model="editEmission.land" filled />
            </td>
            <td>
              <q-input v-model="editEmission.co2" filled />
            </td>
            <td v-if="adminMode">
              <q-toggle v-model="editEmission.validiert" />
            </td>
          </template>
          <template v-else>
            <td>{{ eintrag.unternehmen }}</td>
            <td>{{ eintrag.land }}</td>
            <td>{{ eintrag.co2 }}</td>
            <td v-if="adminMode">
              <q-icon
                :name="eintrag.validiert ? 'check' : 'close'"
                style="cursor: help"
                ><q-tooltip>
                  Eintrag ist
                  {{ eintrag.validiert ? '' : 'nicht ' }}validiert</q-tooltip
                ></q-icon
              >
            </td>
          </template>
          <td v-if="editMode">
            <template v-if="editId && editId === eintrag.id">
              <q-icon name="save" @click="emitUpdate()"
                ><q-tooltip> Bearbeiteten Eintrag abspeichern </q-tooltip>
              </q-icon>
              <q-icon name="close" @click="cancelEdit()"
                ><q-tooltip> Bearbeiten abbrechen </q-tooltip>
              </q-icon>
            </template>
            <template v-else>
              <q-icon name="edit" @click="startEdit(eintrag.id, eintrag)"
                ><q-tooltip> Eintrag bearbeiten </q-tooltip>
              </q-icon>
              <q-icon name="delete" @click="emitDelete(eintrag.id)"
                ><q-tooltip> Eintrag löschen </q-tooltip>
              </q-icon>
            </template>
          </td>
          <td v-if="validateMode && !editMode">
            <template v-if="!eintrag.validiert">
              <q-icon
                tooltip="Validieren"
                name="check"
                @click="emitValidate(eintrag.id)"
                ><q-tooltip> Eintrag validieren </q-tooltip>
              </q-icon>
              <q-icon name="delete" @click="emitDelete(eintrag.id)"
                ><q-tooltip> Eintrag löschen </q-tooltip>
              </q-icon>
            </template>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from 'vue';
import _ from 'lodash';

export interface UnternehmenEmission {
  id: number;
  unternehmen: string;
  land: string;
  co2: number;
  validiert?: boolean;
}

export default defineComponent({
  name: 'UnternehmenCo2Tabelle',
  props: {
    emissionsDaten: {
      type: Array as () => UnternehmenEmission[],
      required: true,
    },
    adminMode: {
      type: Boolean,
      default: false,
    },
    editMode: {
      type: Boolean,
      default: false,
    },
    validateMode: {
      type: Boolean,
      default: false,
    },
  },
  setup(props, { emit }) {
    const editId = ref<number | null>(null);
    const editEmission = ref<UnternehmenEmission | null>(null);
    const filterUnternehmen = ref<string>('');
    const filterLand = ref<string>('');
    const sortierRichtung = ref<'auf' | 'ab'>('auf');
    const sortierSchluessel = ref<keyof UnternehmenEmission | ''>('');

    const sortierIcon = computed(() => {
      if (sortierRichtung.value === 'ab') {
        return 'arrow_drop_up';
      } else {
        return 'arrow_drop_down';
      }
    });

    const gefilterteDaten = computed(() => {
      const temporaereFilterung = props.emissionsDaten
        .filter(
          (eintrag) =>
            eintrag.unternehmen
              .toLowerCase()
              .includes(filterUnternehmen.value.toLowerCase()) &&
            eintrag.land
              .toLowerCase()
              .includes(filterLand.value.toLowerCase()) &&
            (eintrag.validiert || props.adminMode)
        )
        .sort((a, b) => {
          let vergleich = 0;
          if (sortierSchluessel.value === '') {
            vergleich = -1;
          } else {
            const aWert = a[sortierSchluessel.value];
            const bWert = b[sortierSchluessel.value];
            if (typeof aWert === 'number' && typeof bWert === 'number') {
              vergleich = aWert - bWert;
            } else {
              vergleich = String(aWert).localeCompare(String(bWert));
            }
          }
          return sortierRichtung.value === 'auf' ? vergleich : -vergleich;
        });
      return temporaereFilterung;
    });

    function sortiereTabelle(key: keyof UnternehmenEmission) {
      if (sortierSchluessel.value === key) {
        sortierRichtung.value = sortierRichtung.value === 'auf' ? 'ab' : 'auf';
      } else {
        sortierSchluessel.value = key;
        sortierRichtung.value = 'auf';
      }
    }

    function startEdit(id: number, eintrag: UnternehmenEmission) {
      console.log('start', id);
      editEmission.value = _.cloneDeep(eintrag);
      editId.value = id;
    }

    function cancelEdit() {
      console.log('cancel');
      editEmission.value = null;
      editId.value = null;
    }

    function emitDelete(id: number) {
      console.log('delete', id);
      emit('delete', id);
    }

    function emitUpdate() {
      console.log('update', editEmission.value);
      emit('update', editEmission.value);
      editId.value = null;
      editEmission.value = null;
    }

    function emitValidate(id: number) {
      console.log('validate', id);
      emit('validate', id);
    }

    return {
      editId,
      editEmission,
      filterUnternehmen,
      filterLand,
      sortierRichtung,
      sortierSchluessel,
      sortierIcon,
      gefilterteDaten,
      sortiereTabelle,
      startEdit,
      cancelEdit,
      emitDelete,
      emitUpdate,
      emitValidate,
    };
  },
});
</script>

<style scoped lang="scss">
.emissions-date-tabelle {
  .emissions-date-tabelle__header {
    display: flex;
    gap: 12px;
  }
  table.emissions-date-tabelle__tabelle {
    width: 100%;
    border-collapse: collapse;
    margin-top: 12px;

    th {
      background-color: $secondary;
      color: white;
      text-align: left;
      font-weight: bold;
      padding: 6px;
      cursor: pointer;
    }

    td {
      padding: 6px;
      text-align: left;
      border-bottom: 1px solid lightgrey;
    }

    tr:nth-child(even) {
      background-color: $primary;
    }

    tr:hover {
      background-color: $accent;
    }

    :deep(.q-icon) {
      cursor: pointer;
      font-size: 18px;
    }
  }
}
</style>
