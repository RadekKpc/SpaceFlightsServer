package com.spaceflights.dataStructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "flightID",
        "participantID",
        "startDate",
        "endDate",
        "isPaid"
})
public class Reservation {
    @JsonProperty("id")
    private int id;
    @JsonProperty("flightID")
    private int flightID;
    @JsonProperty("participantID")
    private int participantID;
    @JsonProperty("startDate")
    private String startDate;
    @JsonProperty("endDate")
    private String endDate;
    @JsonProperty("isPaid")
    private int isPaid;

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }
    @JsonProperty("flightID")
    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    @JsonProperty("participantID")
    public void setParticipantID(int participantID) {
        this.participantID = participantID;
    }

    @JsonProperty("startDate")
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonProperty("endDate")
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @JsonProperty("isPaid")
    public void setIsPaid(int isPaid) {
        this.isPaid = isPaid;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("flightID")
    public int getFlightID() {
        return flightID;
    }

    @JsonProperty("participantID")
    public int getParticipantID() {
        return participantID;
    }

    @JsonProperty("startDate")
    public String getStartDate() {
        return startDate;
    }

    @JsonProperty("endDate")
    public String getEndDate() {
        return endDate;
    }

    @JsonProperty("isPaid")
    public int getIsPaid() {
        return isPaid;
    }

    public Reservation(int id, int flightID, int participantID, String startDate, String endDate, int isPaid) {
        this.id = id;
        this.flightID = flightID;
        this.participantID = participantID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPaid = isPaid;
    }
    public Reservation(){}
}

