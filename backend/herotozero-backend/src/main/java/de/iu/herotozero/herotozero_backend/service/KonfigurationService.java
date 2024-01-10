package de.iu.herotozero.herotozero_backend.service;

import de.iu.herotozero.herotozero_backend.model.Konfiguration;
import de.iu.herotozero.herotozero_backend.repository.KonfigurationRepository;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
public class KonfigurationService {

    @Inject
    private KonfigurationRepository konfigurationRepository;

    public Konfiguration getKonfiguration() {
        return konfigurationRepository.getKonfiguration();
    }

    public void saveKonfiguration(Konfiguration konfiguration) {
        konfigurationRepository.save(konfiguration);
    }
}
