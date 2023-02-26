package com.solvd.scheduler.exception;

import static com.solvd.scheduler.Main.logger;

public class InputException extends Throwable {
    public InputException(String message, Throwable err, int answer) {
        super(message, err);
        logger.error(String.format("Exception: The entered data \"%d\" does not match the condition\"", answer));
    }
}
