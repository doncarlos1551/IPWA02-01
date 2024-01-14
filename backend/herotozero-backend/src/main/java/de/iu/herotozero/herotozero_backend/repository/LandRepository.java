package de.iu.herotozero.herotozero_backend.repository;

import de.iu.herotozero.herotozero_backend.model.Land;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jakarta.ejb.Stateless;

@Stateless
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
    
    public Double calculateGesamtEmissionen(Long landId) {
        return entityManager.createQuery(
                "SELECT SUM(e.co2Emissionen) FROM EmissionsDaten e WHERE e.landId = :landId AND e.validiert = TRUE", Double.class)
            .setParameter("landId", landId)
            .getSingleResult();
    }

}