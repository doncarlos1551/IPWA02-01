package de.iu.herotozero.herotozero_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "land")
public class Land {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name darf nicht leer sein")
    @Size(min = 2, max = 50, message = "Name muss zwischen 2 und 50 Zeichen lang sein")
    private String name;

    @NotNull
    private Double gesamtCo2Emissionen = 0.0;

    public Land() {
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Double getGesamtCo2Emissionen() {
        return gesamtCo2Emissionen;
    }

    public void setGesamtCo2Emissionen(Double gesamtCo2Emissionen) {
        this.gesamtCo2Emissionen = gesamtCo2Emissionen;
    }
}
