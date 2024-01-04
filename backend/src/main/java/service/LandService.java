package service;

import model.Land;
import repository.LandRepository;
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

    public void saveLand(Land land) {
        landRepository.save(land);
    }

    public void deleteLand(Land land) {
        landRepository.delete(land);
    }
}
