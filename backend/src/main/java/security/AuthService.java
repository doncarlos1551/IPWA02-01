package security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import java.util.HashSet;
import java.util.Set;

@RequestScoped
public class AuthService {

    @Inject
    private JsonWebToken jwt;

    public boolean isUserInRole(String role) {
        return jwt.getGroups().contains(role);
    }

    public String getCurrentUserName() {
        return jwt.getName();
    }

    public Set<String> getCurrentUserRoles() {
        return new HashSet<>(jwt.getGroups());
    }
}
