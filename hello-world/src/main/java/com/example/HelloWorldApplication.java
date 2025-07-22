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
    public void run(final HelloWorldConfiguration config, final Environment env) throws Exception {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(env, config.getDataSourceFactory(), "postgresql");
        String jwksUrl = "http://localhost:8081/realms/persons-project/protocol/openid-connect/certs";

        final PersonDAO personDAO = jdbi.onDemand(PersonDAO.class);
        env.jersey().register(new HelloWorldResource(personDAO));


        // this works for authentication but not for authorization
//        env.jersey().register(new AuthDynamicFeature(
//                new OAuthCredentialAuthFilter.Builder<User>()
//                        .setAuthenticator(new KeycloakAuthenticator(jwksUrl))
//                        .setPrefix("Bearer")
//                        .buildAuthFilter()
//        ));
//        env.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        env.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new KeycloakAuthenticator(jwksUrl))
                        .setAuthorizer(new RoleAuthorizer())
                        .setPrefix("Bearer")
                        .buildAuthFilter()
        ));

        env.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        env.jersey().register(RolesAllowedDynamicFeature.class);
    }

}
