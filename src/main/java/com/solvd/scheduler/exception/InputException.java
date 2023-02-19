package com.solvd.scheduler.exception;

import static com.solvd.scheduler.Main.logger;

public class InputException extends Throwable {
    public InputException(String message, Throwable err) {
        super(message, err);
        logger.error("Exception: Invalid characters were entered in the field\n");
    }
}
