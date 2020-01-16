package com.delorean.service;

import com.delorean.model.ResponseData;
import com.delorean.repository.HistoryRepository;

public class DataHistoryServiceImpl implements DataHistoryService {

    public HistoryRepository historyRepository;

    public DataHistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Override
    public ResponseData create(Integer id, Integer timeStamp, String observation) {
        if (!ValidateInput.validateId(id)) {
            return createErrorResponse("Invalid identifier '"+ id +"'");
        }
        if (!ValidateInput.validateTimeStamp(timeStamp)) {
            return createErrorResponse("Invalid timeStamp '"+ timeStamp +"'");
        }
        if (!ValidateInput.validateObservation(observation)) {
            return createErrorResponse("Invalid observation '"+ observation +"'");
        }
        return historyRepository.create(id, timeStamp, observation);
    }

    @Override
    public ResponseData update(Integer id, Integer timeStamp, String observation) {
        if (!ValidateInput.validateId(id)) {
            return createErrorResponse("Invalid identifier '"+ id +"'");
        }
        if (!ValidateInput.validateTimeStamp(timeStamp)) {
            return createErrorResponse("Invalid timeStamp '"+ timeStamp +"'");
        }
        if (!ValidateInput.validateObservation(observation)) {
            return createErrorResponse("Invalid observation '"+ observation +"'");
        }
        return historyRepository.update(id,timeStamp,observation);
    }

    @Override
    public ResponseData delete(Integer id, Integer timeStamp) {
        if (!ValidateInput.validateId(id)) {
            return createErrorResponse("Invalid identifier '"+ id +"'");
        }
        if (timeStamp!= null && !ValidateInput.validateTimeStamp(timeStamp)) {
            return createErrorResponse("Invalid timeStamp '"+ timeStamp +"'");
        }
        return historyRepository.delete(id, timeStamp);
    }

    @Override
    public ResponseData get(Integer id, Integer timeStamp) {
        if (!ValidateInput.validateId(id)) {
            return createErrorResponse("Invalid identifier '"+ id +"'");
        }
        if (!ValidateInput.validateTimeStamp(timeStamp)) {
            return createErrorResponse("Invalid timeStamp '"+ timeStamp +"'");
        }
        return historyRepository.get(id, timeStamp);
    }

    @Override
    public ResponseData latest(Integer id) {
        if (!ValidateInput.validateId(id)) {
            return createErrorResponse("Invalid identifier '"+ id +"'");
        }
        return historyRepository.latest(id);
    }

    private ResponseData createErrorResponse(String errorString) {
        return  new ResponseData("ERR", errorString);
    }
}
