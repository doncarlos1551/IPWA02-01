package de.iu.herotozero.herotozero_backend.resource;

import de.iu.herotozero.herotozero_backend.model.Benutzer;
import de.iu.herotozero.herotozero_backend.service.AuthService;
import de.iu.herotozero.herotozero_backend.service.BenutzerService;
import de.iu.herotozero.herotozero_backend.util.PasswortVerschluesselung;
import de.iu.herotozero.herotozero_backend.util.StringResponse;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;

@Path("/auth")
public class AuthResource {

    @Inject
    private BenutzerService benutzerService;

    @Inject
    private AuthService authService;

    @POST
    @PermitAll
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(BenutzerCredentials credentials) {
        Benutzer benutzer = benutzerService.getBenutzer(credentials.getBenutzername());
        if (benutzer == null) {
            // Kein Benutzer gefunden - später gleiche message wie passwort falsch - wegen sicherheit
            return Response.status(Response.Status.UNAUTHORIZED).entity(new StringResponse("Benutzername oder Passwort ungültig - Benutzername falsch")).build();
        }
        if (PasswortVerschluesselung.checkPasswort(credentials.getPasswort(), benutzer.getPasswort())) {
        	// Passwort passt
            String token;
			try {
				token = authService.createToken(benutzer);
				return Response.ok(new TokenResponse(token)).build();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new StringResponse("Private Key konnte nicht geholt werden")).build();
			}
        } else {
            // Passwort passt nicht - später gleiche message wie nutzer existiert nicht - wegen sicherheit
            return Response.status(Response.Status.UNAUTHORIZED).entity(new StringResponse("Benutzername oder Passwort ungültig - Passwort falsch")).build();
        }
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    // Hier müsste eigentlich ein zusätzliches Maß Sicherheit für die Registrierung vorgenommen werden, es könnte zum Beispiel Bestätigung durch einen Administrator eingeführt werden oder Bestätigungs-Email, in diesem Fall beides zu viel Aufwand
    public Response register(Benutzer neuerBenutzer) {
        if (benutzerService.isBenutzernameVerfügbar(neuerBenutzer.getBenutzername())) {
        	neuerBenutzer.setPasswort(PasswortVerschluesselung.hashPasswort(neuerBenutzer.getPasswort()));
            benutzerService.createBenutzer(neuerBenutzer);
            return Response.status(Response.Status.CREATED).entity(new StringResponse("Benutzer wurde erstellt")).build();
        }
        return Response.status(Response.Status.CONFLICT).entity(new StringResponse("Benutzername bereits vergeben")).build();
    }

    public static class BenutzerCredentials {
        private String benutzername;
        private String passwort;

        // Getter und Setter
        public String getBenutzername() {
            return benutzername;
        }

        public void setBenutzername(String benutzername) {
            this.benutzername = benutzername;
        }

        public String getPasswort() {
            return passwort;
        }

        public void setPasswort(String passwort) {
            this.passwort = passwort;
        }
    }

    public static class TokenResponse {
        private String token;

        public TokenResponse(String token) {
            this.token = token;
        }

        // Getter und Setter
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}