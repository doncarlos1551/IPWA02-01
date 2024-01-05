package herotozero.api;

import model.Benutzer;
import service.BenutzerService;
import util.PasswortVerschluesselung;

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
        if (benutzerService.getBenutzer("Admin") == null) {
            Benutzer admin = new Benutzer();
            admin.setBenutzername("Admin");
            admin.setPasswort(PasswortVerschluesselung.hashPasswort("HeroToAdmin"));
            benutzerService.createBenutzer(admin);
        }
    }
}
