package com.delorean.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateInput {
    public static boolean validateId(Integer id) {
        Matcher mtchRrange = Pattern.compile("[0..2^31 - 1]").matcher("");
        return mtchRrange.reset(id.toString()).matches();
    }

    public static boolean validateTimeStamp(Integer timeStamp) {
        return timeStamp >= 0 && timeStamp <= Long.MAX_VALUE;
    }

    public  static boolean validateObservation(String observation) {
        Pattern space = Pattern.compile("\\s+");
        Matcher matcherSpace = space.matcher(observation);
        return !matcherSpace.find();
    }
}
