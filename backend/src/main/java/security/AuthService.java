package security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import model.Benutzer;
import util.KeyVerwaltung;
//import org.eclipse.microprofile.jwt.JsonWebToken;
//import java.util.HashSet;
//import java.util.Set;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import io.jsonwebtoken.io.Decoders;

@RequestScoped
public class AuthService {

//    @Inject
//    private JsonWebToken jwt;

    @Inject
    private KeyVerwaltung keyVerwaltung;

//    public boolean isBenutzerInRolle(String rolle) {
//        return jwt.getGroups().contains(rolle);
//    }
//
//    public String getAktuellenBenutzernamen() {
//        return jwt.getName();
//    }
//
//    public Set<String> getAktuelleBenutzerrollen() {
//        return new HashSet<>(jwt.getGroups());
//    }

    public String createToken(Benutzer benutzer) {
        long jetztMillis = System.currentTimeMillis();
        Date jetzt = new Date(jetztMillis);
        long ablaufMillis = jetztMillis + 3600000; // Umgerechnet 1 Stunde
        Date ablauf = new Date(ablaufMillis);

        String kodierterSchluessel = keyVerwaltung.holeOderErstelleSchluessel();
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(kodierterSchluessel));

        return Jwts.builder()
                .subject(benutzer.getBenutzername())
                .claim("groups", benutzer.getRollen())
                .issuedAt(jetzt)
                .expiration(ablauf)
                .signWith(key)
                .compact();
    }
}