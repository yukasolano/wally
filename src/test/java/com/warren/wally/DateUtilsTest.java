package com.warren.wally;

import com.warren.wally.exception.InvalidDateFormatterException;
import com.warren.wally.utils.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class DateUtilsTest {

    @Test
    public void validDate(){
        LocalDate date = DateUtils.dateOf("21/03/2019");
        LocalDate expectedDate = LocalDate.of(2019, 3, 21);

        Assert.assertEquals(expectedDate, date);
    }

    @Test(expected = InvalidDateFormatterException.class)
    public void invalidDateFormatterAtYear() {
        DateUtils.dateOf("09/09/19");
    }

    @Test(expected = InvalidDateFormatterException.class)
    public void invalidDateFormatterAtMonth() {
        DateUtils.dateOf("19/9/2019");
    }

    @Test(expected = InvalidDateFormatterException.class)
    public void invalidDateFormatterAtDay() {
        DateUtils.dateOf("9/12/2019");
    }
}
