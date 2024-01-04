package service;

import model.Benutzer;
import repository.BenutzerRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class BenutzerService {

    @Inject
    private BenutzerRepository benutzerRepository;

    public Benutzer getBenutzer(Long id) {
        return benutzerRepository.findById(id);
    }

    public List<Benutzer> getAllBenutzer() {
        return benutzerRepository.findAll();
    }

    public void saveBenutzer(Benutzer benutzer) {
        benutzerRepository.save(benutzer);
    }

    public void deleteBenutzer(Benutzer benutzer) {
        benutzerRepository.delete(benutzer);
    }
}
