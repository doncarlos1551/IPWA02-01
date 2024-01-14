package de.iu.herotozero.herotozero_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class EmissionsDaten {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "land_id", nullable = false)
	private Long landId;

	@NotNull
    private Integer jahr;

    @NotNull
    private Double co2Emissionen;
    
    @NotNull
    private Boolean validiert = false;

    // Nur für Hibernate! Nicht für Nutzung im Code vorgesehen.
    public EmissionsDaten() {
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLandId() {
        return landId;
    }

    public void setLandId(Long landId) {
        this.landId = landId;
    }

    public Integer getJahr() {
        return jahr;
    }

    public void setJahr(Integer jahr) {
        this.jahr = jahr;
    }

    public Double getCo2Emissionen() {
        return co2Emissionen;
    }

    public void setCo2Emissionen(Double co2Emissionen) {
        this.co2Emissionen = co2Emissionen;
    }
    
    public Boolean getValidiert() {
        return validiert;
    }

    public void setValidiert(Boolean validiert) {
        this.validiert = validiert;
    }
}