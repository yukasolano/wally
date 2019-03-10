package com.warren.wally.model;

import java.time.LocalDate;

public class ProdutoDebenture extends Produto {

	public ProdutoDebenture(LocalDate dtAplicacao, LocalDate dtVencimento, double valorAplicado, double taxa) {
		super(dtAplicacao, dtVencimento, valorAplicado, taxa);
	}

	@Override
	public TipoInvestimento getTipoInvestimento() {
		return TipoInvestimento.DEBENTURE;
	}

	@Override
	public void calculaAccrual(LocalDate hoje) {
		if (hoje == null) {
			return;
		}
		if (dtAplicacao.isAfter(hoje)) {
			return;
		}
		if (dtVencimento.isBefore(hoje)) {
			return;
		}

		du = new BussinessDaysCalendar().getDu(dtAplicacao, hoje);
		if (calc != null) {
			double VPBruto = calc.calculaVPBruto(valorAplicado, taxa, dtAplicacao, hoje);
			double ir = new Leao().getIR(VPBruto - valorAplicado, dtAplicacao, hoje);
			VPLiquido = VPBruto - ir;
			System.out.println(VPLiquido);
		}
	}

}
