package com.spaceflights.dataStructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "surname",
        "gender",
        "country",
        "birthDate",
        "notes"
})

public class Participant {
    @JsonProperty("id")
    private  int participantID;
    @JsonProperty("name")
    private  String name;
    @JsonProperty("surname")
    private  String surname;
    @JsonProperty("gender")
    private  String gender;
    @JsonProperty("country")
    private  String country;
    @JsonProperty("birthDate")
    private  String birthDate;
    @JsonProperty("notes")
    private  String notes;

    @JsonProperty("id")
    public void setParticipantID(int participantID) {
        this.participantID = participantID;
    }
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty("surname")
    public void setSurname(String surname) {
        this.surname = surname;
    }
    @JsonProperty("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }
    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }
    @JsonProperty("birthDate")
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    @JsonProperty("notes")
    public void setNotes(String notes) {
        this.notes = notes;
    }


    public Participant(){}

    public Participant(int participantID, String name, String surname, String gender, String country, String birthDate, String notes) {
        this.participantID = participantID;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.country = country;
        this.birthDate = birthDate;
        this.notes = notes;
    }
    @JsonProperty("id")
    public int getParticipantID() {
        return participantID;
    }
    @JsonProperty("name")
    public String getName() {
        return name;
    }
    @JsonProperty("surname")
    public String getSurname() {
        return surname;
    }
    @JsonProperty("gender")
    public String getGender() {
        return gender;
    }
    @JsonProperty("country")
    public String getCountry() {
        return country;
    }
    @JsonProperty("birthDate")
    public String getBirthDate() {
        return birthDate;
    }
    @JsonProperty("notes")
    public String getNotes() {
        return notes;
    }

}
