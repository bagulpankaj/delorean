package com.delorean.model;

public class ResponseData {

    private String status;
    private String resultString;

    public ResponseData(String status, String resultString) {
        this.status = status;
        this.resultString = resultString;
    }

    public String getStatus() {
        return status;
    }

    public String getResultString() {
        return resultString;
    }
}
