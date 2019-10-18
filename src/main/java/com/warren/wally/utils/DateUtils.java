package com.warren.wally.utils;

import com.warren.wally.exception.InvalidDateFormatterException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utils for handle over Dates.
 */
public class DateUtils {

    private static final String DATE_PATTERN = "dd/MM/yyyy";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static LocalDate dateOf(String date) throws InvalidDateFormatterException {
        try {
            return LocalDate.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatterException(date, DATE_PATTERN, e);
        }
    }

}
