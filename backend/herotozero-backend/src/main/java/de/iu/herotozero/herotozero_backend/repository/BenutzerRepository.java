package de.iu.herotozero.herotozero_backend.repository;

import de.iu.herotozero.herotozero_backend.model.Benutzer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jakarta.ejb.Stateless;
// import jakarta.enterprise.context.ApplicationScoped;

import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
// @ApplicationScoped
public class BenutzerRepository {

	private static final Logger LOGGER = Logger.getLogger(BenutzerRepository.class.getName());
	
    @PersistenceContext
    private EntityManager entityManager;

    public Benutzer findById(Long id) {
        return entityManager.find(Benutzer.class, id);
    }
    
    public Benutzer findByBenutzername(String benutzername) {
        try {
        	Benutzer benutzer =  entityManager.createQuery("SELECT b FROM Benutzer b WHERE b.benutzername = :benutzername", Benutzer.class)
                                .setParameter("benutzername", benutzername)
                                .getSingleResult();
        	if (benutzer != null) {
                LOGGER.log(Level.INFO, "Benutzer gefunden: " + benutzername);
            }
            return benutzer;
        } catch (jakarta.persistence.NoResultException e) {
            return null; // oder eine andere geeignete Antwort
        } catch (Exception e) {
        	LOGGER.log(Level.SEVERE, "Fehler beim Suchen des Benutzers: " + benutzername, e);
            // Erstmal keine besondere Behandlung des Fehlers!!!
            return null;
        }
    }


    public List<Benutzer> findAll() {
        return entityManager.createQuery("SELECT b FROM Benutzer b", Benutzer.class).getResultList();
    }

    public void save(Benutzer benutzer) {
        if (benutzer.getId() == null) {
            entityManager.persist(benutzer);
        } else {
            entityManager.merge(benutzer);
        }
    }

    public void delete(Benutzer benutzer) {
        entityManager.remove(entityManager.contains(benutzer) ? benutzer : entityManager.merge(benutzer));
    }
}
