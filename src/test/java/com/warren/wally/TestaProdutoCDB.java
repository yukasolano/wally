package com.warren.wally;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.warren.wally.model.CalculadoraCDI;
import com.warren.wally.model.CalculadoraIPCA;
import com.warren.wally.model.CalculadoraPre;
import com.warren.wally.model.ProdutoCDB;
import com.warren.wally.model.calculadora.Calculadora;

public class TestaProdutoCDB {

	
	@Test
	public void testCalculoPre() {
		ProdutoCDB produto = new ProdutoCDB(LocalDate.of(2017,12,01), LocalDate.of(2022,11,07), 6000, 0.1221);
		produto.setCalc(new CalculadoraPre());
		LocalDate hoje = LocalDate.of(2019, 01, 18);
		produto.calculaAccrual(hoje);
		assertEquals(6681.10, produto.getValorPresente(), 0.01);
		assertEquals(11.35, produto.getRentabilidadeLiquida()*100, 0.01);
		assertEquals(10.09, produto.getTaxaAnualLiquida()*100, 0.01);
		assertEquals(0.8, produto.getTaxaMensalLiquida()*100, 0.01);
	}
	
	@Test
	public void testCalculoCDI() {
		ProdutoCDB produto = new ProdutoCDB(LocalDate.of(2016,06,24), LocalDate.of(2021,05,31), 10000, 1.22);
		produto.setCalc(new CalculadoraCDI());
		LocalDate hoje = LocalDate.of(2018, 12, 10);
		produto.calculaAccrual(hoje);
		assertEquals(12606.04, produto.getValorPresente(), 0.01);
		assertEquals(26.06, produto.getRentabilidadeLiquida()*100, 0.01);
		assertEquals(9.94, produto.getTaxaAnualLiquida()*100, 0.01);
		assertEquals(0.79, produto.getTaxaMensalLiquida()*100, 0.01);
	}
	
	@Test
	public void testCalculoIPCA() {
		ProdutoCDB produto = new ProdutoCDB(LocalDate.of(2018,10,23), LocalDate.of(2024,12,15), 1000, 0.07);
		produto.setCalc(new CalculadoraIPCA());
		LocalDate hoje = LocalDate.of(2018, 12, 10);
		produto.calculaAccrual(hoje);
		assertEquals(1008.03, produto.getValorPresente(), 0.01);
		assertEquals(0.80, produto.getRentabilidadeLiquida()*100, 0.01);
		assertEquals(6.50, produto.getTaxaAnualLiquida()*100, 0.01);
		assertEquals(0.53, produto.getTaxaMensalLiquida()*100, 0.01);
		
	}
	

}
