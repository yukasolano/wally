package com.warren.wally.grafico;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GraficoMultiDados {

	public String[] labels;
	public String[] series;
	public double[][] data;

}