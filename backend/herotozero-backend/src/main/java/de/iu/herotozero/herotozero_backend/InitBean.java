package de.iu.herotozero.herotozero_backend;

import de.iu.herotozero.herotozero_backend.model.Benutzer;
import de.iu.herotozero.herotozero_backend.service.BenutzerService;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class InitBean {

    @Inject
    private BenutzerService benutzerService;

    @PostConstruct
    public void init() {
        initAdminBenutzer();
    }

    private void initAdminBenutzer() {
    	if (benutzerService.getBenutzer("Admin") == null) {
    	    Benutzer admin = new Benutzer("Admin", "admin@example.com", "HeroToAdmin");
    	    benutzerService.createBenutzer(admin);
    	}
    }
}

