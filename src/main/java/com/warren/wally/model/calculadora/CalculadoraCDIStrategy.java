package com.warren.wally.model.calculadora;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.warren.wally.model.BussinessDaysCalendar;
import com.warren.wally.model.DataValor;
import com.warren.wally.model.calculadora.repository.CdiEntity;
import com.warren.wally.model.calculadora.repository.CdiRepository;

@Component
public class CalculadoraCDIStrategy implements Calculadora{
	
	@Resource
	private CdiRepository cdiRepository;
	
	@Override
	public TipoRentabilidade getTipoRentabilidade() {
		return TipoRentabilidade.CDI;
	}

	@Override
	public double calculaVPBruto(double valorAplicado, double taxa, LocalDate dtAplicacao, LocalDate dtRef) {	
		List<DataValor> cdiFiltrado = filtraCDI(dtAplicacao, dtRef);
		return valorAplicado * getFatorAcumulado(taxa, cdiFiltrado);
	}


	private List<DataValor> filtraCDI(LocalDate dataInicio, LocalDate dataFim) {
		BussinessDaysCalendar bc = new BussinessDaysCalendar();
		LocalDate dtInicio = bc.getNextWorkDay(dataInicio.plusDays(1));
		List<DataValor> cdis = cdiRepository.findByDataBetween(dtInicio, dataFim).stream()
				.map(it -> new DataValor(it.getData(), it.getValor())).collect(Collectors.toList());
		completaDados(cdis, dataFim);
		return cdis;
		
	}

	private double getFatorAcumulado(double taxa, List<DataValor> cdiFiltrado) {
		double fatorAcumulado = 1.0;
		for (DataValor dt : cdiFiltrado) {
			fatorAcumulado *= Math.pow(1 + dt.getValor() / 100 * taxa, 1 / 252.0);
		}
		return fatorAcumulado;
	}

	public void completaDados(List<DataValor> cdi, LocalDate dataFim) {
		BussinessDaysCalendar bc = new BussinessDaysCalendar();
		LocalDate today = bc.getNextWorkDay(dataFim);
		LocalDate lastDate = cdi.get(cdi.size()-1).getData();
		double lastValue = cdi.get(cdi.size()-1).getValor();
		while(lastDate.isBefore(today)) {
			lastDate = bc.getNextWorkDay(lastDate.plusDays(1));
			cdi.add(new DataValor(lastDate, lastValue));
		}
	}
}