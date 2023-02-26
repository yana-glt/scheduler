package com.solvd.scheduler.terminal.scanner;

import com.solvd.scheduler.exception.InputException;

import java.io.IOException;

public class ScannerUtils {

    public static void checkCorrectValue(int answer, int comparativeNumber1, int comparativeNumber2) throws InputException {
        boolean valid = answer >= comparativeNumber1 && answer <= comparativeNumber2;
        if (!(valid)) {
            throw new InputException(String.format("\nYou can only select integer numbers from %d to %d", comparativeNumber1, comparativeNumber2), new IOException(), answer);
        }
    }
}
