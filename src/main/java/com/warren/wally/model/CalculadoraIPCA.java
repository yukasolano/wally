package com.warren.wally.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CalculadoraIPCA implements Calculadora {

	List<DataValor> ipcaAcumulado = new ArrayList<>();
	List<DataValor> ipca = new ArrayList<>();
	BussinessDaysCalendar bc = new BussinessDaysCalendar();

	@Override
	public double calculaVPBruto(double valorAplicado, double taxa, LocalDate dtAplicacao, LocalDate dtRef) {
		recuperaDados();
		criaDados();
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
		return fteInicio * fteMeio * fteFim;

	}

	private double find(LocalDate data) {
		Optional<DataValor> ipcaAcumuladoFiltrado = ipcaAcumulado.stream().filter(dt -> dt.getData().isEqual(data))
				.findFirst();
		if (ipcaAcumuladoFiltrado.isPresent()) {
			return ipcaAcumuladoFiltrado.get().getValor();
		}
		throw new RuntimeException("Data de IPCA não tratada - " + data);
	}

	public void criaDados() {
		LocalDate nextMonth = LocalDate.now().withDayOfMonth(1).plusMonths(1);

		LocalDate lastDate = ipca.get(ipca.size() - 1).getData();
		double lastValue = ipca.get(ipca.size() - 1).getValor();
		double lastAcum = ipcaAcumulado.get(ipcaAcumulado.size() - 1).getValor();
		while (lastDate.isBefore(nextMonth)) {
			lastDate = lastDate.plusMonths(1);
			lastAcum = lastAcum * (1 + lastValue / 100);
			ipcaAcumulado.add(new DataValor(lastDate, lastAcum));
		}
	}

	public void recuperaDados() {

		List<DataValor> ipcaAcumulado1 = new ArrayList<>();
		List<DataValor> ipca1 = new ArrayList<>();

		ipca1.add(new DataValor("01/06/2019", 0.010));
		ipca1.add(new DataValor("01/05/2019", 0.130));
		ipca1.add(new DataValor("01/04/2019", 0.570));

		ipcaAcumulado1.add(new DataValor("01/06/2019", 1343.788));
		ipcaAcumulado1.add(new DataValor("01/05/2019", 1343.654));
		ipcaAcumulado1.add(new DataValor("01/04/2019", 1341.909));
		ipcaAcumulado1.add(new DataValor("01/03/2019", 1334.304));
		ipcaAcumulado1.add(new DataValor("01/02/2019", 1324.371));
		ipcaAcumulado1.add(new DataValor("01/01/2019", 1318.700));
		ipcaAcumulado1.add(new DataValor("01/12/2018", 1314.494));
		ipcaAcumulado1.add(new DataValor("01/11/2018", 1312.525));
		ipcaAcumulado1.add(new DataValor("01/10/2018", 1315.287));
		ipcaAcumulado1.add(new DataValor("01/09/2018", 1309.395));
		ipcaAcumulado1.add(new DataValor("01/08/2018", 1303.140));
		ipcaAcumulado1.add(new DataValor("01/07/2018", 1304.314));
		ipcaAcumulado1.add(new DataValor("01/06/2018", 1300.024));
		ipcaAcumulado1.add(new DataValor("01/05/2018", 1283.847));
		ipcaAcumulado1.add(new DataValor("01/04/2018", 1278.732));
		ipcaAcumulado1.add(new DataValor("01/03/2018", 1275.925));
		ipcaAcumulado1.add(new DataValor("01/02/2018", 1274.778));
		ipcaAcumulado1.add(new DataValor("01/01/2018", 1270.712));
		ipcaAcumulado1.add(new DataValor("01/12/2017", 1267.037));
		ipcaAcumulado1.add(new DataValor("01/11/2017", 1261.487));
		ipcaAcumulado1.add(new DataValor("01/10/2017", 1257.965));
		ipcaAcumulado1.add(new DataValor("01/09/2017", 1252.703));
		ipcaAcumulado1.add(new DataValor("01/08/2017", 1250.702));
		ipcaAcumulado1.add(new DataValor("01/07/2017", 1248.330));
		ipcaAcumulado1.add(new DataValor("01/06/2017", 1245.341));
		ipcaAcumulado1.add(new DataValor("01/05/2017", 1248.212));
		ipcaAcumulado1.add(new DataValor("01/04/2017", 1244.355));
		ipcaAcumulado1.add(new DataValor("01/03/2017", 1242.615));
		ipcaAcumulado1.add(new DataValor("01/02/2017", 1239.516));
		ipcaAcumulado1.add(new DataValor("01/01/2017", 1235.439));
		ipcaAcumulado1.add(new DataValor("01/12/2016", 1230.763));
		ipcaAcumulado1.add(new DataValor("01/11/2016", 1227.081));
		ipcaAcumulado1.add(new DataValor("01/10/2016", 1224.877));
		ipcaAcumulado1.add(new DataValor("01/09/2016", 1221.700));
		ipcaAcumulado1.add(new DataValor("01/08/2016", 1220.724));
		ipcaAcumulado1.add(new DataValor("01/07/2016", 1215.376));
		ipcaAcumulado1.add(new DataValor("01/06/2016", 1209.089));
		ipcaAcumulado1.add(new DataValor("01/05/2016", 1204.872));
		ipcaAcumulado1.add(new DataValor("01/04/2016", 1195.546));
		ipcaAcumulado1.add(new DataValor("01/03/2016", 1188.298));
		ipcaAcumulado1.add(new DataValor("01/02/2016", 1183.210));
		ipcaAcumulado1.add(new DataValor("01/01/2016", 1172.656));
		ipcaAcumulado1.add(new DataValor("01/12/2015", 1157.950));
		ipcaAcumulado1.add(new DataValor("01/11/2015", 1146.939));
		ipcaAcumulado1.add(new DataValor("01/10/2015", 1135.471));
		ipcaAcumulado1.add(new DataValor("01/09/2015", 1126.236));
		ipcaAcumulado1.add(new DataValor("01/08/2015", 1120.187));
		ipcaAcumulado1.add(new DataValor("01/07/2015", 1117.728));
		ipcaAcumulado1.add(new DataValor("01/06/2015", 1110.841));
		ipcaAcumulado1.add(new DataValor("01/05/2015", 1102.134));
		ipcaAcumulado1.add(new DataValor("01/04/2015", 1094.038));
		ipcaAcumulado1.add(new DataValor("01/03/2015", 1086.325));
		ipcaAcumulado1.add(new DataValor("01/02/2015", 1072.172));
		ipcaAcumulado1.add(new DataValor("01/01/2015", 1059.250));

		ipca = ipca1.stream().sorted().collect(Collectors.toList());
		ipcaAcumulado = ipcaAcumulado1.stream().sorted().collect(Collectors.toList());
	}

}
