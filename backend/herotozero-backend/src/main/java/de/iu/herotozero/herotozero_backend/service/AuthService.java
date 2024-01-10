package de.iu.herotozero.herotozero_backend.service;

import de.iu.herotozero.herotozero_backend.model.Benutzer;
import de.iu.herotozero.herotozero_backend.model.Konfiguration;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import io.jsonwebtoken.io.Decoders;

@RequestScoped
public class AuthService {

    @Inject
    private KonfigurationService konfigurationService;

    public String createToken(Benutzer benutzer) {
        long jetztMillis = System.currentTimeMillis();
        Date jetzt = new Date(jetztMillis);
        long ablaufMillis = jetztMillis + 3600000; // entspricht 1 Stunde
        Date ablauf = new Date(ablaufMillis);

        Konfiguration konfiguration = konfigurationService.getKonfiguration();
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(konfiguration.getSecretKey()));

        return Jwts.builder()
                .subject(benutzer.getBenutzername())
                .claim("groups", benutzer.getRollen())
                .issuedAt(jetzt)
                .expiration(ablauf)
                .signWith(key)
                .compact();
    }
}
