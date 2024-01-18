// --- AUTH TYP-DEFINITIONEN ---
interface LoginRequest {
  benutzername: string;
  passwort: string;
}
interface LoginResponse {
  token: string;
}
interface RegisterRequest {
  benutzername: string;
  email: string;
  passwort: string;
}
type RegisterResponse = string;

// --- TEST TYP-DEFINITIONEN ---
type PublicTestResponse = string;
type PrivateTestResponse = string;

// --- BENUTZER TYP-DEFINITIONEN ---
export interface BenutzerResponse {
  id: number;
  benutzername: string;
  email: string;
}

export interface BenutzerRequest {
  benutzername: string;
  email: string;
  passwort: string;
}

type AlleBenutzerResponse = BenutzerNachIdResponse[];
type BenutzerNachIdResponse = BenutzerResponse;
type NeuerBenutzerRequest = BenutzerRequest;
type NeuerBenutzerResponse = BenutzerResponse;
type BenutzerAktualisierenRequest = BenutzerRequest;
type BenutzerAktualisierenResponse = BenutzerResponse;
type BenutzerLoeschenResponse = string;

// --- LAND TYP-DEFINITIONEN ---
export interface LandRequest {
  name: string;
}

export interface LandResponse {
  id: number;
  name: string;
  gesamtCo2Emissionen: number;
}

type AlleLaenderResponse = LandResponse[];
type LandNachIdResponse = LandResponse;
type NeuesLandRequest = LandRequest;
type NeuesLandResponse = LandResponse;
type LandAktualisierenRequest = LandRequest;
type LandAktualisierenResponse = LandResponse;
type LandLoeschenResponse = string;

// --- LAND TYP-DEFINITIONEN ---
export interface EmissionsDatenRequest {
  landId: number;
  unternehmen: string;
  jahr: number;
  co2Emissionen: number;
  validiert?: boolean; // optional für admin
}
export interface EmissionsDatenResponse {
  id: number;
  landId: number;
  unternehmen: string;
  jahr: number;
  co2Emissionen: number;
  validiert: boolean;
}

type AlleEmissionsDatenResponse = EmissionsDatenResponse[];
type EmissionsDatenNachIdResponse = EmissionsDatenResponse;
type NeueEmissionsDatenRequest = EmissionsDatenRequest;
type NeueEmissionsDatenResponse = EmissionsDatenResponse;
type EmissionsDatenAktualisierenRequest = EmissionsDatenRequest;
type EmissionsDatenAktualisierenResponse = EmissionsDatenResponse;
interface EmissionsDatenValidierenRequest {
  landId: number;
  jahr: number;
  co2Emissionen: number;
}
type EmissionsDatenValidierenResponse = EmissionsDatenResponse;
type EmissionsDatenLoeschenResponse = string;
type PublicKeyResponse = string;

import axios from 'axios';
import { JwtPayload, jwtDecode } from 'jwt-decode';
import { Notify } from 'quasar';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

