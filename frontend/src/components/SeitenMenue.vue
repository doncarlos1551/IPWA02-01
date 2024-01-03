<template>
  <div class="seiten-menue" :class="mobileAnsicht ? 'seiten-menue--mobil' : ''">
    <h5>Wichtige Links</h5>
    <ul class="seiten-menue__liste">
      <li class="liste__eintrag" v-for="link in allgemeineLinks" :key="link.name">
        <a v-if="link.typ === 'extern'" :href="link.url" target="_blank">{{
          link.name
        }}</a>
        <router-link v-else :to="link.url">{{ link.name }}</router-link>
      </li>
    </ul>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from "vue";

interface AllgemeinerLink {
  name: string;
  url: string;
  typ: "extern" | "intern";
}

export default defineComponent({
  name: "SeitenMenue",
  props: {
    mobileAnsicht: {
      type: Boolean,
      required: true,
    },
  },
  setup() {
    const allgemeineLinks = ref<AllgemeinerLink[]>([
      { name: "IU", url: "https://www.iu.de", typ: "extern" },
      { name: "Nachhaltigkeit", url: "/nachhaltigkeit", typ: "intern" },
    ]);

    return { allgemeineLinks };
  },
});
</script>

<style scoped lang="scss">
.seiten-menue {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  .seiten-menue__liste {
    display: flex;
    align-items: flex-start;
    flex-direction: column;
    list-style-type: none;
    margin-left: 24px;
    padding: 0px;
    gap: 12px;
    .liste__eintrag {
      font-size: 16px;
    }
  }
  &--mobil {
    align-items: center;
    .seiten-menue__liste {
      margin-left: 0px;
      align-items: center;
      .liste__eintrag {
        font-size: 18px;
      }
    }
  }
}
</style>
