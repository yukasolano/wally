package com.warren.wally.model.calculadora;

import com.warren.wally.model.calculadora.repository.IpcaRepository;
import com.warren.wally.model.dadosmercado.DMipcaActor;
import com.warren.wally.utils.BussinessDaysCalendar;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

@Component
public class CalculadoraIPCAStrategy implements Calculadora {

	@Resource
	private BussinessDaysCalendar bc;

	@Resource
	private IpcaRepository ipcaRepository;

	@Resource
	private DMipcaActor ipcaActor;

	@Override
	public TipoRentabilidade getTipoRentabilidade() {
		return TipoRentabilidade.IPCA;
	}

	@Override
	public double calculaVPBruto(double valorAplicado, double taxa, LocalDate dtAplicacao, LocalDate dtRef) {
		double fatorPre = getFatorPre(dtAplicacao, dtRef, taxa);
		double ipcaAcum = getFatorAcumulado(dtAplicacao, dtRef);
		return valorAplicado * fatorPre * ipcaAcum;
	}

	private double getFatorPre(LocalDate dtInicio, LocalDate dtFim, double taxa) {
		long du = bc.getDu(dtInicio, dtFim);
		if (du < 0)
			System.out.println("du é menor que zero");
		return Math.pow(1 + taxa, du / 252.0);
	}

	private double getFatorAcumulado(LocalDate dtInicio, LocalDate dtFim) {
		if (dtInicio.isAfter(dtFim))
			System.out.println("Data início é maior que data fim");

		LocalDate mesInicio = dtInicio.withDayOfMonth(1);
		LocalDate mesInicioMais1 = mesInicio.plusMonths(1);
		LocalDate mesFim = dtFim.withDayOfMonth(1);
		LocalDate mesFimMais1 = mesFim.plusMonths(1);

		if (mesInicio.isEqual(mesFim)) {
			long du = bc.getDu(dtInicio, dtFim);
			return Math.pow(ipcaActor.find(mesInicioMais1) / ipcaActor.find(mesInicio), du / 21.0);
		}
		long duInicio = bc.getDu(dtInicio, mesInicioMais1);
		long duFim = bc.getDu(mesFim, dtFim);
		double fteInicio = Math.pow(ipcaActor.find(mesInicioMais1) / ipcaActor.find(mesInicio), duInicio / 21.0);
		double fteMeio = ipcaActor.find(mesFim) / ipcaActor.find(mesInicioMais1);
		double fteFim = Math.pow(ipcaActor.find(mesFimMais1) / ipcaActor.find(mesFim), duFim / 21.0);
		return fteInicio * fteMeio * fteFim;

	}


}
