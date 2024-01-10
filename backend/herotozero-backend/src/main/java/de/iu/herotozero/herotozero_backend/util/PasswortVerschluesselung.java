package de.iu.herotozero.herotozero_backend.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswortVerschluesselung {

    public static String hashPasswort(String passwortKlartext) {
        return BCrypt.hashpw(passwortKlartext, BCrypt.gensalt());
    }

    public static boolean checkPasswort(String passwortKlartext, String gespeichertesHashPasswort) {
        return BCrypt.checkpw(passwortKlartext, gespeichertesHashPasswort);
    }
}
