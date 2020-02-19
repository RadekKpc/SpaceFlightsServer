package com.spaceflights.dataStructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "startDate",
        "endDate",
        "participantCapacity",
        "price"
})

public class Flight {
    @JsonProperty("id")
    private  int id;
    @JsonProperty("startDate")
    private  String startData;
    @JsonProperty("endDate")
    private  String endData;
    @JsonProperty("participantCapacity")
    private  int participantCapacity;
    @JsonProperty("price")
    private  float price;

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }
    @JsonProperty("startDate")
    public void setStartData(String startData) {
        this.startData = startData;
    }
    @JsonProperty("endDate")
    public void setEndData(String endData) {
        this.endData = endData;
    }
    @JsonProperty("participantCapacity")
    public void setParticipantCapacity(int participantCapacity) {
        this.participantCapacity = participantCapacity;
    }
    @JsonProperty("price")
    public void setPrice(float price) {
        this.price = price;
    }

    public Flight(){

    }
    public Flight(int id,String startData,String endData,int participantCapacity,float price){
        this.id = id;
        this.endData = endData;
        this.startData = startData;
        this.price = price;
        this.participantCapacity = participantCapacity;
    }
    @JsonProperty("startDate")
    public String getStartData() {
        return startData;
    }
    @JsonProperty("endDate")
    public String getEndData() {
        return endData;
    }
    @JsonProperty("participantCapacity")
    public int getParticipantCapacity() {
        return participantCapacity;
    }
    @JsonProperty("price")
    public float getPrice() {
        return price;
    }
    @JsonProperty("id")
    public int getId() {
        return id;
    }
}

