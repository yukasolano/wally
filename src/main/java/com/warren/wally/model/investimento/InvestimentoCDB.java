package com.warren.wally.model.investimento;

import java.time.LocalDate;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.warren.wally.model.BussinessDaysCalendar;
import com.warren.wally.model.Leao;
import com.warren.wally.model.ProdutoVO;
import com.warren.wally.model.calculadora.Calculadora;
import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.repository.ProdutoEntity;

@Component
public class InvestimentoCDB implements Investimento {
	
	@Resource
	private CalculadoraResolver calculadoraResolver;
	
	@Override
	public TipoInvestimento getTipoInvestimento() {
		return TipoInvestimento.CDB;
	}

	@Override
	public ProdutoVO calc(LocalDate hoje, ProdutoEntity entity) {
		
		
		ProdutoVO vo = ProdutoVO.builder()
				.corretora(entity.getCorretora())
				.dtAplicacao(entity.getDtAplicacao())
				.dtVencimento(entity.getVencimento())
				.instituicao(entity.getInstituicao())
				.taxa(entity.getTaxa())
				.valorAplicado(entity.getValorAplicado())
				.tipoInvestimento(entity.getTipoInvestimento())
				.tipoRentabilidade(entity.getTipoRentabilidade())
				.dataReferencia(hoje)
				.build();
		
		if (vo.getDtAplicacao().isAfter(hoje)) {
			return null;
		}
		if (vo.getDtVencimento().isBefore(hoje)) {
			return null;
		}


		vo.setDu(new BussinessDaysCalendar().getDu(vo.getDtAplicacao(), vo.getDataReferencia()));
		Calculadora calc = calculadoraResolver.resolve(vo.getTipoRentabilidade());
		if (calc != null) {
			double VPBruto = calc.calculaVPBruto(vo.getValorAplicado(), vo.getTaxa(), 
					vo.getDtAplicacao(), vo.getDataReferencia());
			vo.setValorPresente(getValorPresente(vo, VPBruto));
			vo.setRentabilidadeLiquida(getRentabilidadeLiquida(vo));
			vo.setTaxaAnualLiquida(getTaxaAnualLiquida(vo));
			vo.setTaxaMensalLiquida(getTaxaMensalLiquida(vo));
		}
		return vo;
	}
	
	private double getValorPresente(ProdutoVO vo, double VPBruto) {
		double ir = new Leao().getIR(VPBruto - vo.getValorAplicado(), vo.getDtAplicacao(), vo.getDataReferencia());
		return VPBruto - ir;	
	}
	
	private double getRentabilidadeLiquida(ProdutoVO vo) {
		return vo.getValorPresente()/vo.getValorAplicado() - 1;
	}

	private double getTaxaAnualLiquida(ProdutoVO vo) {
		if (vo.getDu() == 0) { return 0;}
		try {		
			return Math.pow(vo.getRentabilidadeLiquida() + 1, 252.0/vo.getDu())-1;
		}catch(Exception exp) {
			return 0.0;
		}
	}

	private double getTaxaMensalLiquida(ProdutoVO vo) {
		if (vo.getDu() == 0) { return 0;}
		try {
			return Math.pow(vo.getRentabilidadeLiquida() + 1, 21.0/vo.getDu())-1;
		}catch(Exception exp) {
			return 0.0;
		}
	}
}
