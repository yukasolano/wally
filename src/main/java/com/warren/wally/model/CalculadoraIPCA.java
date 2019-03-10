package com.warren.wally.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalculadoraIPCA implements Calculadora {

	List<DataValor> ipcaAcumulado = new ArrayList<>();
	BussinessDaysCalendar bc = new BussinessDaysCalendar();

	@Override
	public double calculaVPBruto(double valorAplicado, double taxa, LocalDate dtAplicacao, LocalDate dtRef) {
		recuperaDados();

		double fatorPre = getFatorPre(dtAplicacao, dtRef, taxa);
		double ipcaAcum = getFatorAcumulado(dtAplicacao, dtRef);
		return valorAplicado * fatorPre * ipcaAcum;
	}

	@Override
	public TipoRentabilidade getTipoRentabilidade() {
		return TipoRentabilidade.IPCA;
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
		System.out.println("du fim " + duFim);
		System.out.println("valor mesFimMais1 " + find(mesFimMais1));
		System.out.println("valor mesFim " + find(mesFim));
		System.out.println(fteInicio + " " + fteMeio + " " + fteFim);
		return fteInicio * fteMeio * fteFim;

	}

	private double find(LocalDate data) {
		List<DataValor> ipcaAcumuladoFiltrado = ipcaAcumulado.stream().filter(dt -> dt.getData().isEqual(data))
				.collect(Collectors.toList());
		if (ipcaAcumuladoFiltrado.size() == 0) {
			DataValor ultimo = ipcaAcumulado.get(ipcaAcumulado.size() - 1);
			DataValor penultimo = ipcaAcumulado.get(ipcaAcumulado.size() - 2);
			if (data.isBefore(ultimo.getData())) {
				throw new RuntimeException("Data de IPCA não tratada - " + data);
			}
			long qtdMeses = ChronoUnit.MONTHS.between(ultimo.getData(), data);
			double ipcaAcum = ultimo.getValor() * Math.pow(ultimo.getValor() / penultimo.getValor(), qtdMeses);
			return ipcaAcum;
		}
		return ipcaAcumuladoFiltrado.get(0).getValor();
	}

	public void recuperaDados() {
		ipcaAcumulado.add(new DataValor("01/01/2019", 1318.700));
		ipcaAcumulado.add(new DataValor("01/12/2018", 1314.494));
		ipcaAcumulado.add(new DataValor("01/11/2018", 1312.525));
		ipcaAcumulado.add(new DataValor("01/10/2018", 1315.287));
		ipcaAcumulado.add(new DataValor("01/09/2018", 1309.395));
		ipcaAcumulado.add(new DataValor("01/08/2018", 1303.140));
		ipcaAcumulado.add(new DataValor("01/07/2018", 1304.314));
		ipcaAcumulado.add(new DataValor("01/06/2018", 1300.024));
		ipcaAcumulado.add(new DataValor("01/05/2018", 1283.847));
		ipcaAcumulado.add(new DataValor("01/04/2018", 1278.732));
		ipcaAcumulado.add(new DataValor("01/03/2018", 1275.925));
		ipcaAcumulado.add(new DataValor("01/02/2018", 1274.778));
		ipcaAcumulado.add(new DataValor("01/01/2018", 1270.712));
		ipcaAcumulado.add(new DataValor("01/12/2017", 1267.037));
		ipcaAcumulado.add(new DataValor("01/11/2017", 1261.487));
		ipcaAcumulado.add(new DataValor("01/10/2017", 1257.965));
		ipcaAcumulado.add(new DataValor("01/09/2017", 1252.703));
		ipcaAcumulado.add(new DataValor("01/08/2017", 1250.702));
		ipcaAcumulado.add(new DataValor("01/07/2017", 1248.330));
		ipcaAcumulado.add(new DataValor("01/06/2017", 1245.341));
		ipcaAcumulado.add(new DataValor("01/05/2017", 1248.212));
		ipcaAcumulado.add(new DataValor("01/04/2017", 1244.355));
		ipcaAcumulado.add(new DataValor("01/03/2017", 1242.615));
		ipcaAcumulado.add(new DataValor("01/02/2017", 1239.516));
		ipcaAcumulado.add(new DataValor("01/01/2017", 1235.439));
		ipcaAcumulado.add(new DataValor("01/12/2016", 1230.763));
		ipcaAcumulado.add(new DataValor("01/11/2016", 1227.081));
		ipcaAcumulado.add(new DataValor("01/10/2016", 1224.877));
		ipcaAcumulado.add(new DataValor("01/09/2016", 1221.700));
		ipcaAcumulado.add(new DataValor("01/08/2016", 1220.724));
		ipcaAcumulado.add(new DataValor("01/07/2016", 1215.376));
		ipcaAcumulado.add(new DataValor("01/06/2016", 1209.089));
		ipcaAcumulado.add(new DataValor("01/05/2016", 1204.872));
		ipcaAcumulado.add(new DataValor("01/04/2016", 1195.546));
		ipcaAcumulado.add(new DataValor("01/03/2016", 1188.298));
		ipcaAcumulado.add(new DataValor("01/02/2016", 1183.210));
		ipcaAcumulado.add(new DataValor("01/01/2016", 1172.656));
		ipcaAcumulado.add(new DataValor("01/12/2015", 1157.950));
		ipcaAcumulado.add(new DataValor("01/11/2015", 1146.939));
		ipcaAcumulado.add(new DataValor("01/10/2015", 1135.471));
		ipcaAcumulado.add(new DataValor("01/09/2015", 1126.236));
		ipcaAcumulado.add(new DataValor("01/08/2015", 1120.187));
		ipcaAcumulado.add(new DataValor("01/07/2015", 1117.728));
		ipcaAcumulado.add(new DataValor("01/06/2015", 1110.841));
		ipcaAcumulado.add(new DataValor("01/05/2015", 1102.134));
		ipcaAcumulado.add(new DataValor("01/04/2015", 1094.038));
		ipcaAcumulado.add(new DataValor("01/03/2015", 1086.325));
		ipcaAcumulado.add(new DataValor("01/02/2015", 1072.172));
		ipcaAcumulado.add(new DataValor("01/01/2015", 1059.250));

	}

}
