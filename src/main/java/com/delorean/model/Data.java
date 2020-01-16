package com.delorean.model;

import java.util.HashMap;

public class Data {

    private Integer Id;
    private Integer latestTimeStamp;
    private Integer previousTimeStamp;
    private HashMap<Integer, String> timeStampData;

    public Data(Integer id, Integer latestTimeStamp, Integer previousTimeStamp,
                HashMap<Integer, String> timeStampData) {
        Id = id;
        this.latestTimeStamp = latestTimeStamp;
        this.previousTimeStamp = previousTimeStamp;
        this.timeStampData = timeStampData;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getLatestTimeStamp() {
        return latestTimeStamp;
    }

    public void setLatestTimeStamp(Integer latestTimeStamp) {
        this.latestTimeStamp = latestTimeStamp;
    }

    public Integer getPreviousTimeStamp() {
        return previousTimeStamp;
    }

    public void setPreviousTimeStamp(Integer previousTimeStamp) {
        this.previousTimeStamp = previousTimeStamp;
    }

    public HashMap<Integer, String> getTimeStampData() {
        return timeStampData;
    }

    public void setTimeStampData(HashMap<Integer, String> timeStampData) {
        this.timeStampData = timeStampData;
    }
}
