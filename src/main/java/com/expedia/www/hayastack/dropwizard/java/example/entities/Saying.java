package com.expedia.www.hayastack.dropwizard.java.example.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ragsingh on 04/02/19.
 */
public class Saying {

    private long id;

    private String saying;

    public Saying() {

    }

    public Saying(long id, String saying) {
        this.id = id;
        this.saying = saying;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getSaying() {
        return saying;
    }
}
