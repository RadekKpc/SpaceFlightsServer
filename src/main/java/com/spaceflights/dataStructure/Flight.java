package com.spaceflights.dataStructure;

public class Flight {
    private final int id;
    private final String startData;
    private final String endData;
    private final int participantCapacity;
    private final float price;

    public Flight(int id,String startData,String endData,int participantCapacity,float price){
            this.id = id;
        this.endData = endData;
        this.startData = startData;
        this.price = price;
        this.participantCapacity = participantCapacity;
    }

    public String getStartData() {
        return startData;
    }

    public String getEndData() {
        return endData;
    }

    public int getParticipantCapacity() {
        return participantCapacity;
    }

    public float getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }
}

