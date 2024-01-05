package model;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "benutzer")
public class Benutzer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Benutzername darf nicht leer sein")
    @Size(min = 4, max = 50, message = "Benutzername muss zwischen 4 und 50 Zeichen sein")
    @Column(unique = true)
    private String benutzername;

    @NotBlank(message = "E-Mail darf nicht leer sein")
    @Email(message = "E-Mail muss gültig sein")
    private String email;

    @NotBlank(message = "Passwort darf nicht leer sein")
    private String passwort; // Verschlüsselung später beachten!!!

    public Benutzer() {
    }

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBenutzername() {
        return benutzername;
    }

    public void setBenutzername(String benutzername) {
        this.benutzername = benutzername;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }
    
    public List<String> getRollen() {
    	if(benutzername.equals("Admin")) {
    		return List.of("User", "Admin");
    	}
        return List.of("User");
    }
}
