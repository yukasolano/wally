package com.warren.wally.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;

@Component
public class ProdutoFIIActor {

	@Resource
	private MovimentacaoRepository movimentacaoRepository;

	// TODO filtrar por tipo de movimentacao
	public ProdutoFIIVO run(LocalDate dataPosicao, String codigo) {
		List<MovimentacaoEntity> movimentacoes = movimentacaoRepository.findByCodigoAndDataLessThan(codigo,
				dataPosicao);
		ProdutoFIIVO produto = new ProdutoFIIVO(codigo);
		movimentacoes.stream().forEach(it -> adicionaMovimentacao(it, produto));

		return produto;
	}

	public List<ProdutoFIIVO> run(LocalDate dataPosicao) {
		DataMarketEquities dm = new DataMarketEquities();
		
		List<MovimentacaoEntity> movimentacoes = movimentacaoRepository.findByDataLessThan(dataPosicao);

		Map<String, ProdutoFIIVO> produtos = new HashMap<>();

		for (MovimentacaoEntity entity : movimentacoes) {
			if (produtos.containsKey(entity.getCodigo())) {
				adicionaMovimentacao(entity, produtos.get(entity.getCodigo()));
			} else {
				ProdutoFIIVO vo = new ProdutoFIIVO(entity.getCodigo());
				vo.setCotacao(dm.get(entity.getCodigo(), dataPosicao));
				adicionaMovimentacao(entity, vo);
				produtos.put(entity.getCodigo(), vo);
			}
		}
		return produtos.values().stream().collect(Collectors.toList());
	}

	
	private void adicionaMovimentacao(MovimentacaoEntity mov, ProdutoFIIVO vo) {
		vo.setPrecoTotal(vo.getPrecoTotal() + mov.getValorUnitario() * mov.getQuantidade());
		vo.setQuantidade(vo.getQuantidade() + mov.getQuantidade());
	}
}
