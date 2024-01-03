<template>
  <div class="emissions-date-tabelle">
    <div class="emissions-date-tabelle__header">
      <q-input v-model="filterUnternehmen" placeholder="Nach Unternehmen filtern" />
      <q-input v-model="filterLand" placeholder="Nach Land filtern" />
    </div>

    <table class="emissions-date-tabelle__tabelle">
      <thead>
        <tr>
          <th @click="sortiereTabelle('')">
            Index<q-icon v-if="sortierSchluessel === ''" :name="sortierIcon" />
          </th>
          <th @click="sortiereTabelle('unternehmen')">
            Unternehmen<q-icon
              v-if="sortierSchluessel === 'unternehmen'"
              :name="sortierIcon"
            />
          </th>
          <th @click="sortiereTabelle('land')">
            Land<q-icon v-if="sortierSchluessel === 'land'" :name="sortierIcon" />
          </th>
          <th @click="sortiereTabelle('co2')">
            CO2-Wert<q-icon v-if="sortierSchluessel === 'co2'" :name="sortierIcon" />
          </th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(eintrag, index) in gefilterteDaten" :key="index">
          <td>
            {{ sortierRichtung === "ab" ? gefilterteDaten.length - index : index + 1 }}
          </td>
          <td>{{ eintrag.unternehmen }}</td>
          <td>{{ eintrag.land }}</td>
          <td>{{ eintrag.co2 }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, computed } from "vue";

interface EmissionsTabellenEintrag {
  unternehmen: string;
  land: string;
  co2: number;
}

export default defineComponent({
  name: "EmissionsdatenTabelle",
  props: {
    mobileAnsicht: {
      type: Boolean,
      required: true,
    },
  },
  setup() {
    // Bei den hier dargestellten Daten sind die Namen der Unternehmen frei erfunden, genauso wie die co2-Ausstoßwerte.
    const daten = ref<EmissionsTabellenEintrag[]>([
      { unternehmen: "FliegendeFrösche GmbH", land: "Mexiko", co2: 93 },
      { unternehmen: "TurboTreter AG", land: "Indien", co2: 252 },
      { unternehmen: "QuirligeQuallen UG", land: "Frankreich", co2: 241 },
      { unternehmen: "FantasieFirma24.com", land: "Deutschland", co2: 111 },
      { unternehmen: "BlinkendeBirnen Ltd.", land: "Ägypten", co2: 120 },
      { unternehmen: "SuperSupermarkt OHG", land: "USA", co2: 150 },
      { unternehmen: "WackelndeWürmer e.V.", land: "Brasilien", co2: 67 },
      { unternehmen: "MaxMustermannEnterprises", land: "Andorra", co2: 202 },
      { unternehmen: "SpringendeSuppenkellen Corp", land: "Äthiopien", co2: 225 },
      { unternehmen: "TanzendeTassen KG", land: "Südkorea", co2: 132 },
      { unternehmen: "PfeifendePfannen Inc.", land: "Spanien", co2: 103 },
      { unternehmen: "ZappelndeZwiebeln eG", land: "Belgien", co2: 143 },
      { unternehmen: "HuepfendeHummeln GmbH", land: "Neuseeland", co2: 212 },
    ]);

    const filterUnternehmen = ref<string>("");
    const filterLand = ref<string>("");
    const sortierRichtung = ref<"auf" | "ab">("auf");
    const sortierSchluessel = ref<keyof EmissionsTabellenEintrag | "">("");

    const sortierIcon = computed(() => {
      if (sortierRichtung.value === "ab") {
        return "arrow_drop_up";
      } else {
        return "arrow_drop_down";
      }
    });

    const gefilterteDaten = computed(() => {
      const temporaereFilterung = daten.value
        .filter(
          (eintrag) =>
            eintrag.unternehmen
              .toLowerCase()
              .includes(filterUnternehmen.value.toLowerCase()) &&
            eintrag.land.toLowerCase().includes(filterLand.value.toLowerCase())
        )
        .sort((a, b) => {
          let vergleich = 0;
          if (sortierSchluessel.value === "") {
            vergleich = -1;
          } else {
            const aWert = a[sortierSchluessel.value];
            const bWert = b[sortierSchluessel.value];
            if (typeof aWert === "number" && typeof bWert === "number") {
              vergleich = aWert - bWert;
            } else {
              vergleich = aWert.localeCompare(bWert);
            }
          }
          return sortierRichtung.value === "auf" ? vergleich : -vergleich;
        });
      return temporaereFilterung;
    });

    function sortiereTabelle(key: keyof EmissionsTabellenEintrag) {
      if (sortierSchluessel.value === key) {
        sortierRichtung.value = sortierRichtung.value === "auf" ? "ab" : "auf";
      } else {
        sortierSchluessel.value = key;
        sortierRichtung.value = "auf";
      }
    }

    return {
      daten,
      sortierRichtung,
      sortierSchluessel,
      sortierIcon,
      gefilterteDaten,
      filterUnternehmen,
      filterLand,
      sortiereTabelle,
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
  }
}
</style>
