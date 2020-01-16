package com.delorean.model;

public class TimeStampData {

    private Integer timeStamp;
    private String observation;

    public TimeStampData(Integer timeStamp, String observation) {
        this.timeStamp = timeStamp;
        this.observation = observation;
    }

    public Integer getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Integer timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
