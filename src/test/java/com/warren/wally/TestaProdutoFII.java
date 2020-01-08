package com.warren.wally;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.investimento.ProdutoFIIActor;
import com.warren.wally.model.investimento.ProdutoFIIVO;
import com.warren.wally.repository.DividendoEntity;
import com.warren.wally.repository.DividendoRepository;
import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.assertEquals;


public class TestaProdutoFII extends WallyTestCase {

	@Autowired
	private ProdutoFIIActor actor;

	@Resource
	private MovimentacaoRepository movimentacaoRepository;
	
	@Resource 
	private DividendoRepository dividendoRepository;

	@Test
	public void test() {

		LocalDate hoje = dateOf("20/08/2018");

		MovimentacaoEntity movimento1 = new MovimentacaoEntity();
		movimento1.setCodigo("VRTA11");
		movimento1.setData(dateOf("04/06/2018"));
		movimento1.setQuantidade(2);
		movimento1.setValorUnitario(105.0);
		movimentacaoRepository.save(movimento1);
		
		MovimentacaoEntity movimento2 = new MovimentacaoEntity();
		movimento2.setCodigo("VRTA11");
		movimento2.setData(dateOf("27/07/2018"));
		movimento2.setQuantidade(2);
		movimento2.setValorUnitario(106.3);
		movimentacaoRepository.save(movimento2);
		
		DividendoEntity dividendo1 = new DividendoEntity();
		dividendo1.setCodigo("VRTA11");
		dividendo1.setData(dateOf("15/07/2018"));
		dividendo1.setQuantidade(2);
		dividendo1.setValorUnitario(0.6);
		dividendoRepository.save(dividendo1);
		
		DividendoEntity dividendo2 = new DividendoEntity();
		dividendo2.setCodigo("VRTA11");
		dividendo2.setData(dateOf("15/08/2018"));
		dividendo2.setQuantidade(4);
		dividendo2.setValorUnitario(0.65);
		dividendoRepository.save(dividendo2);
		
		DividendoEntity dividendo3 = new DividendoEntity();
		dividendo3.setCodigo("VRTA11");
		dividendo3.setData(dateOf("15/09/2018"));
		dividendo3.setQuantidade(4);
		dividendo3.setValorUnitario(0.7);
		dividendoRepository.save(dividendo3);
		
		ProdutoFIIVO produtoActual = actor.run(hoje, "VRTA11");

		assertEquals(105.65, produtoActual.getPrecoMedio(), 0.01);
		assertEquals(4, produtoActual.getQuantidade(), 0.01);
		assertEquals(0.075, produtoActual.getRentabilidadeDividendo(), 0.01);
	}

}
