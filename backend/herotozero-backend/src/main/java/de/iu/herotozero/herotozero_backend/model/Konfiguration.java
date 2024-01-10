package de.iu.herotozero.herotozero_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Konfiguration {

    @Id
    private String secretKey;

    // Nur für Hibernate! Nicht für Nutzung im Code vorgesehen.
    public Konfiguration() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}