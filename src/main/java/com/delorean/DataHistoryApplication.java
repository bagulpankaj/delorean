package com.delorean;

import com.delorean.model.ResponseData;
import com.delorean.repository.HistoryRepository;
import com.delorean.repository.InMemoryHistoryRepository;
import com.delorean.service.DataHistoryService;
import com.delorean.service.DataHistoryServiceImpl;

import java.util.Scanner;

public class DataHistoryApplication {


    public static void main(String[] args) {
        HistoryRepository historyRepository = new InMemoryHistoryRepository();
        DataHistoryService dataHistoryService = new DataHistoryServiceImpl(historyRepository);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a command or QUIT to terminate:");
        boolean quit = false;

        while (!quit) {

            if (scanner.hasNext()) {

                String[] userInput = scanner.nextLine().split("\\s+");
                ResponseData responseData = null;
                switch (userInput[0].toUpperCase()) {

                    case "CREATE":
                        responseData = dataHistoryService.
                                create(Integer.valueOf(userInput[1]), Integer.valueOf(userInput[2]), userInput[3]);
                        printResponse(responseData);
                        break;
                    case "UPDATE":
                        responseData = dataHistoryService.update(Integer.valueOf(userInput[1]), Integer.valueOf(userInput[2]), userInput[3]);
                        printResponse(responseData);
                        break;
                    case "DELETE":
                        responseData = dataHistoryService.delete(Integer.valueOf(userInput[1]), userInput.length == 3? Integer.valueOf(userInput[2]) : null);
                        printResponse(responseData);
                        break;
                    case "GET":
                        responseData = dataHistoryService.
                                get(Integer.valueOf(userInput[1]), Integer.valueOf(userInput[2]));
                        printResponse(responseData);
                        break;
                    case "LATEST":
                        responseData = dataHistoryService.
                                latest(Integer.valueOf(userInput[1]));
                        printResponse(responseData);
                        break;
                    case "QUIT":
                        quit = true;
                        break;
                    default:
                        System.out.println("Please enter a valid command:");
                }

            }
        }
    }

    private static void printResponse(ResponseData responseData) {
        System.out.println(responseData.getStatus() + " " + responseData.getResultString());
    }
}

