<template>
  <div v-if="decodedJwt === null">
    <q-tabs v-model="tab" dense>
      <q-tab name="login" label="Login" />
      <q-tab name="register" label="Registrieren" />
    </q-tabs>

    <div v-if="tab === 'login'" class="login-komponente">
      <!-- Login-Formular -->
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

    <div v-if="tab === 'register'" class="register-komponente">
      <!-- Registrierungs-Formular -->
      <q-input v-model="benutzername" label="Benutzername" />
      <q-input v-model="email" label="E-Mail" />
      <q-input v-model="passwort" type="password" label="Passwort" />
      <q-btn
        label="Registrieren"
        color="primary"
        @click="register({ benutzername, email, passwort })"
      />
    </div>
    <span v-if="errorNachricht">{{ errorNachricht }}</span>
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
    const { errorNachricht, decodedJwt, login, logout, register } =
      useApiService();

    const benutzername = ref<string>('');
    const passwort = ref<string>('');
    const email = ref<string>('');
    const tab = ref<'login' | 'register'>('login');

    return {
      errorNachricht,
      decodedJwt,
      login,
      logout,
      register,
      benutzername,
      passwort,
      email,
      tab,
    };
  },
});
</script>

<style scoped lang="scss">
.login-komponente {
  display: flex;
  flex-direction: column;
}
.register-komponente {
  display: flex;
  flex-direction: column;
}
</style>
