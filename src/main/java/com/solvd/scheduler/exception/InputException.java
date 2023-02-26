package com.solvd.scheduler.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class InputException extends Throwable {
    public final static Logger LOGGER = LogManager.getLogger(InputException.class);

    public InputException(String message, Throwable err, int answer) {
        super(message, err);
        LOGGER.error(String.format("Exception: The entered data \"%d\" does not match the condition\"", answer));
    }
}
