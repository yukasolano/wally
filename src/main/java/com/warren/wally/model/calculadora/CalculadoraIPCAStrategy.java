package com.warren.wally.model.calculadora;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.warren.wally.model.calculadora.repository.IpcaEntity;
import com.warren.wally.model.calculadora.repository.IpcaRepository;
import com.warren.wally.utils.BussinessDaysCalendar;

@Component
public class CalculadoraIPCAStrategy implements Calculadora {

	@Resource
	private BussinessDaysCalendar bc;

	@Resource
	private IpcaRepository ipcaRepository;

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
			return Math.pow(find(mesInicioMais1) / find(mesInicio), du / 21.0);
		}
		long duInicio = bc.getDu(dtInicio, mesInicioMais1);
		long duFim = bc.getDu(mesFim, dtFim);
		double fteInicio = Math.pow(find(mesInicioMais1) / find(mesInicio), duInicio / 21.0);
		double fteMeio = find(mesFim) / find(mesInicioMais1);
		double fteFim = Math.pow(find(mesFimMais1) / find(mesFim), duFim / 21.0);
		return fteInicio * fteMeio * fteFim;

	}

	public double find(LocalDate dataRef) {
		List<IpcaEntity> ipcaAcum = ipcaRepository.findByData(dataRef);
		if (ipcaAcum != null && ipcaAcum.size() > 0) {
			return ipcaAcum.get(0).getValorAcum();
		}

		LocalDate nextMonth = dataRef.withDayOfMonth(1).plusMonths(1);

		List<IpcaEntity> ipca = ipcaRepository.findFirstByOrderByDataDesc();
		LocalDate lastDate = ipca.get(0).getData();
		double lastValue = ipca.get(0).getValor();
		double lastAcum = ipca.get(0).getValorAcum();
		while (lastDate.isBefore(nextMonth)) {
			lastDate = lastDate.plusMonths(1);
			lastAcum = lastAcum * (1 + lastValue / 100);
			IpcaEntity entity = new IpcaEntity();
			entity.setData(lastDate);
			entity.setValorAcum(lastValue);
			ipcaAcum.add(entity);
		}

		Optional<IpcaEntity> ipcaAcumuladoFiltrado = ipcaAcum.stream().filter(dt -> dt.getData().isEqual(dataRef))
				.findFirst();
		if (ipcaAcumuladoFiltrado.isPresent()) {
			return ipcaAcumuladoFiltrado.get().getValor();
		}
		throw new RuntimeException("Data de IPCA não tratada - " + dataRef);
	}

}
