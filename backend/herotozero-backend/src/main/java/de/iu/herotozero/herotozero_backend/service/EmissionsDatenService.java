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
    
    @Inject
    private LandService landService;

    public EmissionsDaten getEmissionsDaten(Long id) {
        return emissionsDatenRepository.findById(id);
    }

    public List<EmissionsDaten> getAllEmissionsDaten() {
        return emissionsDatenRepository.findAll();
    }

    public void saveEmissionsDaten(EmissionsDaten emissionsDaten) {
        emissionsDatenRepository.save(emissionsDaten);
        if(emissionsDaten.getValidiert()) {            	
        	landService.updateGesamtEmissionen(emissionsDaten.getLandId());
        }
    }

    public EmissionsDaten updateEmissionsDaten(Long id, EmissionsDaten updatedEmissionsDaten) {
        EmissionsDaten existingEmissionsDaten = getEmissionsDaten(id);
        if (existingEmissionsDaten != null) {
            existingEmissionsDaten.setLandId(updatedEmissionsDaten.getLandId());
            existingEmissionsDaten.setJahr(updatedEmissionsDaten.getJahr());
            existingEmissionsDaten.setCo2Emissionen(updatedEmissionsDaten.getCo2Emissionen());
            existingEmissionsDaten.setValidiert(updatedEmissionsDaten.getValidiert());
            emissionsDatenRepository.save(existingEmissionsDaten);
            if(existingEmissionsDaten.getValidiert()) {            	
            	landService.updateGesamtEmissionen(existingEmissionsDaten.getLandId());
            }
            return existingEmissionsDaten;
        }
        return null;
    }

    public boolean deleteEmissionsDaten(Long id) {
        EmissionsDaten emissionsDaten = getEmissionsDaten(id);
        if (emissionsDaten != null) {
            Long landId = emissionsDaten.getLandId();
            emissionsDatenRepository.delete(emissionsDaten);
            landService.updateGesamtEmissionen(landId);
            return true;
        }
        return false;
    }
    
    public EmissionsDaten validateEmissionsDaten(Long id) {
        EmissionsDaten emissionsDaten = emissionsDatenRepository.findById(id);
        if (emissionsDaten != null) {
            emissionsDaten.setValidiert(true);
            emissionsDatenRepository.save(emissionsDaten);
            landService.updateGesamtEmissionen(emissionsDaten.getLandId());
            return emissionsDaten;
        }
        return null;
    }
}