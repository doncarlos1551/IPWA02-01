<template>
  <div v-if="decodedJwt === null" class="login-komponente">
    <q-input v-model="benutzername" label="Benutzername" />
    <q-input
      v-model="passwort"
      type="password"
      label="Passwort"
      @keyup.enter="login({ benutzername, passwort })"
    />
    <q-btn
      label="Login"
      color="primary"
      @click="login({ benutzername, passwort })"
    />
  </div>
  <div v-else>
    <span>Du bist bereits eingeloggt, {{ decodedJwt.sub }}!</span>
    <br />
    <span @click="logout()"><a>logout</a></span>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue';
import { useApiService } from '@/composables/useApiService';

export default defineComponent({
  name: 'LoginKomponente',
  setup() {
    const { login, logout, decodedJwt } = useApiService();

    const benutzername = ref<string>('');
    const passwort = ref<string>('');

    return {
      decodedJwt,
      login,
      logout,
      benutzername,
      passwort,
    };
  },
});
</script>

<style scoped lang="scss">
.login-komponente {
  display: flex;
  flex-direction: column;
}
</style>
