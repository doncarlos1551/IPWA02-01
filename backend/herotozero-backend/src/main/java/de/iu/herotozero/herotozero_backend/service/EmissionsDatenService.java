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
        updateGesamtEmissionen(emissionsDaten.getLand());
    }

    public EmissionsDaten updateEmissionsDaten(Long id, EmissionsDaten updatedEmissionsDaten) {
        EmissionsDaten existingEmissionsDaten = getEmissionsDaten(id);
        if (existingEmissionsDaten != null) {
            existingEmissionsDaten.setLand(updatedEmissionsDaten.getLand());
            existingEmissionsDaten.setJahr(updatedEmissionsDaten.getJahr());
            existingEmissionsDaten.setCo2Emissionen(updatedEmissionsDaten.getCo2Emissionen());
            emissionsDatenRepository.save(existingEmissionsDaten);
            updateGesamtEmissionen(existingEmissionsDaten.getLand());
            return existingEmissionsDaten;
        }
        return null;
    }

    public boolean deleteEmissionsDaten(Long id) {
        EmissionsDaten emissionsDaten = getEmissionsDaten(id);
        if (emissionsDaten != null) {
            emissionsDatenRepository.delete(emissionsDaten);
            updateGesamtEmissionen(emissionsDaten.getLand());
            return true;
        }
        return false;
    }
    
    public void updateGesamtEmissionen(Land land) {
        Double gesamtEmissionen = emissionsDatenRepository.berechneGesamtEmissionen(land);
        land.setGesamtCo2Emissionen(gesamtEmissionen);
        landRepository.save(land);
    }
}