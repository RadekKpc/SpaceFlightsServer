package com.spaceflights.dataStructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "freePlaces"
})

public class FreePlaces {
    @JsonProperty("id")
    private  int id;
    @JsonProperty("freePlaces")
    private  int freePlaces;

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }
    @JsonProperty("freePlaces")
    public void freePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }
    @JsonProperty("id")
    public int getId() {
        return id;
    }
    @JsonProperty("freePlaces")
    public int freePlaces() {
        return freePlaces;
    }

    public FreePlaces(int id, int freePlaces) {
        this.id = id;
        this.freePlaces = freePlaces;
    }

    public FreePlaces(){

    }

}

