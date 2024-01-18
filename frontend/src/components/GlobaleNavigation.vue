<template>
  <div
    class="globale-navigation"
    :class="mobileAnsicht ? 'globale-navigation--mobil' : ''"
  >
    <q-btn v-if="mobileAnsicht" @click="menueUmschalten"
      ><q-icon name="menu"
    /></q-btn>
    <div
      v-if="zeigeMenue"
      class="globale-navigation__eintraege"
      :class="mobileAnsicht ? 'globale-navigation__eintraege--mobil' : ''"
    >
      <div
        class="einträge__main einträge__unter-bereich"
        :class="mobileAnsicht ? 'einträge__unter-bereich--mobil' : ''"
      >
        <span v-for="item in navigationsEintraegeMain" :key="item.path">
          <router-link
            v-if="
              !item.groups ||
              (decodedJwt &&
                item.groups.some((rolle) => decodedJwt.groups?.includes(rolle)))
            "
            :to="item.path"
            >{{ item.name }}</router-link
          >
        </span>
      </div>
      <div
        class="einträge__user einträge__unter-bereich"
        :class="mobileAnsicht ? 'einträge__unter-bereich--mobil' : ''"
      >
        <a v-if="decodedJwt === null" @click="zeigeLoginDialog = true">Login</a>
        <span v-else>
          Willkommen, {{ decodedJwt.sub }}! <a @click="logout()">logout</a>
        </span>
        <q-icon
          name="settings"
          class="cursor-pointer"
          @click="zeigeSettingsDialog = true"
        />
      </div>
    </div>
  </div>

  <q-dialog v-model="zeigeLoginDialog">
    <q-card style="width: 450px; max-width: 80vw">
      <q-card-section class="row items-center q-pb-none">
        <div class="text-h6">Login</div>
        <q-space></q-space>
        <q-btn icon="close" flat round dense v-close-popup></q-btn>
      </q-card-section>

      <q-card-section>
        <LoginKomponente />
      </q-card-section>
    </q-card>
  </q-dialog>
  <q-dialog v-model="zeigeSettingsDialog">
    <q-card style="width: 450px; max-width: 80vw">
      <q-card-section class="row items-center q-pb-none">
        <div class="text-h6">Login</div>
        <q-space></q-space>
        <q-btn icon="close" flat round dense v-close-popup></q-btn>
      </q-card-section>

      <q-card-section>
        <SettingsKomponente />
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useApiService } from '@/composables/useApiService';

import LoginKomponente from 'components/LoginKomponente.vue';
import SettingsKomponente from 'components/SettingsKomponente.vue';

export default defineComponent({
  name: 'GlobaleNavigation',
  components: {
    LoginKomponente,
    SettingsKomponente,
  },
  props: {
    mobileAnsicht: {
      type: Boolean,
      required: true,
    },
  },
  setup() {
    const { logout, decodedJwt } = useApiService();

    const zeigeMenue = ref<boolean>(true);
    const zeigeLoginDialog = ref<boolean>(false);
    const zeigeSettingsDialog = ref<boolean>(false);

    const navigationsEintraegeMain = [
      { name: 'Startseite', path: '/' },
      { name: 'Über Emissionen', path: '/emission' },
      {
        name: 'Emissionen-Verwaltung',
        path: '/emission-verwaltung',
        groups: ['User'],
      },
      { name: 'Über Nachhaltigkeit', path: '/nachhaltigkeit' },
    ];

    function menueUmschalten() {
      zeigeMenue.value = !zeigeMenue.value;
    }

    return {
      logout,
      decodedJwt,
      zeigeMenue,
      zeigeLoginDialog,
      zeigeSettingsDialog,
      navigationsEintraegeMain,
      menueUmschalten,
    };
  },
});
</script>

<style scoped lang="scss">
.globale-navigation {
  flex: 1;
  display: flex;
  flex-direction: column;
  &--mobil {
    width: 100%;
  }

  .globale-navigation__eintraege {
    display: flex;
    flex-direction: row;
    padding: 12px 6px;
    gap: 12px;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;

    .einträge__unter-bereich {
      display: flex;
      flex-direction: row;
      gap: 12px;
      align-items: center;

      &--mobil {
        flex-direction: column;
      }
    }

    .einträge__main {
      justify-content: flex-start;
    }

    .einträge__user {
      justify-content: flex-end;
    }

    &--mobil {
      background-color: lightgrey;
      flex-direction: column;
    }
  }
}
</style>
