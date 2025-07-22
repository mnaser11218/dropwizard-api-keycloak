package com.example.auth;

import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.AuthenticationException;
import com.example.User;
import java.util.Optional;

public class KeycloakAuthenticator implements Authenticator<String, User> {
    @Override
    public Optional<User> authenticate(String token) throws AuthenticationException {
        // TODO: Validate the token with Keycloak and extract user roles
        // Example: return Optional.of(new User(Set.of("user", "admin")));
        return Optional.empty();
    }
}