package de.iu.herotozero.herotozero_backend.repository;

import de.iu.herotozero.herotozero_backend.model.Land;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jakarta.ejb.Stateless;
//import jakarta.enterprise.context.ApplicationScoped;

@Stateless
//@ApplicationScoped
public class LandRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Land findById(Long id) {
        return entityManager.find(Land.class, id);
    }

    public List<Land> findAll() {
        return entityManager.createQuery("SELECT l FROM Land l", Land.class).getResultList();
    }

    public void save(Land land) {
        if (land.getId() == null) {
            entityManager.persist(land);
        } else {
            entityManager.merge(land);
        }
    }

    public void delete(Land land) {
        entityManager.remove(entityManager.contains(land) ? land : entityManager.merge(land));
    }
}