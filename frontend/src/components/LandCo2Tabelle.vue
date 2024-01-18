<template>
  <div class="emissions-date-tabelle">
    <div class="emissions-date-tabelle__header">
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
          <th v-if="editMode">Aktionen</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="(eintrag, index) in gefilterteDaten"
          :key="'eintrag-land--' + index"
        >
          <td>
            <span>{{ eintrag.id }}</span>
          </td>
          <td>
            <q-input
              v-if="editId && editId === eintrag.id && editEmission"
              v-model="editEmission.land"
              filled
            />
            <span v-else>{{ eintrag.land }}</span>
          </td>
          <td>
            <span>{{ eintrag.co2 }}</span>
          </td>
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
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
import _ from 'lodash';
import { defineComponent, ref, computed } from 'vue';

export interface LandEmission {
  id: number;
  land: string;
  co2: number;
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export function isLandEmission(obj: any): obj is LandEmission {
  return (
    obj &&
    typeof obj.id === 'number' &&
    typeof obj.land === 'string' &&
    typeof obj.co2 === 'number'
  );
}

export default defineComponent({
  name: 'LandCo2Tabelle',
  props: {
    emissionsDaten: {
      type: Array as () => LandEmission[],
      required: true,
    },
    editMode: {
      type: Boolean,
      default: false,
    },
  },
  emits: {
    delete: (payload: number): boolean => {
      return typeof payload === 'boolean';
    },
    update: (payload: LandEmission): boolean => {
      return isLandEmission(payload);
    },
  },
  setup(props, { emit }) {
    const editId = ref<number | null>(null);
    const editEmission = ref<LandEmission | null>(null);
    const filterLand = ref<string>('');
    const sortierRichtung = ref<'auf' | 'ab'>('auf');
    const sortierSchluessel = ref<keyof LandEmission | ''>('');

    const sortierIcon = computed(() => {
      if (sortierRichtung.value === 'ab') {
        return 'arrow_drop_up';
      } else {
        return 'arrow_drop_down';
      }
    });

    const gefilterteDaten = computed(() => {
      const temporaereFilterung = props.emissionsDaten
        .filter((eintrag) =>
          eintrag.land.toLowerCase().includes(filterLand.value.toLowerCase())
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

    function sortiereTabelle(key: keyof LandEmission) {
      if (sortierSchluessel.value === key) {
        sortierRichtung.value = sortierRichtung.value === 'auf' ? 'ab' : 'auf';
      } else {
        sortierSchluessel.value = key;
        sortierRichtung.value = 'auf';
      }
    }

    function startEdit(id: number, eintrag: LandEmission) {
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
      if (editEmission.value) {
        emit('update', editEmission.value);
      }
      editId.value = null;
      editEmission.value = null;
    }

    return {
      editId,
      editEmission,
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
      max-height: 40px;
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
