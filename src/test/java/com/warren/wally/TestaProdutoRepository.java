package com.warren.wally;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.warren.wally.model.TipoInvestimento;
import com.warren.wally.model.TipoRentabilidade;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WallyApplication.class})
public class TestaProdutoRepository {

	@Autowired
	ProdutoRepository repository;
	@Test
	public void testaAdicionaProduto() {	
		repository.deleteAll();
		ProdutoEntity produto = new ProdutoEntity();
        produto.setCorretora("EASYNVEST");
        produto.setInstituicao("FIBRA");
        produto.setValorAplicado(6000.12);
        produto.setTaxa(0.1221);
        produto.setTipoInvestimento(TipoInvestimento.CDB);
        produto.setTipoRentabilidade(TipoRentabilidade.PRE);
        produto.setDtAplicacao(LocalDate.of(2017, 12, 01));
        produto.setVencimento(LocalDate.of(2018, 12, 01));
        repository.save(produto);
        
        List<ProdutoEntity> produtosFromDB = repository.findByTipoInvestimento(TipoInvestimento.CDB);
        ProdutoEntity produtoFromDB = produtosFromDB.get(0);
        assertEquals(produto.getCorretora(), produtoFromDB.getCorretora());
        assertEquals(produto.getDtAplicacao(), produtoFromDB.getDtAplicacao());
        assertEquals(produto.getTipoInvestimento(), produtoFromDB.getTipoInvestimento());
	}

}
