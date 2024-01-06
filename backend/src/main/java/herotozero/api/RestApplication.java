package herotozero.api;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.logging.Logger;

@ApplicationPath("/api")
public class RestApplication extends Application {
    // Bleibt leer, da kein weiterer Code nötig!
	// Logger ist temporär zum testen eingebunden
	private static final Logger logger = Logger.getLogger(RestApplication.class.getName());

    public RestApplication() {
        logger.info("RestApplication gestartet");
    }
}
