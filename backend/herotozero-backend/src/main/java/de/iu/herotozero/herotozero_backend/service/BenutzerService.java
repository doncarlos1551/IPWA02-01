package de.iu.herotozero.herotozero_backend.service;

import de.iu.herotozero.herotozero_backend.model.Benutzer;
import de.iu.herotozero.herotozero_backend.repository.BenutzerRepository;
import de.iu.herotozero.herotozero_backend.resource.AuthResource;
import de.iu.herotozero.herotozero_backend.util.PasswortVerschluesselung;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class BenutzerService {

	private static final Logger LOGGER = Logger.getLogger(AuthResource.class.getName());
	
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
        String hashedPassword = PasswortVerschluesselung.hashPasswort(benutzer.getPasswort());
        benutzer.setPasswort(hashedPassword);
        benutzerRepository.save(benutzer);
    }

    public Benutzer updateBenutzer(Long id, Benutzer updatedBenutzer) {
    	if (id == 1) {
            LOGGER.warning("Admin-Account aktualisieren wurde abgelehnt.");
            return null;
        }
        Benutzer existingBenutzer = getBenutzer(id);
        if (existingBenutzer != null) {
            existingBenutzer.setBenutzername(updatedBenutzer.getBenutzername());
            if (!existingBenutzer.getPasswort().equals(updatedBenutzer.getPasswort())) {
                String hashedPassword = PasswortVerschluesselung.hashPasswort(updatedBenutzer.getPasswort());
                existingBenutzer.setPasswort(hashedPassword);
            }
            benutzerRepository.save(existingBenutzer);
            return existingBenutzer;
        }
        return null;
    }

	public boolean deleteBenutzer(Long id) {
    	if (id == 1) {
            LOGGER.warning("Admin-Account aktualisieren wurde abgelehnt.");
            return false;
    	}
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