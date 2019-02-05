package com.warren.wally.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CalculadoraIPCA implements Calculadora {

	List<DataValor> ipcaAcumulado = new ArrayList<>();
	@Override
	public double calculaVPBruto(double valorAplicado, double taxa, LocalDate dtAplicacao, LocalDate dtRef) {
		recuperaDados();
		
		double fatorPre = getFatorPre(dtAplicacao, dtRef, taxa);
		double ipcaAcum = getFatorAcumulado(dtAplicacao, dtRef);
		return valorAplicado*fatorPre*ipcaAcum;
	}

	@Override
	public TipoRentabilidade getTipoRentabilidade() {
		return TipoRentabilidade.IPCA;
	}
	
	private double getFatorPre(LocalDate dtInicio, LocalDate dtFim, double taxa) {
		BussinessDaysCalendar bc = new BussinessDaysCalendar();
		long du = bc.getDu(dtInicio, dtFim);
		return Math.pow(1+ taxa,du/252.0);
	}
	
	private double getFatorAcumulado(LocalDate dtInicio, LocalDate dtFim) {
		
		List<DataValor> ipcaAcumuladoFiltrado = ipcaAcumulado.stream().filter(dt -> 
			dt.getData().isBefore(dtFim) && dt.getData().isAfter(dtInicio.minusMonths(2))
		).collect(Collectors.toList());
		DataValor ipcaAcumuladoInicial = ipcaAcumuladoFiltrado.get(ipcaAcumuladoFiltrado.size()-1);
		DataValor ipcaAcumuladoFinal = ipcaAcumuladoFiltrado.get(0);
		return ipcaAcumuladoFinal.getValor()/ipcaAcumuladoInicial.getValor();
	}
	
	public void recuperaDados() {
		ipcaAcumulado.add(new DataValor("01/12/2018",1314.494));
		ipcaAcumulado.add(new DataValor("01/11/2018",1312.525));
		ipcaAcumulado.add(new DataValor("01/10/2018",1315.287));
		ipcaAcumulado.add(new DataValor("01/09/2018",1309.395));
		ipcaAcumulado.add(new DataValor("01/08/2018",1303.140));
		ipcaAcumulado.add(new DataValor("01/07/2018",1304.314));
		ipcaAcumulado.add(new DataValor("01/06/2018",1300.024));
		ipcaAcumulado.add(new DataValor("01/05/2018",1283.847));
		ipcaAcumulado.add(new DataValor("01/04/2018",1278.732));
		ipcaAcumulado.add(new DataValor("01/03/2018",1275.925));
		ipcaAcumulado.add(new DataValor("01/02/2018",1274.778));
		ipcaAcumulado.add(new DataValor("01/01/2018",1270.712));
		ipcaAcumulado.add(new DataValor("01/12/2017",1267.037));
		ipcaAcumulado.add(new DataValor("01/11/2017",1261.487));
		ipcaAcumulado.add(new DataValor("01/10/2017",1257.965));
		ipcaAcumulado.add(new DataValor("01/09/2017",1252.703));
		ipcaAcumulado.add(new DataValor("01/08/2017",1250.702));
		ipcaAcumulado.add(new DataValor("01/07/2017",1248.330));
		ipcaAcumulado.add(new DataValor("01/06/2017",1245.341));
		ipcaAcumulado.add(new DataValor("01/05/2017",1248.212));
		ipcaAcumulado.add(new DataValor("01/04/2017",1244.355));
		ipcaAcumulado.add(new DataValor("01/03/2017",1242.615));
		ipcaAcumulado.add(new DataValor("01/02/2017",1239.516));
		ipcaAcumulado.add(new DataValor("01/01/2017",1235.439));
		ipcaAcumulado.add(new DataValor("01/12/2016",1230.763));
		ipcaAcumulado.add(new DataValor("01/11/2016",1227.081));
		ipcaAcumulado.add(new DataValor("01/10/2016",1224.877));
		ipcaAcumulado.add(new DataValor("01/09/2016",1221.700));
		ipcaAcumulado.add(new DataValor("01/08/2016",1220.724));
		ipcaAcumulado.add(new DataValor("01/07/2016",1215.376));
		ipcaAcumulado.add(new DataValor("01/06/2016",1209.089));
		ipcaAcumulado.add(new DataValor("01/05/2016",1204.872));
		ipcaAcumulado.add(new DataValor("01/04/2016",1195.546));
		ipcaAcumulado.add(new DataValor("01/03/2016",1188.298));
		ipcaAcumulado.add(new DataValor("01/02/2016",1183.210));
		ipcaAcumulado.add(new DataValor("01/01/2016",1172.656));
		ipcaAcumulado.add(new DataValor("01/12/2015",1157.950));
		ipcaAcumulado.add(new DataValor("01/11/2015",1146.939));
		ipcaAcumulado.add(new DataValor("01/10/2015",1135.471));
		ipcaAcumulado.add(new DataValor("01/09/2015",1126.236));
		ipcaAcumulado.add(new DataValor("01/08/2015",1120.187));
		ipcaAcumulado.add(new DataValor("01/07/2015",1117.728));
		ipcaAcumulado.add(new DataValor("01/06/2015",1110.841));
		ipcaAcumulado.add(new DataValor("01/05/2015",1102.134));
		ipcaAcumulado.add(new DataValor("01/04/2015",1094.038));
		ipcaAcumulado.add(new DataValor("01/03/2015",1086.325));
		ipcaAcumulado.add(new DataValor("01/02/2015",1072.172));
		ipcaAcumulado.add(new DataValor("01/01/2015",1059.250));



	}

}
