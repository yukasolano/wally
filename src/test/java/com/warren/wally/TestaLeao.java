package com.warren.wally;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.warren.wally.model.Leao;

public class TestaLeao {

	@Test
	public void test() {
		Leao leao = new Leao();
		assertEquals(22.5, leao.getIR(100, LocalDate.of(2018, 01, 01), LocalDate.of(2018, 02, 01)), 0.01);
		assertEquals(20.0, leao.getIR(100, LocalDate.of(2018, 01, 01), LocalDate.of(2018, 07, 01)), 0.01);
		assertEquals(17.5, leao.getIR(100, LocalDate.of(2018, 01, 01), LocalDate.of(2019, 02, 01)), 0.01);
		assertEquals(15.0, leao.getIR(100, LocalDate.of(2018, 01, 01), LocalDate.of(2020, 01, 01)), 0.01);
	}

}
