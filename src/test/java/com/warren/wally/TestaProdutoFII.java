package com.warren.wally;

import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.investimento.ProdutoRVActor;
import com.warren.wally.model.investimento.ProdutoRVVO;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;

public class TestaProdutoFII extends WallyTestCase {

	@Autowired
	private ProdutoRVActor actor;

	@Resource
	private MovimentacaoRepository movimentacaoRepository;

	@Test
	public void test() {

		LocalDate hoje = dateOf("20/08/2018");

		movimentacaoRepository.save(new MovimentacaoEntity(TipoInvestimento.FII, TipoMovimento.COMPRA,
				dateOf("04/06/2018"), "VRTA11", 2, 105.0));

		movimentacaoRepository.save(new MovimentacaoEntity(TipoInvestimento.FII, TipoMovimento.COMPRA,
				dateOf("27/07/2018"), "VRTA11", 2, 106.3));

		movimentacaoRepository.save(new MovimentacaoEntity(TipoInvestimento.FII, TipoMovimento.DIVIDENDO,
				dateOf("15/07/2018"), "VRTA11", 2, 0.6));

		movimentacaoRepository.save(new MovimentacaoEntity(TipoInvestimento.FII, TipoMovimento.DIVIDENDO,
				dateOf("15/08/2018"), "VRTA11", 4, 0.65));

		movimentacaoRepository.save(new MovimentacaoEntity(TipoInvestimento.FII, TipoMovimento.DIVIDENDO,
				dateOf("15/09/2018"), "VRTA11", 4, 0.7));

		ProdutoRVVO produtoActual = actor.run(hoje, "VRTA11");

		assertEquals(105.65, produtoActual.getPrecoMedio(), 0.01);
		assertEquals(4, produtoActual.getQuantidade(), 0.01);
		assertEquals(0.075, produtoActual.getRentabilidadeDividendo(), 0.01);
	}

}
