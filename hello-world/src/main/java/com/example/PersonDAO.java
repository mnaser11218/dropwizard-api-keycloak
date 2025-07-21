package com.example;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
@RegisterBeanMapper(Person.class)
public interface PersonDAO {
    @SqlQuery("SELECT id, name FROM person")
    List<Person> getAll();

    @SqlQuery("SELECT id, name FROM person WHERE id = :id")
    Person findById(@Bind("id") int id);

    @SqlUpdate("INSERT INTO person (name) VALUES (:name)")
    @GetGeneratedKeys
    int createPerson(@Bind("name") String name);

}
