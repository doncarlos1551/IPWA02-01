package model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.util.Date;

@Entity
public class EmissionsDaten {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "land_id", nullable = false)
    private Land land;

    @NotNull
    @Temporal(TemporalType.DATE)
    @PastOrPresent
    private Date jahr;

    @NotNull
    private Double co2Emissionen;

    public EmissionsDaten() {
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public Date getJahr() {
        return jahr;
    }

    public void setJahr(Date jahr) {
        this.jahr = jahr;
    }

    public Double getCo2Emissionen() {
        return co2Emissionen;
    }

    public void setCo2Emissionen(Double co2Emissionen) {
        this.co2Emissionen = co2Emissionen;
    }
}