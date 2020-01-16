package com.delorean.repository;

import com.delorean.model.ResponseData;

public interface HistoryRepository {

    public ResponseData create(Integer id, Integer timeStamp, String observation);

    public ResponseData update(Integer id, Integer timeStamp, String observation);

    public ResponseData delete(Integer id, Integer timeStamp);

    public ResponseData get(Integer id, Integer timeStamp);

    public ResponseData latest(Integer id);
}
