package com.delorean.repository;

import com.delorean.model.Data;
import com.delorean.model.ResponseData;

import java.util.HashMap;

public class InMemoryHistoryRepository implements HistoryRepository{

    public HashMap<Integer, Data> inMemoryHistory = new HashMap();

    public ResponseData create(Integer id, Integer timeStamp, String observation) {
        return createData(id, timeStamp, observation);
    }

    private ResponseData createData(Integer id, Integer timeStamp, String observation) {
        if (inMemoryHistory.get(id) == null) {
            Data data = getData(id, timeStamp, observation);
            inMemoryHistory.put(id, data);
            return createResponse(observation);
        }
        return createErrorResponse("A history already exists for identifier " + "'"+ id +"'");
    }

    private Data getData(Integer id, Integer timeStamp, String observation) {
        HashMap<Integer, String> history = new HashMap();
        history.put(timeStamp, observation);
        return new Data(id, timeStamp, timeStamp, history);
    }

    public ResponseData update(Integer id, Integer timeStamp, String observation) {
        Data data = inMemoryHistory.get(id);
        if (data == null) {
            return createErrorResponse("No history exists for '" + id + "'");
        }
        String previousObservation = data.getTimeStampData().containsKey(timeStamp)? data.getTimeStampData().get(timeStamp) : data.getTimeStampData().get(data.getPreviousTimeStamp());

        data.setLatestTimeStamp(timeStamp);
        data.setPreviousTimeStamp(timeStamp);
        data.getTimeStampData().put(timeStamp, observation);

        return createResponse(previousObservation);
    }

    public ResponseData delete(Integer id, Integer timeStamp) {
        Data data = inMemoryHistory.get(id);
        if (data == null || data.getTimeStampData() == null || data.getTimeStampData().isEmpty()) {
            return createErrorResponse("No history exists for identifier '"+ id +"'");
        }

        String latestObservation = timeStamp!= null && data.getTimeStampData().containsKey(timeStamp)? data.getTimeStampData().get(timeStamp)
                                                                                        :data.getTimeStampData().get(data.getLatestTimeStamp());

        if (timeStamp == null) {
            data.setPreviousTimeStamp(null);
            data.setLatestTimeStamp(null);
            data.setTimeStampData(null);
        } else {
            data.getTimeStampData().entrySet().removeIf(timeStampHistory -> timeStampHistory.getKey() >= timeStamp);
        }
        return createResponse(latestObservation);
    }

    public ResponseData get(Integer id, Integer timeStamp) {
        Data data = inMemoryHistory.get(id);
        if (data == null || data.getTimeStampData() == null || data.getTimeStampData().isEmpty()) {
            return createErrorResponse("No history exists for identifier '"+ id +"'");
        }
        if (data.getTimeStampData().get(timeStamp) == null) {
            return createResponse(data.getTimeStampData().get(data.getLatestTimeStamp()));
        }
        if (timeStamp > data.getLatestTimeStamp()) {
            return createResponse(data.getTimeStampData().get(data.getLatestTimeStamp()));
        }
        return createResponse(data.getTimeStampData().get(timeStamp));
    }

    public ResponseData latest(Integer id) {
        Data data = inMemoryHistory.get(id);
        if (data == null || data.getTimeStampData() == null || data.getTimeStampData().isEmpty()) {
            return createErrorResponse("No history exists for identifier '"+ id +"'");
        }
        return createResponse(data.getLatestTimeStamp() + " "
                + data.getTimeStampData().get(data.getLatestTimeStamp()));
    }

    private ResponseData createResponse(String response) {
        return new ResponseData("OK", response);
    }

    private ResponseData createErrorResponse(String errorString) {
        return  new ResponseData("ERR", errorString);
    }
}
