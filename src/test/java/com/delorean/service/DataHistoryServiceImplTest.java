package com.delorean.service;

import com.delorean.model.ResponseData;
import com.delorean.repository.HistoryRepository;
import com.delorean.repository.InMemoryHistoryRepository;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DataHistoryServiceImplTest {

    @Test
    public void createTest() {
        DataHistoryService dataHistoryService = getDataHistoryService();
        ResponseData responseData = dataHistoryService.create(0, 100, "1.5");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));
    }

    @Test
    public void createValidateDataTest() {
        DataHistoryService dataHistoryService = getDataHistoryService();
        ResponseData responseData = dataHistoryService.create(-1, 100, "1.5");
        assertThat(responseData.getStatus(), is("ERR"));
        assertThat(responseData.getResultString(), is("Invalid identifier '-1'"));

        responseData = dataHistoryService.create(1, -100, "1.5");
        assertThat(responseData.getStatus(), is("ERR"));
        assertThat(responseData.getResultString(), is("Invalid timeStamp '-100'"));

        responseData = dataHistoryService.create(1, 100, "1.5 23");
        assertThat(responseData.getStatus(), is("ERR"));
        assertThat(responseData.getResultString(), is("Invalid observation '1.5 23'"));
    }

    private DataHistoryService getDataHistoryService() {
        HistoryRepository historyRepository = new InMemoryHistoryRepository();
        return new DataHistoryServiceImpl(historyRepository);
    }

    @Test
    public void updateTest() {
        DataHistoryService dataHistoryService = getDataHistoryService();
        ResponseData responseData = dataHistoryService.create(0, 100, "1.5");
        dataHistoryService.update(0, 110, "2.5");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));
    }

    @Test
    public void deleteTest() {
        DataHistoryService dataHistoryService = getDataHistoryService();
        ResponseData responseData = dataHistoryService.create(0, 100, "1.5");
        dataHistoryService.delete(0, 110);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));
    }

    @Test
    public void getTest() {

        DataHistoryService dataHistoryService = getDataHistoryService();
        ResponseData responseData = dataHistoryService.create(0, 100, "1.5");
        dataHistoryService.get(0, 110);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));
    }

    @Test
    public void latestTest() {
        DataHistoryService dataHistoryService = getDataHistoryService();
        ResponseData responseData = dataHistoryService.create(0, 100, "1.5");
        dataHistoryService.latest(0);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));
    }
}