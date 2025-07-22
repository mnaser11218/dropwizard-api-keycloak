package com.example;
import com.example.User;
import io.dropwizard.auth.Authorizer;
import jakarta.ws.rs.container.ContainerRequestContext;

public class RoleAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User user, String role, ContainerRequestContext context) {
        if (user == null || user.getRoles() == null) {
            return false;
        }
        return user.getRoles().contains(role);
    }
}
