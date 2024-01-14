package de.iu.herotozero.herotozero_backend.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

// In der KeyVerwaltung wurden zu Testzwecken die Private und Public-Keys vorerst in Klartest eingefügt. Das ist sehr schlechte Praxis und sollte nur zum Testen so verwendet werden. Später können die Keys über die System-Environment Variablen gesetzt werden durch umkommentieren.
public class KeyVerwaltung {

    public static PrivateKey getPrivateKey() throws Exception {
//        String privateKeyPEM = System.getenv("HEROTOZERO_APP_PRIVATE_KEY"); // Für Production diese Zeile einkommentieren und die nächste Zeile löschen
    	String privateKeyPEM = "-----BEGIN PRIVATE KEY-----MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC0PrIJ/FfQPFxR+f96em4MPS9Oe1OEn3I4jaOQyUNZ1rDIdCHS/mIRgmEMphIx3AyNR8eEYFC9fxr6sQRJbyD179Tlywav/68ZFkCywteTpq0hF5oJ6LDKSRoHlL747Tv9aIocjCJoXmMh5Ev7lJKfAFXnFIqsVVXRoLrto5CoqMkDwmWcGxPEWlm7d1fhS6KswoeoZJzT7g0TmRUp6rLaCHC9Ve/D/q0xsaKiK3gfAX9bvOAvFAirm6uVXG3I35dvXq5iUarEwYXNz8OXXZmPb6apeD580FaK1Qo6/4zfLRozo53U11oWN4J/ir0xNE0ghELSEgL0Ry6tUlsgu+IVAgMBAAECggEABgfZ+6EMjBxU525CqMWrZG/wowLX1MZj8zXz/M5rGTiochCGqDFYNSdEWYC995qZ9ekqqQhy8yArVitxjgZwlyH24Nhl6SIYKPH4aLLBtUA8pzCLuBynu6DuBMZqjFgdq3khs9pRdWYBEZt6mliKflTryt9CU4lIZYrn7jcMikQK6OiyTT2gu1IC72wbXuJBO6N8T220nnq3tmhM4miQCOH2Fpwxp4HZvmKIT8jsiTlEvguY6ElJl+IbzV1ddKvS8BRlUyg7647oA/gA3YQw8Hudcal8NTzGT/RViExe8vvKNXxlXBmnEFVa9gGYKmI/UWH32Srq+TSDvkJHZJxiwQKBgQD6PYKnUlhmk+6RPMDc8oSMGaOm2MSIgGlYwln2+Cb8byWWGtPMUo03jv3MoDszJR9CUrFe3nm1QgfIsjlKQW/VbU6tDE16YjBDvMhPdv0BiW3FL9Ksuwe5JEB6ZDaLvlaUSdBFGDUN+JQHngm50VPYMd+Iq+3ZydEyy7bx9W/FQQKBgQC4ZMBvDXBfRAGhCrBu95PZ6Dvd7nlmpDXn70sM8xsicgId5xghFa2sISrHlM9HnWmkLNFetmnym6sY+Vu5bgLZGzZH2Ii/bRDa/gavWEOWR/a9ZZMFhCR9cXqsqQ9u44Rlt42bhHKHMSyYhZ3UeTExpAl7s7Jff3SAW9cNLy0D1QKBgC5YuhwXSjwlZegMYoQJfrccAI1kVHIbpmdEB7mQGFhklYeJjHSpTXJez27Ytl4wn+t+uU8i0VPa2Oo46Bdyqy3SQuIWBzuFd/7ztf3RweZI98Wvp8tKEJIfvrlsWhXjuNFaInLJmvntci0+/fomZOsDwazkevb9Q/uuc0G4/WyBAoGACo3Bsdl8EAfNbBE3jfJfpP40xvwKOMS3TSIWEpSrfeo2ks2SRPp/qTVwxGGRB5FL5ZinFxurrrYYMeK7zD4urYxwK+aJ1SMB7vbRHSIZJDXUOnzg0FUC3acsFUvL1VXyUvwRf9Ax2ekHm2cyZGHXXfb8j/b2A59A5MPc6CVGF00CgYA47r8ujLoPJ5sd/rh/13cAytMybslSI1fHQbEZTML7PQyJ6V1eAGo08KTHoB+/ZMW3fmGuVxN/R+cdw7UoqfhstDqcmK96HxXSdY9rh+y5nMEu9S9z1uD4x9slCU99Uey5ILaYCM+gm170iNpCSJPX8R0yZNE57TokpvAaZ9wlxQ==-----END PRIVATE KEY-----";   
        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");
        privateKeyPEM = privateKeyPEM.replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey getPublicKey() throws Exception {
//        String publicKeyPEM = System.getenv("HEROTOZERO_APP_PUBLIC_KEY"); // Für Production diese Zeile einkommentieren und die nächste Zeile löschen
        String publicKeyPEM = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtD6yCfxX0DxcUfn/enpuDD0vTntThJ9yOI2jkMlDWdawyHQh0v5iEYJhDKYSMdwMjUfHhGBQvX8a+rEESW8g9e/U5csGr/+vGRZAssLXk6atIReaCeiwykkaB5S++O07/WiKHIwiaF5jIeRL+5SSnwBV5xSKrFVV0aC67aOQqKjJA8JlnBsTxFpZu3dX4UuirMKHqGSc0+4NE5kVKeqy2ghwvVXvw/6tMbGioit4HwF/W7zgLxQIq5urlVxtyN+Xb16uYlGqxMGFzc/Dl12Zj2+mqXg+fNBWitUKOv+M3y0aM6Od1NdaFjeCf4q9MTRNIIRC0hIC9EcurVJbILviFQIDAQAB-----END PUBLIC KEY-----";
        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
        publicKeyPEM = publicKeyPEM.replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        return keyFactory.generatePublic(keySpec);
    }
}