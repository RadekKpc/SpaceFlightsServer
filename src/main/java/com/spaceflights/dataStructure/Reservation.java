package com.spaceflights.dataStructure;

public class Reservation {
    private final int id;
    private final int flightID;
    private final int participantID;
    private final String startDate;
    private final String endDate;
    private final int isPaid;

    public int getId() {
        return id;
    }

    public int getFlightID() {
        return flightID;
    }

    public int getParticipantID() {
        return participantID;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

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
}

