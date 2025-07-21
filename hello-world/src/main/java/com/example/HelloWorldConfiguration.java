package com.example;

import io.dropwizard.core.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.*;
import jakarta.validation.constraints.*;

public class HelloWorldConfiguration extends Configuration {
    // TODO: implement service configuration

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory factory) {
        this.database = factory;
    }
}
