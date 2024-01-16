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
interface BenutzerResponse {
  id: number;
  benutzername: string;
  email: string;
}

interface BenutzerRequest {
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
interface LandRequest {
  name: string;
}

interface LandResponse {
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
interface EmissionsDatenRequest {
  landId: number;
  jahr: number;
  co2Emissionen: number;
  validiert?: boolean; // optional für admin
}
interface EmissionsDatenResponse {
  id: number;
  landId: number;
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
  const decodedJwt = ref<JwtPayload | null>(null);

  const baseUrl = localStorage.getItem('baseUrl') || '';
  const jwtToken = localStorage.getItem('jwtToken');

  const isValidUrl = (url: string): boolean => /^http(s)?:\/\/.*/.test(url);

  if (!isValidUrl(baseUrl)) {
    errorNachricht.value = 'Ungültige Base URL';
    return;
  }

  // === UTIL METHODEN ===
  const showNotify = (message: string, type = 'negative') => {
    Notify.create({
      message: message,
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

  // === AUTH METHODEN ===
  const login = async (loginData: LoginRequest) => {
    errorNachricht.value = null;
    try {
      const response = await axios.post<LoginResponse>(
        `${baseUrl}/auth/login`,
        loginData
      );

      localStorage.setItem('jwtToken', response.data.token);
      console.log('NewToken!', localStorage.getItem('jwtToken'));

      router.go(0);
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Login-Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Login-Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // Register
  const register = async (registerData: RegisterRequest) => {
    try {
      const response = await axios.post<RegisterResponse>(
        `${baseUrl}/auth/register`,
        registerData
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Register-Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Register-Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // === TEST METHODEN ===
  const publicTest = async () => {
    try {
      const response = await axios.get<PublicTestResponse>(
        `${baseUrl}/test/public-test`
      );
      showNotify(response.data, 'positive');
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Public Test Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Public Test Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const privateTest = async () => {
    try {
      const response = await axios.get<PrivateTestResponse>(
        `${baseUrl}/test/private-test`
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Private Test Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Private Test Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // === BENUTZER METHODEN ===
  const alleBenutzer = async () => {
    try {
      const response = await axios.get<AlleBenutzerResponse>(
        `${baseUrl}/benutzer`
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Alle Benutzer Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Alle Benutzer Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const benutzerNachId = async (id: number) => {
    try {
      const response = await axios.get<BenutzerNachIdResponse>(
        `${baseUrl}/benutzer/${id}`
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Benutzer Nach ID Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Benutzer Nach ID Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const neuerBenutzer = async (benutzerData: NeuerBenutzerRequest) => {
    try {
      const response = await axios.post<NeuerBenutzerResponse>(
        `${baseUrl}/benutzer`,
        benutzerData
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Neuer Benutzer Fehler: ${error.message}`;
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
      const response = await axios.put<BenutzerAktualisierenResponse>(
        `${baseUrl}/benutzer/${id}`,
        benutzerData
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Benutzer Aktualisieren Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Benutzer Aktualisieren Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const benutzerLoeschen = async (id: number) => {
    try {
      const response = await axios.delete<BenutzerLoeschenResponse>(
        `${baseUrl}/benutzer/${id}`
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Benutzer Löschen Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Benutzer Löschen Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // === LAND METHODEN ===
  const alleLaender = async () => {
    try {
      const response = await axios.get<AlleLaenderResponse>(
        `${baseUrl}/laender/`
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Alle Länder Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Alle Länder Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const landNachId = async (id: number) => {
    try {
      const response = await axios.get<LandNachIdResponse>(
        `${baseUrl}/laender/${id}`
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Land Nach ID Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Land Nach ID Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const neuesLand = async (landData: NeuesLandRequest) => {
    try {
      const response = await axios.post<NeuesLandResponse>(
        `${baseUrl}/laender`,
        landData
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Neues Land Fehler: ${error.message}`;
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
      const response = await axios.put<LandAktualisierenResponse>(
        `${baseUrl}/laender/${id}`,
        landData
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Land Aktualisieren Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Land Aktualisieren Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const landLoeschen = async (id: number) => {
    try {
      const response = await axios.delete<LandLoeschenResponse>(
        `${baseUrl}/laender/${id}`
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Land Löschen Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Land Löschen Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // === EMISSIONSDATEN METHODEN ===
  const alleEmissionsDaten = async () => {
    try {
      const response = await axios.get<AlleEmissionsDatenResponse>(
        `${baseUrl}/emissionsdaten/`
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Alle EmissionsDaten Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Alle EmissionsDaten Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const emissionsDatenNachId = async (id: number) => {
    try {
      const response = await axios.get<EmissionsDatenNachIdResponse>(
        `${baseUrl}/emissionsdaten/${id}`
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `EmissionsDaten Nach ID Fehler: ${error.message}`;
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
      const response = await axios.post<NeueEmissionsDatenResponse>(
        `${baseUrl}/emissionsdaten/`,
        emissionsDaten
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Neue EmissionsDaten Fehler: ${error.message}`;
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
      const response = await axios.put<EmissionsDatenAktualisierenResponse>(
        `${baseUrl}/emissionsdaten/${id}`,
        emissionsDaten
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `EmissionsDaten Aktualisieren Fehler: ${error.message}`;
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
      const response = await axios.put<EmissionsDatenValidierenResponse>(
        `${baseUrl}/emissionsdaten/validieren/${id}`,
        emissionsDaten
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `EmissionsDaten Validieren Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `EmissionsDaten Validieren Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  const emissionsDatenLoeschen = async (id: number) => {
    try {
      const response = await axios.delete<EmissionsDatenLoeschenResponse>(
        `${baseUrl}/emissionsdaten/${id}`
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `EmissionsDaten Löschen Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `EmissionsDaten Löschen Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // === PUBLIC METHODEN ===
  const publicKey = async () => {
    try {
      const response = await axios.get<PublicKeyResponse>(
        `${baseUrl}/public/public-key`
      );
      return response.data;
    } catch (error) {
      if (error instanceof Error) {
        errorNachricht.value = `Public Key Fehler: ${error.message}`;
      } else {
        errorNachricht.value = `Public Key Fehler: Unbekannter Fehler`;
      }
      showNotify(errorNachricht.value);
    }
  };

  // ========

  onMounted(() => {
    dekodiereJwt();
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
