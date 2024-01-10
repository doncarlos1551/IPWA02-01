package de.iu.herotozero.herotozero_backend.repository;

import de.iu.herotozero.herotozero_backend.model.Benutzer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jakarta.ejb.Stateless;
// import jakarta.enterprise.context.ApplicationScoped;

@Stateless
// @ApplicationScoped
public class BenutzerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Benutzer findById(Long id) {
        return entityManager.find(Benutzer.class, id);
    }
    
    public Benutzer findByBenutzername(String benutzername) {
        try {
            return entityManager.createQuery("SELECT b FROM Benutzer b WHERE b.benutzername = :benutzername", Benutzer.class)
                                .setParameter("benutzername", benutzername)
                                .getSingleResult();
        } catch (Exception e) {
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
