package de.iu.herotozero.herotozero_backend.service;

import de.iu.herotozero.herotozero_backend.model.Benutzer;
import de.iu.herotozero.herotozero_backend.util.KeyVerwaltung;
import jakarta.enterprise.context.RequestScoped;
import io.jsonwebtoken.Jwts;
import java.security.PrivateKey;
import java.util.Date;

@RequestScoped
public class AuthService {

    public String createToken(Benutzer benutzer) throws Exception {
        long jetztMillis = System.currentTimeMillis();
        Date jetzt = new Date(jetztMillis);
        long ablaufMillis = jetztMillis + 3600000; // entspricht 1 Stunde
        Date ablauf = new Date(ablaufMillis);

        PrivateKey privateKey = KeyVerwaltung.getPrivateKey();

        return Jwts.builder()
        		.issuer("de.iu.herotozero.herotozero_backend")
        		.audience().add("herotozero_frontend").and()
                .subject(benutzer.getBenutzername())
                .claim("groups", benutzer.getRollen())
                .issuedAt(jetzt)
                .expiration(ablauf)
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }
}