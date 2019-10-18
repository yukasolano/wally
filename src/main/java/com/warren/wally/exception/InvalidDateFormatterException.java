package com.warren.wally.exception;

public class InvalidDateFormatterException extends RuntimeException {

    public InvalidDateFormatterException(String date, String pattern, Throwable cause) {
        super(String.format("A data informada %s, não corresponde ao padrão '%s'", date, pattern), cause);
    }
}
