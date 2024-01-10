package de.iu.herotozero.herotozero_backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import de.iu.herotozero.herotozero_backend.model.Konfiguration;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import javax.crypto.SecretKey;
import java.util.List;

@Singleton
public class KeyVerwaltung {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String SCHLUESSEL_ID = "SECRET_KEY";

    public String holeOderErstelleSchluessel() {
        String kodierterSchluessel = findeSchluesselInDatenbank();
        if (kodierterSchluessel == null) {
            kodierterSchluessel = generiereUndSpeichereNeuenSchluessel();
        }
        return kodierterSchluessel;
    }

    private String findeSchluesselInDatenbank() {
        TypedQuery<Konfiguration> query = entityManager.createQuery(
            "SELECT k FROM Konfiguration k WHERE k.secretKey = :secretKey", Konfiguration.class);
        query.setParameter("secretKey", SCHLUESSEL_ID);

        List<Konfiguration> ergebnis = query.getResultList();
        if (!ergebnis.isEmpty()) {
            return ergebnis.get(0).getValue();
        }
        return null;
    }

    private String generiereUndKodiereSchluessel() {
        SecretKey schluessel = Jwts.SIG.HS256.key().build();
        return Encoders.BASE64.encode(schluessel.getEncoded());
    }

    public String generiereUndSpeichereNeuenSchluessel() {
        String kodierterSchluessel = generiereUndKodiereSchluessel();

        Konfiguration konfiguration = new Konfiguration();
        konfiguration.setKey(SCHLUESSEL_ID);
        konfiguration.setValue(kodierterSchluessel);

        entityManager.persist(konfiguration);

        return kodierterSchluessel;
    }
}