export function useApiService() {
  const router = useRouter();

  const errorNachricht = ref<string | null>(null);
  const systemErrorNachricht = ref<string | null>(null);
  const decodedJwt = ref<JwtPayload | null>(null);

  const baseUrl = localStorage.getItem('baseUrl') || '';
  const jwtToken = localStorage.getItem('jwtToken');

  const apiPublic = axios.create({
    baseURL: baseUrl,
  });

  const apiPrivate = axios.create({
    baseURL: baseUrl,
    headers: {
      Authorization: `Bearer ${jwtToken}`,
    },
  });

  const isValidUrl = (url: string): boolean => /^http(s)?:\/\/.*/.test(url);

  if (!isValidUrl(baseUrl)) {
    errorNachricht.value = 'Ungültige Base URL';
    return;
  }

  // === UTIL METHODEN ===
  const showNotify = (message: string, type = 'negative') => {
    Notify.create({
      message: message.replace(/\n/g, '<br />'),
      html: true,
      type: type,
      position: 'top',
      timeout: 2500,
    });
  };

  const changeBaseUrl = (neueBaseUrl: string) => {
    localStorage.setItem('baseUrl', neueBaseUrl);
    logout();
  };

  const logout = () => {
    localStorage.removeItem('jwtToken');
    router.go(0);
  };

  const dekodiereJwt = () => {
    if (jwtToken) {
      try {
        decodedJwt.value = jwtDecode(jwtToken);
        console.log('DECODED', decodedJwt);
      } catch (error) {
        decodedJwt.value = null;
        console.error('Token-Decode-Fehler:', error);
      }
    } else {
      decodedJwt.value = null;
    }
  };

  const istJwtAbgelaufen = () => {
    const currentTime = Date.now() / 1000; // Zeit in Sekunden seit dem Unix-Epoch
    if (decodedJwt.value && decodedJwt.value.exp) {
      return decodedJwt.value.exp < currentTime;
    }
    return false;
  };

  // === AUTH METHODEN ===
  const login = async (loginData: LoginRequest) => {
    errorNachricht.value = null;
    try {
      const response = await apiPublic.post<LoginResponse>(
        `/auth/login`,
        loginData
      );

      localStorage.setItem('jwtToken', response.data.token);
      console.log('NewToken!', localStorage.getItem('jwtToken'));

      router.go(0);
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Login-Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Login-Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // Register
  const register = async (registerData: RegisterRequest) => {
    try {
      const response = await apiPublic.post<RegisterResponse>(
        `/auth/register`,
        registerData
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Register-Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Register-Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // === TEST METHODEN ===
  const publicTest = async () => {
    try {
      const response = await apiPublic.get<PublicTestResponse>(
        `/test/public-test`
      );
      showNotify(response.data, 'positive');
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Public Test Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Public Test Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const privateTest = async () => {
    try {
      const response = await apiPrivate.get<PrivateTestResponse>(
        `/test/private-test`,
        {
          headers: {
            Authorization: `Bearer ${jwtToken}`,
          },
        }
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Private Test Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Private Test Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // === BENUTZER METHODEN ===
  const alleBenutzer = async () => {
    try {
      const response = await apiPrivate.get<AlleBenutzerResponse>(`/benutzer`);
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Alle Benutzer Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Alle Benutzer Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const benutzerNachId = async (id: number) => {
    try {
      const response = await apiPrivate.get<BenutzerNachIdResponse>(
        `/benutzer/${id}`
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Benutzer Nach ID Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Benutzer Nach ID Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const neuerBenutzer = async (benutzerData: NeuerBenutzerRequest) => {
    try {
      const response = await apiPrivate.post<NeuerBenutzerResponse>(
        `/benutzer`,
        benutzerData
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Neuer Benutzer Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Neuer Benutzer Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const benutzerAktualisieren = async (
    id: number,
    benutzerData: BenutzerAktualisierenRequest
  ) => {
    try {
      const response = await apiPrivate.put<BenutzerAktualisierenResponse>(
        `/benutzer/${id}`,
        benutzerData
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Benutzer Aktualisieren Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Benutzer Aktualisieren Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const benutzerLoeschen = async (id: number) => {
    try {
      const response = await apiPrivate.delete<BenutzerLoeschenResponse>(
        `/benutzer/${id}`
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Benutzer Löschen Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Benutzer Löschen Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // === LAND METHODEN ===
  const alleLaender = async () => {
    try {
      const response = await apiPublic.get<AlleLaenderResponse>(`/laender/`);
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Alle Länder Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Alle Länder Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const landNachId = async (id: number) => {
    try {
      const response = await apiPublic.get<LandNachIdResponse>(
        `/laender/${id}`
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Land Nach ID Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Land Nach ID Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const neuesLand = async (landData: NeuesLandRequest) => {
    console.log(landData);
    try {
      const response = await apiPrivate.post<NeuesLandResponse>(
        `/laender`,
        landData
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Neues Land Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Neues Land Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const landAktualisieren = async (
    id: number,
    landData: LandAktualisierenRequest
  ) => {
    try {
      const response = await apiPrivate.put<LandAktualisierenResponse>(
        `/laender/${id}`,
        landData
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Land Aktualisieren Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Land Aktualisieren Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const landLoeschen = async (id: number) => {
    try {
      const response = await apiPrivate.delete<LandLoeschenResponse>(
        `/laender/${id}`
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Land Löschen Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Land Löschen Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // === EMISSIONSDATEN METHODEN ===
  const alleEmissionsDaten = async () => {
    try {
      const response = await apiPublic.get<AlleEmissionsDatenResponse>(
        `/emissionsdaten/`
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Alle EmissionsDaten Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Alle EmissionsDaten Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const emissionsDatenNachId = async (id: number) => {
    try {
      const response = await apiPublic.get<EmissionsDatenNachIdResponse>(
        `/emissionsdaten/${id}`
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `EmissionsDaten Nach ID Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `EmissionsDaten Nach ID Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const neueEmissionsDaten = async (
    emissionsDaten: NeueEmissionsDatenRequest
  ) => {
    try {
      const response = await apiPrivate.post<NeueEmissionsDatenResponse>(
        `/emissionsdaten/`,
        emissionsDaten
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Neue EmissionsDaten Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Neue EmissionsDaten Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const emissionsDatenAktualisieren = async (
    id: number,
    emissionsDaten: EmissionsDatenAktualisierenRequest
  ) => {
    try {
      const response =
        await apiPrivate.put<EmissionsDatenAktualisierenResponse>(
          `/emissionsdaten/${id}`,
          emissionsDaten
        );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `EmissionsDaten Aktualisieren Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `EmissionsDaten Aktualisieren Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const emissionsDatenValidieren = async (
    id: number,
    emissionsDaten: EmissionsDatenValidierenRequest
  ) => {
    try {
      const response = await apiPrivate.put<EmissionsDatenValidierenResponse>(
        `/emissionsdaten/validieren/${id}`,
        emissionsDaten
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `EmissionsDaten Validieren Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `EmissionsDaten Validieren Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const emissionsDatenLoeschen = async (id: number) => {
    try {
      const response = await apiPrivate.delete<EmissionsDatenLoeschenResponse>(
        `/emissionsdaten/${id}`
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `EmissionsDaten Löschen Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `EmissionsDaten Löschen Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // === PUBLIC METHODEN ===
  const publicKey = async () => {
    try {
      const response = await apiPublic.get<PublicKeyResponse>(
        `/public/public-key`
      );
      return response.data;
    } catch (error) {
      if (
        error instanceof Error &&
        axios.isAxiosError(error) &&
        error.response
      ) {
        systemErrorNachricht.value = error.message;
        let tempErrorNachricht = error.message;
        if (
          typeof error.response.data === 'object' &&
          'nachricht' in error.response.data &&
          typeof error.response.data.nachricht === 'string'
        ) {
          tempErrorNachricht = error.response.data.nachricht;
        }
        errorNachricht.value = `Public Key Fehler: ${tempErrorNachricht}`;
      } else {
        errorNachricht.value = `Public Key Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // ========

  onMounted(() => {
    dekodiereJwt();

    if (istJwtAbgelaufen()) {
      showNotify('Login-Token ist abgelaufen! Abmelden...');
      logout();
    }
  });

  return {
    errorNachricht,
    decodedJwt,
    baseUrl,
    jwtToken,
    changeBaseUrl,
    logout,
    login,
    register,
    publicTest,
    privateTest,
    alleBenutzer,
    benutzerNachId,
    neuerBenutzer,
    benutzerAktualisieren,
    benutzerLoeschen,
    alleLaender,
    landNachId,
    neuesLand,
    landAktualisieren,
    landLoeschen,
    alleEmissionsDaten,
    emissionsDatenNachId,
    neueEmissionsDaten,
    emissionsDatenAktualisieren,
    emissionsDatenValidieren,
    emissionsDatenLoeschen,
    publicKey,
  };
}
