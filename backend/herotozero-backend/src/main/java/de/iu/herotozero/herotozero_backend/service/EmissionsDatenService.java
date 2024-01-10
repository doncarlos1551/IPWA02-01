package de.iu.herotozero.herotozero_backend.service;

import de.iu.herotozero.herotozero_backend.model.EmissionsDaten;
import de.iu.herotozero.herotozero_backend.repository.EmissionsDatenRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class EmissionsDatenService {

    @Inject
    private EmissionsDatenRepository emissionsDatenRepository;

    public EmissionsDaten getEmissionsDaten(Long id) {
        return emissionsDatenRepository.findById(id);
    }

    public List<EmissionsDaten> getAllEmissionsDaten() {
        return emissionsDatenRepository.findAll();
    }

    public void saveEmissionsDaten(EmissionsDaten emissionsDaten) {
        emissionsDatenRepository.save(emissionsDaten);
    }

    public EmissionsDaten updateEmissionsDaten(Long id, EmissionsDaten updatedEmissionsDaten) {
        EmissionsDaten existingEmissionsDaten = getEmissionsDaten(id);
        if (existingEmissionsDaten != null) {
            existingEmissionsDaten.setLand(updatedEmissionsDaten.getLand());
            existingEmissionsDaten.setJahr(updatedEmissionsDaten.getJahr());
            existingEmissionsDaten.setCo2Emissionen(updatedEmissionsDaten.getCo2Emissionen());
            emissionsDatenRepository.save(existingEmissionsDaten);
            return existingEmissionsDaten;
        }
        return null;
    }

    public boolean deleteEmissionsDaten(Long id) {
        EmissionsDaten emissionsDaten = getEmissionsDaten(id);
        if (emissionsDaten != null) {
            emissionsDatenRepository.delete(emissionsDaten);
            return true;
        }
        return false;
    }
}