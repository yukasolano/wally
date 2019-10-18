package com.warren.wally;

import com.warren.wally.model.BussinessDaysCalendar;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.assertEquals;

public class TestBussinessCalendarDays {

    private LocalDate start;
    private LocalDate end;
    private BussinessDaysCalendar bc;

    @Before
    public void inicializa() {
        bc = new BussinessDaysCalendar();
        start = dateOf("01/12/2017");
        end = dateOf("07/11/2022");
    }

    @Test
    public void testDC() {
        assertEquals(1802, bc.getDc(start, end));
    }

    @Test
    public void testDU() {
        assertEquals(1237, bc.getDu(start, end));
    }

}
