package com.example.auth;

import com.example.User;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.jwt.proc.JWTProcessor;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import io.dropwizard.auth.Authenticator;

import java.net.URL;
import java.util.*;

public class KeycloakAuthenticator implements Authenticator<String, User> {

    private final ConfigurableJWTProcessor<SecurityContext> jwtProcessor;

    public KeycloakAuthenticator(String jwksUrl) throws Exception {
        RemoteJWKSet<SecurityContext> jwkSet = new RemoteJWKSet<>(new URL(jwksUrl));
        this.jwtProcessor = new DefaultJWTProcessor<>();
        JWSKeySelector<SecurityContext> keySelector =
                new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, jwkSet);
        this.jwtProcessor.setJWSKeySelector(keySelector);
    }

    @Override
    public Optional<User> authenticate(String token) {
        try {
            JWTClaimsSet claims = jwtProcessor.process(token, null);
            String username = claims.getStringClaim("preferred_username");

            Map<String, Object> realmAccess = (Map<String, Object>) claims.getClaim("realm_access");
            List<String> roles = (List<String>) realmAccess.get("roles");

            if (username == null || roles == null) {
                System.out.println("[WARN] Token missing username or roles");
                return Optional.empty();
            }

            System.out.println("[INFO] Authenticated user: " + username + " with roles: " + roles);
            return Optional.of(new User(username, new HashSet<>(roles)));
        } catch (Exception e) {
            System.out.println("[WARN] Invalid token: " + e.getMessage());
            return Optional.empty();
        }
    }
}

