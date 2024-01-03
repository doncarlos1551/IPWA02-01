<template>
  <div class="main-layout">
    <header
      class="main-layout__header"
      :class="istMobil ? 'main-layout__header--mobil' : ''"
    >
      <q-icon name="eco" size="lg" color="primary" />
      <span class="header__title firmen-identitaet">C02-FootPrint</span>
      <GlobaleNavigation :mobile-ansicht="istMobil" />
    </header>
    <div
      class="main-layout__content"
      :class="[
        $q.lang.rtl ? 'main-layout__content--rtl' : '',
        istMobil ? 'main-layout__content--mobil' : '',
      ]"
    >
      <nav class="content__menue">
        <SeitenMenue :mobile-ansicht="istMobil" />
      </nav>

      <main class="content__router-view">
        <router-view />
      </main>
    </div>

    <footer class="main-layout__footer">
      <RechtlicheHinweise :mobile-ansicht="istMobil" />
    </footer>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref, watchEffect } from "vue";
import GlobaleNavigation from "components/GlobaleNavigation.vue";
import SeitenMenue from "components/SeitenMenue.vue";
import RechtlicheHinweise from "components/RechtlicheHinweise.vue";

export default defineComponent({
  name: "HauptLayout",
  components: {
    GlobaleNavigation,
    SeitenMenue,
    RechtlicheHinweise,
  },
  setup() {
    const istMobil = ref<boolean>(window.innerWidth < 700);

    watchEffect(() => {
      const handleResize = () => {
        istMobil.value = window.innerWidth < 700;
      };
      window.addEventListener("resize", handleResize);
      return () => {
        window.removeEventListener("resize", handleResize);
      };
    });

    return {
      istMobil,
    };
  },
});
</script>

<style scoped lang="scss">
.main-layout {
  display: flex;
  flex-direction: column;
  height: 100vh;
  background-color: white;
  .main-layout__header,
  .main-layout__footer {
    padding: 20px;
    background-color: white;
  }
  .main-layout__header {
    display: flex;
    flex-direction: row;
    align-items: center;

    .header__title {
      font-size: 24px;
      margin-right: 48px;
    }

    &--mobil {
      flex-direction: column;
      .header__title {
        font-size: 32px;
        margin-right: none;
        text-align: center;
      }
    }
  }
  .main-layout__content {
    flex: 1;
    display: flex;
    align-items: stretch;
    background-color: lightgrey;

    .content__menue {
      padding: 20px;
      min-width: 200px;
    }

    .content__router-view {
      flex: 1;
      padding: 20px;
    }

    &--mobil {
      flex-direction: column-reverse;
      align-items: center;
      .content__menue {
        min-width: none;
        width: 100%;
      }
    }

    &--rtl {
      flex-direction: row-reverse;
    }
  }
}
</style>
