package com.warren.wally;

import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.BussinessDaysCalendar;
import com.warren.wally.model.CalculadoraCDI;
import com.warren.wally.model.Produto;
import com.warren.wally.model.ProdutoCDB;
import com.warren.wally.model.ProdutoVO;
import com.warren.wally.model.calculadora.Calculadora;
import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.calculadora.repository.CdiEntity;
import com.warren.wally.model.calculadora.repository.CdiRepository;
import com.warren.wally.model.investimento.Investimento;
import com.warren.wally.model.investimento.InvestimentoResolver;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;

public class TestaInvestimentoCDB extends WallyTestCase{
	
	@Resource
	private CalculadoraResolver calculadoraResolver;
	
	@Resource
	private InvestimentoResolver investimentoResolver;

	@Test
	public void testCalculoCDI() {
		
		ProdutoEntity entity = new ProdutoEntity();
		entity.setDtAplicacao(dateOf("24/06/2016"));
		entity.setVencimento(dateOf("31/05/2021"));
		entity.setValorAplicado(10000);
		entity.setTaxa(1.22);
		entity.setTipoInvestimento(TipoInvestimento.CDB);
		entity.setTipoRentabilidade(TipoRentabilidade.CDI);
		LocalDate hoje = dateOf("10/12/2018");
		
		Investimento invest =  investimentoResolver.resolve(entity.getTipoInvestimento());
		assertEquals(TipoInvestimento.CDB, invest.getTipoInvestimento());
		
		ProdutoVO produto = invest.calc(hoje, entity);
		assertEquals(12606.04, produto.getValorPresente(), 0.01);
		assertEquals(26.06, produto.getRentabilidadeLiquida()*100, 0.01);
		assertEquals(9.94, produto.getTaxaAnualLiquida()*100, 0.01);
		assertEquals(0.79, produto.getTaxaMensalLiquida()*100, 0.01);
	}
}
