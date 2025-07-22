package com.example;

import com.example.auth.KeycloakAuthenticator;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jdbi.v3.core.Jdbi;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public void run(final HelloWorldConfiguration config, final Environment env) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(env, config.getDataSourceFactory(), "postgresql");

        final PersonDAO personDAO = jdbi.onDemand(PersonDAO.class);
        env.jersey().register(new HelloWorldResource(personDAO));

        // Register resources
        env.jersey().register(new HelloWorldResource(personDAO));
        // Register Keycloak authentication filter
        env.jersey().register(new com.example.auth.KeycloakAuthFilter());
        env.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new KeycloakAuthenticator())
                        .setAuthorizer(new RoleAuthorizer())
                        .setPrefix("Bearer")
                        .buildAuthFilter()
        ));


    }

}
