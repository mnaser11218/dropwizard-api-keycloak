//package com.example.auth;
//
//import jakarta.ws.rs.container.ContainerRequestContext;
//import jakarta.ws.rs.container.ContainerRequestFilter;
//import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.ext.Provider;
//import java.io.IOException;
//import org.keycloak.TokenVerifier;
//import org.keycloak.representations.AccessToken;
//
//@Provider
//public class KeycloakAuthFilter implements ContainerRequestFilter {
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        String authHeader = requestContext.getHeaderString("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//            return;
//        }
//        String token = authHeader.substring("Bearer ".length());
//        try {
//            AccessToken accessToken = TokenVerifier.create(token, AccessToken.class).getToken();
//            // Optionally, check audience, issuer, or roles here
//        } catch (Exception e) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//        }
//    }
//}