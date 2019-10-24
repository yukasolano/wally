package com.warren.wally;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import com.warren.wally.model.Produto;
import com.warren.wally.model.ProdutoFactory;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.repository.ProdutoEntity;

public class TestaProdutoFactory {

	@Test
	public void test() {
		ProdutoEntity entity = new ProdutoEntity();
		entity.setCorretora("EASYNVEST");
		entity.setInstituicao("FIBRA");
		entity.setValorAplicado(6000.12);
		entity.setTaxa(12.21);
		entity.setTipoInvestimento(TipoInvestimento.CDB);
		entity.setTipoRentabilidade(TipoRentabilidade.PRE);
		entity.setDtAplicacao(LocalDate.of(2017, 12, 01));
		entity.setVencimento(LocalDate.of(2018, 12, 01));
        
        Produto produto = ProdutoFactory.getProduto(entity);
        assertEquals(TipoInvestimento.CDB, produto.getTipoInvestimento());
        assertEquals(TipoRentabilidade.PRE, produto.getTipoRentabilidade());
        
	}

}
