package com.example;

import io.dropwizard.auth.Auth;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final PersonDAO personDAO;

    public HelloWorldResource(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GET
    public List<Person> getAllPeople(@Auth User user) {
        return personDAO.getAll();
    }

    @GET
    @Path("/{id}")
    public Person getPersonById(@PathParam("id") int id) {
        return personDAO.findById(id);
    }


    @POST
    @RolesAllowed("admin")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPerson(@Auth User user, Person person) {
        personDAO.createPerson(person.getName());
        return Response.ok("Person created by " + user.getName()).build();
    }
}


//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response createPerson(Person person) {
//        int id = personDAO.createPerson(person.getName());
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("id", id);
//
//        return Response.status(Response.Status.CREATED)
//                .entity(response)
//                .build();
//    }

