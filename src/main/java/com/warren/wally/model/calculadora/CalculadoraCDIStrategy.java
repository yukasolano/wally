package com.warren.wally.model.calculadora;

import com.warren.wally.model.dadosmercado.repository.CdiRepository;
import com.warren.wally.model.dadosmercado.DMcdiActor;
import com.warren.wally.utils.BussinessDaysCalendar;
import com.warren.wally.utils.DataValor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Component
public class CalculadoraCDIStrategy implements Calculadora {

	@Resource
	private CdiRepository cdiRepository;

	@Resource
	private BussinessDaysCalendar bc;

	@Resource
	private DMcdiActor cdiActor;

	@Override
	public TipoRentabilidade getTipoRentabilidade() {
		return TipoRentabilidade.CDI;
	}

	@Override
	public double calculaVPBruto(double valorAplicado, double taxa, LocalDate dtAplicacao, LocalDate dtRef) {
		return valorAplicado * getFatorAcumulado(taxa, cdiActor.filtraCDI(dtAplicacao, dtRef));
	}

	private double getFatorAcumulado(double taxa, List<DataValor> cdiFiltrado) {
		double fatorAcumulado = 1.0;
		for (DataValor dt : cdiFiltrado) {
			fatorAcumulado *= Math.pow(1 + dt.getValor() / 100 * taxa, 1 / 252.0);
		}
		return fatorAcumulado;
	}


}