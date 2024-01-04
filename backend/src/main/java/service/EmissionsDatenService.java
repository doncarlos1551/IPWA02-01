package service;

import model.EmissionsDaten;
import repository.EmissionsDatenRepository;
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

    public void deleteEmissionsDaten(EmissionsDaten emissionsDaten) {
        emissionsDatenRepository.delete(emissionsDaten);
    }
}
