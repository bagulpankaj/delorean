package com.delorean.repository;

import com.delorean.model.ResponseData;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class InMemoryHistoryRepositoryTest {

    @Test
    public void createTest() {
        HistoryRepository  historyRepository =  getHistoryRepository();
        ResponseData responseData =  historyRepository.create(0, 100, "1.5");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));
    }

    @Test
    public void createAlreadyExistDataTest() {
        HistoryRepository  historyRepository =  getHistoryRepository();
        ResponseData responseData =  historyRepository.create(0, 100, "1.5");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));
        responseData =  historyRepository.create(0, 110, "2.5");
        assertThat(responseData.getStatus(), is("ERR"));
        assertThat(responseData.getResultString(), is("A history already exists for identifier '0'"));
    }

    @Test
    public void updateTest() {
        HistoryRepository  historyRepository =  getHistoryRepository();
         historyRepository.create(0, 100, "1.5");
        ResponseData responseData =  historyRepository.update(0, 105, "1.6");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));

    }

    @Test
    public void updateNoHistoryExistTest() {
        HistoryRepository  historyRepository =  getHistoryRepository();
        historyRepository.create(0, 100, "1.5");
        ResponseData responseData =  historyRepository.update(0, 105, "1.6");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));
        responseData = historyRepository.update(1, 100, "2.6");
        assertThat(responseData.getStatus(), is("ERR"));
        assertThat(responseData.getResultString(), is("No history exists for '1'"));

    }

    @Test
    public void deleteTest() {
        HistoryRepository  historyRepository =  getHistoryRepository();
         historyRepository.create(0, 100, "1.5");
         historyRepository.update(0, 105, "1.6");
        ResponseData responseData =  historyRepository.delete(0, 100);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));

         historyRepository.create(0, 100, "1.5");
         historyRepository.update(0, 105, "1.6");
        responseData =  historyRepository.delete(0, 105);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.6"));
    }

    @Test
    public void deleteNotExistDataTest() {
        HistoryRepository  historyRepository =  getHistoryRepository();
        historyRepository.create(0, 100, "1.5");
        historyRepository.update(0, 105, "1.6");
        ResponseData responseData =  historyRepository.delete(1, 100);
        assertThat(responseData.getStatus(), is("ERR"));
        assertThat(responseData.getResultString(), is("No history exists for identifier '1'"));

        responseData =  historyRepository.delete(1, null);
        assertThat(responseData.getStatus(), is("ERR"));
        assertThat(responseData.getResultString(), is("No history exists for identifier '1'"));
    }


    @Test
    public void getTest() {
        HistoryRepository  historyRepository =  getHistoryRepository();
         historyRepository.create(0, 100, "1.5");
        ResponseData responseData =  historyRepository.get(0, 100);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));

        responseData =  historyRepository.get(0, 110);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));
    }

    @Test
    public void getHistoryNotExistTest() {
        HistoryRepository  historyRepository =  getHistoryRepository();
        historyRepository.create(0, 100, "1.5");
        ResponseData responseData =  historyRepository.get(0, 100);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));

        responseData =  historyRepository.get(1, 110);
        assertThat(responseData.getStatus(), is("ERR"));
        assertThat(responseData.getResultString(), is("No history exists for identifier '1'"));

        historyRepository.delete(0, 100);
        responseData =  historyRepository.get(0, 100);
        assertThat(responseData.getStatus(), is("ERR"));
        assertThat(responseData.getResultString(), is("No history exists for identifier '0'"));

    }

    @Test
    public void latestTest() {
        HistoryRepository  historyRepository =  getHistoryRepository();
         historyRepository.create(0, 100, "1.5");
         historyRepository.update(0, 105, "1.6");
        ResponseData responseData =  historyRepository.latest(0);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("105 1.6"));
    }

    @Test
    public void latestHistoryNotExistTest() {
        HistoryRepository  historyRepository =  getHistoryRepository();
        historyRepository.create(0, 100, "1.5");
        historyRepository.update(0, 105, "1.6");
        ResponseData responseData =  historyRepository.latest(1);
        assertThat(responseData.getStatus(), is("ERR"));
        assertThat(responseData.getResultString(), is("No history exists for identifier '1'"));
    }

    @Test
    public void testFlow() {
        HistoryRepository  historyRepository =  getHistoryRepository();
//        CREATE 0 100 1.5
//        OK 1.5
        ResponseData responseData =  historyRepository.create(0, 100, "1.5");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));
//        UPDATE 0 105 1.6
//        OK 1.5
        responseData =  historyRepository.update(0, 105, "1.6");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));
//        GET 0 100
//        OK 1.5
        responseData =  historyRepository.get(0, 100);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.5"));
//        GET 0 110
//        OK 1.6
        responseData =  historyRepository.get(0, 110);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("1.6"));

//        LATEST 0
//        OK 105 1.6
        responseData =  historyRepository.latest(0);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("105 1.6"));
//        LATEST 1
//        ERR No history exists for identifier '1'
        responseData =  historyRepository.latest(1);
        assertThat(responseData.getStatus(), is("ERR"));
        assertThat(responseData.getResultString(), is("No history exists for identifier '1'"));

//        CREATE 1 110 2.5
//        OK 2.5
        responseData =  historyRepository.create(1, 110, "2.5");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("2.5"));

//        CREATE 1 115 2.4
//        ERR A history already exists for identifier '1'
        responseData =  historyRepository.create(1, 115, "2.4");
        assertThat(responseData.getStatus(), is("ERR"));
        assertThat(responseData.getResultString(), is("A history already exists for identifier '1'"));

//        UPDATE 1 115 2.4
//        OK 2.5
        responseData =  historyRepository.update(1, 115, "2.4");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("2.5"));

//        UPDATE 1 120 2.3
//        OK 2.4
        responseData =  historyRepository.update(1, 120, "2.3");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("2.4"));

//        UPDATE 1 125 2.2
//        OK 2.3
        responseData =  historyRepository.update(1, 125, "2.2");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("2.3"));

//        LATEST 1
//        OK 125 2.2
        responseData =  historyRepository.latest(1);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("125 2.2"));

//        GET 1 120
//        OK 2.3
        responseData =  historyRepository.get(1, 120);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("2.3"));

//        UPDATE 1 120 2.35
//        OK 2.3
        responseData =  historyRepository.update(1,120, "2.35");
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("2.3"));

//        GET 1 122
//        OK 2.35
        responseData =  historyRepository.get(1, 122);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("2.35"));

//        DELETE 1 122
//        OK 2.35
        responseData =  historyRepository.delete(1, 122);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("2.35"));


//        GET 1 125
//        OK 2.35
        responseData =  historyRepository.get(1, 125);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("2.35"));

//        DELETE 1
//        OK 2.35
        responseData =  historyRepository.delete(1, null);
        assertThat(responseData.getStatus(), is("OK"));
        assertThat(responseData.getResultString(), is("2.35"));
    }

    private HistoryRepository  getHistoryRepository() {
        return new InMemoryHistoryRepository();
    }
}