package de.iu.herotozero.herotozero_backend.service;

import de.iu.herotozero.herotozero_backend.model.Land;
import de.iu.herotozero.herotozero_backend.repository.LandRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class LandService {

    @Inject
    private LandRepository landRepository;

    public Land getLand(Long id) {
        return landRepository.findById(id);
    }

    public List<Land> getAllLaender() {
        return landRepository.findAll();
    }

    public void createLand(Land land) {
        landRepository.save(land);
    }

    public Land updateLand(Long id, Land updatedLand) {
        Land existingLand = getLand(id);
        if (existingLand != null) {
            existingLand.setName(updatedLand.getName());
            landRepository.save(existingLand);
            return existingLand;
        }
        return null;
    }

    public boolean deleteLand(Long id) {
        Land land = getLand(id);
        if (land != null) {
            landRepository.delete(land);
            return true;
        }
        return false;
    }
    
    public void updateGesamtEmissionen(Long landId) {
        Land land = landRepository.findById(landId);
        if (land != null) {
            Double gesamtEmissionen = landRepository.calculateGesamtEmissionen(landId);
            if (gesamtEmissionen == null) {
                gesamtEmissionen = 0.0;
            }
            land.setGesamtCo2Emissionen(gesamtEmissionen);
            landRepository.save(land);
        }
    }
}