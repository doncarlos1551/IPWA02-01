import { LandRequest } from './../composables/useApiService';
import { LandEmission } from '@/components/LandCo2Tabelle.vue';
import { UnternehmenEmission } from '@/components/UnternehmenCo2Tabelle.vue';
import {
  EmissionsDatenRequest,
  EmissionsDatenResponse,
  LandResponse,
} from '@/composables/useApiService';

export function convertLandResponseZuLandEmission(
  landResponse: LandResponse
): LandEmission {
  return {
    id: landResponse.id,
    land: landResponse.name,
    co2: landResponse.gesamtCo2Emissionen,
  };
}

export function convertEmissionsDatenResponseZuUnternehmenEmission(
  emissionsDatenResponse: EmissionsDatenResponse,
  landEmissionen: LandEmission[]
): UnternehmenEmission {
  const tempLand =
    landEmissionen.find((land) => land.id === emissionsDatenResponse.landId)
      ?.land ?? 'Unbekanntes Land';
  return {
    id: emissionsDatenResponse.id,
    unternehmen: emissionsDatenResponse.unternehmen,
    land: tempLand,
    co2: emissionsDatenResponse.co2Emissionen,
    validiert: emissionsDatenResponse.validiert,
  };
}

export function convertLandEmissionZuLandRequest(
  landEmission: LandEmission
): LandRequest {
  return {
    name: landEmission.land,
  };
}

export function convertUnternehmenEmissionZuEmissionsDatenRequest(
  unternehmenEmission: UnternehmenEmission,
  landEmissionen: LandEmission[]
): EmissionsDatenRequest {
  const tempLandId =
    landEmissionen.find(
      (emission) => emission.land === unternehmenEmission.land
    )?.id ?? -1;
  return {
    landId: tempLandId,
    unternehmen: unternehmenEmission.unternehmen,
    jahr: unternehmenEmission.jahr,
    co2Emissionen: unternehmenEmission.co2,
    validiert: unternehmenEmission.validiert ?? false,
  };
}
