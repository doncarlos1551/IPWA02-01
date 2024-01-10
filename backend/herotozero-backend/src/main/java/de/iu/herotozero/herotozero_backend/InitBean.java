package de.iu.herotozero.herotozero_backend;

import de.iu.herotozero.herotozero_backend.model.Benutzer;
import de.iu.herotozero.herotozero_backend.model.Konfiguration;
import de.iu.herotozero.herotozero_backend.service.BenutzerService;
import de.iu.herotozero.herotozero_backend.service.KonfigurationService;
import de.iu.herotozero.herotozero_backend.util.KeyVerwaltung;
import de.iu.herotozero.herotozero_backend.util.PasswortVerschluesselung;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

@Singleton
@Startup
public class InitBean {

    @Inject
    private BenutzerService benutzerService;

    @Inject
    private KonfigurationService konfigurationService;

    @PostConstruct
    public void init() {
        initAdminBenutzer();
        initSecretKey();
    }

    private void initAdminBenutzer() {
    	if (benutzerService.getBenutzer("Admin") == null) {
    	    Benutzer admin = new Benutzer("Admin", "admin@example.com", PasswortVerschluesselung.hashPasswort("HeroToAdmin"));
    	    benutzerService.createBenutzer(admin);
    	}
    }

    private void initSecretKey() {
    	if (konfigurationService.getKonfiguration() == null) {
            String kodierterSchluessel = KeyVerwaltung.generiereUndKodiereSchluessel();
            Konfiguration konfiguration = new Konfiguration();
            konfiguration.setSecretKey(kodierterSchluessel);
            konfigurationService.saveKonfiguration(konfiguration);
        }
    }
}

