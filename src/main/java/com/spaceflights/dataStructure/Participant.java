package com.spaceflights.dataStructure;

public class Participant {
    private final int participantID;
    private final String name;
    private final String surname;
    private final String gender;
    private final String country;
    private final String birthDate;
    private final String notes;

    public Participant(int participantID, String name, String surname, String gender, String country, String birthDate, String notes) {
        this.participantID = participantID;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.country = country;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public int getParticipantID() {
        return participantID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getNotes() {
        return notes;
    }
}
