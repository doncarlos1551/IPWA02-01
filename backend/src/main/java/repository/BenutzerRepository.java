package repository;

import model.Benutzer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

public class BenutzerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Benutzer findById(Long id) {
        return entityManager.find(Benutzer.class, id);
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
