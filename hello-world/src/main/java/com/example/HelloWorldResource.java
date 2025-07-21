package com.example;

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
    public List<Person> getAllPeople() {
        return personDAO.getAll();
    }

    @GET
    @Path("/{id}")
    public Person getPersonById(@PathParam("id") int id) {
        return personDAO.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPerson(Person person) {
        int id = personDAO.createPerson(person.getName());

        Map<String, Object> response = new HashMap<>();
        response.put("id", id);

        return Response.status(Response.Status.CREATED)
                .entity(response)
                .build();
    }
}
