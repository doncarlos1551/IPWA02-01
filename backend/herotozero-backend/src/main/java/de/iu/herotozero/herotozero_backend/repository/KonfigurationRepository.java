package de.iu.herotozero.herotozero_backend.repository;

import de.iu.herotozero.herotozero_backend.model.Konfiguration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ejb.Stateless;

@Stateless
public class KonfigurationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Konfiguration getKonfiguration() {
        try {
            return entityManager.createQuery("SELECT k FROM Konfiguration k", Konfiguration.class)
                                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void save(Konfiguration konfiguration) {
        if (getKonfiguration() == null) {
            entityManager.persist(konfiguration);
        } else {
            entityManager.merge(konfiguration);
        }
    }
}
