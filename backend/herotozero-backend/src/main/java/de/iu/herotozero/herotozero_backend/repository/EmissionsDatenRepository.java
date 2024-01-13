package de.iu.herotozero.herotozero_backend.repository;

import de.iu.herotozero.herotozero_backend.model.EmissionsDaten;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import jakarta.ejb.Stateless;
//import jakarta.enterprise.context.ApplicationScoped;

@Stateless
//@ApplicationScoped
public class EmissionsDatenRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public EmissionsDaten findById(Long id) {
        return entityManager.find(EmissionsDaten.class, id);
    }

    public List<EmissionsDaten> findAll() {
        return entityManager.createQuery("SELECT e FROM EmissionsDaten e", EmissionsDaten.class).getResultList();
    }

    public void save(EmissionsDaten emissionsDaten) {
        if (emissionsDaten.getId() == null) {
            entityManager.persist(emissionsDaten);
        } else {
            entityManager.merge(emissionsDaten);
        }
    }

    public void delete(EmissionsDaten emissionsDaten) {
        entityManager.remove(entityManager.contains(emissionsDaten) ? emissionsDaten : entityManager.merge(emissionsDaten));
    }
    
    public Double berechneGesamtEmissionen(Long landId) {
        return entityManager.createQuery(
                "SELECT SUM(e.co2Emissionen) FROM EmissionsDaten e WHERE e.landId = :landId", Double.class)
            .setParameter("landId", landId)
            .getSingleResult();
    }

}