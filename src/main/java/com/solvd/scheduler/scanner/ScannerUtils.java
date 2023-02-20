package com.solvd.scheduler.scanner;

import com.solvd.scheduler.exception.InputException;

import java.io.IOException;

public class ScannerUtils {

    public static void checkCorrectValue(int answer, int comparativeNumber1, int comparativeNumber2) throws InputException {
        boolean valid = answer >= comparativeNumber1 && answer <= comparativeNumber2;
        if (!(valid)) {
            if (comparativeNumber1 == comparativeNumber2) {
                throw new InputException(String.format("\nYou can only select %d lessons per day", comparativeNumber1), new IOException(), answer);
            } else {
                throw new InputException(String.format("\nYou can only select integer numbers from %d to %d", comparativeNumber1, comparativeNumber2), new IOException(), answer);
            }
        }
    }
}
