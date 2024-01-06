package resource;

import model.Benutzer;
import service.AuthService;
import service.BenutzerService;
import util.PasswortVerschluesselung;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;

@Path("/auth")
public class AuthResource {

    @Inject
    private BenutzerService benutzerService;

    @Inject
    private AuthService authService;

    @POST
    @Path("/login")
    public Response login(BenutzerCredentials credentials) {
        Benutzer benutzer = benutzerService.getBenutzer(credentials.getBenutzername());
        if (benutzer != null && PasswortVerschluesselung.checkPasswort(credentials.getPasswort(), benutzer.getPasswort())) {
            String token = authService.createToken(benutzer);
            return Response.ok(new TokenResponse(token)).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path("/register")
    public Response register(Benutzer newUser) {
        if (benutzerService.isBenutzernameVerfügbar(newUser.getBenutzername())) {
            newUser.setPasswort(PasswortVerschluesselung.hashPasswort(newUser.getPasswort()));
            benutzerService.createBenutzer(newUser);
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.CONFLICT).entity("Benutzername bereits vergeben").build();
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