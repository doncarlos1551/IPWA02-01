package de.iu.herotozero.herotozero_backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import javax.crypto.SecretKey;

public class KeyVerwaltung {

	public static String generiereUndKodiereSchluessel() {
        SecretKey schluessel = Jwts.SIG.HS256.key().build();
        return Encoders.BASE64.encode(schluessel.getEncoded());
    }
}