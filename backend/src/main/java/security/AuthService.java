package security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import model.Benutzer;
import org.eclipse.microprofile.jwt.JsonWebToken;
import java.util.HashSet;
import java.util.Set;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

@RequestScoped
public class AuthService {

    @Inject
    private JsonWebToken jwt;

    public boolean isUserInRole(String role) {
        return jwt.getGroups().contains(role);
    }

    public String getCurrentUserName() {
        return jwt.getName();
    }

    public Set<String> getCurrentUserRoles() {
        return new HashSet<>(jwt.getGroups());
    }

    public String createToken(Benutzer benutzer) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + 3600000; // umgerechnet 1h
        Date exp = new Date(expMillis);

//        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
        SecretKey key = Keys.hmacShaKeyFor("SecretKey".getBytes()); // Übergangsweise zum Testen, später auswechseln!!!
        return Jwts.builder()
                .subject(benutzer.getBenutzername())
                .claim("groups", benutzer.getRollen())
                .issuedAt(now)
                .expiration(exp)
                .signWith(key)
                .compact();
    }
}