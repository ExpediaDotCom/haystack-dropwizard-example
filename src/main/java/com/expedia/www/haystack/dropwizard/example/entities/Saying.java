package com.expedia.www.haystack.dropwizard.example.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

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
