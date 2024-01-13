package de.iu.herotozero.herotozero_backend;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;

@ApplicationPath("/api")
@LoginConfig(authMethod="MP-JWT", realmName="ApplicationDomain")
public class RestApplication extends Application {
    public RestApplication() {
    	// Bleibt leer, da kein weiterer Code nötig!
    }
}