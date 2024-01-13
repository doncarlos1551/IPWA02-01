package de.iu.herotozero.herotozero_backend.service;

import de.iu.herotozero.herotozero_backend.model.EmissionsDaten;
import de.iu.herotozero.herotozero_backend.model.Land;
import de.iu.herotozero.herotozero_backend.repository.EmissionsDatenRepository;
import de.iu.herotozero.herotozero_backend.repository.LandRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class EmissionsDatenService {

    @Inject
    private EmissionsDatenRepository emissionsDatenRepository;
    
    @Inject
    private LandRepository landRepository;

    public EmissionsDaten getEmissionsDaten(Long id) {
        return emissionsDatenRepository.findById(id);
    }

    public List<EmissionsDaten> getAllEmissionsDaten() {
        return emissionsDatenRepository.findAll();
    }

    public void saveEmissionsDaten(EmissionsDaten emissionsDaten) {
        emissionsDatenRepository.save(emissionsDaten);
        updateGesamtEmissionen(emissionsDaten.getLandId());
    }

    public EmissionsDaten updateEmissionsDaten(Long id, EmissionsDaten updatedEmissionsDaten) {
        EmissionsDaten existingEmissionsDaten = getEmissionsDaten(id);
        if (existingEmissionsDaten != null) {
            existingEmissionsDaten.setLandId(updatedEmissionsDaten.getLandId());
            existingEmissionsDaten.setJahr(updatedEmissionsDaten.getJahr());
            existingEmissionsDaten.setCo2Emissionen(updatedEmissionsDaten.getCo2Emissionen());
            emissionsDatenRepository.save(existingEmissionsDaten);
            updateGesamtEmissionen(existingEmissionsDaten.getLandId());
            return existingEmissionsDaten;
        }
        return null;
    }

    public boolean deleteEmissionsDaten(Long id) {
        EmissionsDaten emissionsDaten = getEmissionsDaten(id);
        if (emissionsDaten != null) {
            Long landId = emissionsDaten.getLandId();
            emissionsDatenRepository.delete(emissionsDaten);
            updateGesamtEmissionen(landId);
            return true;
        }
        return false;
    }
    
    public void updateGesamtEmissionen(Long landId) {
        Land land = landRepository.findById(landId);
        if (land != null) {
            Double gesamtEmissionen = emissionsDatenRepository.berechneGesamtEmissionen(landId);
            if (gesamtEmissionen == null) {
                gesamtEmissionen = 0.0;
            }
            land.setGesamtCo2Emissionen(gesamtEmissionen);
            landRepository.save(land);
        }
    }
}