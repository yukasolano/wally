package com.warren.wally;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.warren.wally.model.BussinessDaysCalendar;

public class TestBussinessCalendarDays {

	private LocalDate start;
	private LocalDate end;
	private BussinessDaysCalendar bc;
	
	@Before
	public void inicializa() {
		bc = new BussinessDaysCalendar();
		start = LocalDate.of(2017,12,01);
		end = LocalDate.of(2022,11,07);
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
