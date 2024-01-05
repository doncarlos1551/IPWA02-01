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
    
    public Benutzer getBenutzer(String name) {
        return benutzerRepository.findByBenutzername(name);
    }

    public List<Benutzer> getAllBenutzer() {
        return benutzerRepository.findAll();
    }

    public void createBenutzer(Benutzer benutzer) {
        benutzerRepository.save(benutzer);
    }

    public Benutzer updateBenutzer(Long id, Benutzer updatedBenutzer) {
        Benutzer existingBenutzer = getBenutzer(id);
        if (existingBenutzer != null) {
            existingBenutzer.setBenutzername(updatedBenutzer.getBenutzername());
            benutzerRepository.save(existingBenutzer);
            return existingBenutzer;
        }
        return null;
    }

    public boolean deleteBenutzer(Long id) {
        Benutzer benutzer = getBenutzer(id);
        if (benutzer != null) {
            benutzerRepository.delete(benutzer);
            return true;
        }
        return false;
    }

    public boolean isBenutzernameVerfügbar(String benutzername) {
        Benutzer existierenderBenutzer = benutzerRepository.findByBenutzername(benutzername);
        return existierenderBenutzer == null;
    }
}
