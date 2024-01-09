package de.iu.herotozero.herotozero_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Konfiguration {

    @Id
    private String secretKey;
    private String secretValue;

    public Konfiguration() {
    }

    public String getKey() {
        return secretKey;
    }

    public void setKey(String key) {
        this.secretKey = key;
    }

    public String getValue() {
        return secretValue;
    }

    public void setValue(String value) {
        this.secretValue = value;
    }
}
