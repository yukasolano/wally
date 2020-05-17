package com.warren.wally.model.investimento;

import javax.annotation.Resource;

import com.warren.wally.model.dadosmercado.DMequitiesActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warren.wally.model.investimento.repository.MovimentacaoRepository;

@Component
public class ProdutoRVActor {

	@Resource
	private MovimentacaoRepository movimentacaoRepository;

	@Autowired
	private DMequitiesActor dm;

	/*
	// TODO filtrar por tipo de movimentacao
	public ProdutoRVVO run(LocalDate dataPosicao, String codigo) {
		List<MovimentacaoEntity> movimentacoes = movimentacaoRepository
				.findByCodigoAndTipoMovimentoAndDataLessThan(codigo, TipoMovimento.COMPRA, dataPosicao);
		ProdutoRVVO produto = new ProdutoRVVO(codigo);
		movimentacoes.stream().forEach(it -> adicionaMovimentacao(it, produto));
		produto.setCotacao(dm.get(produto.getCodigo(), dataPosicao));
		produto.setPrecoMedio(produto.getPrecoTotal() / produto.getQuantidade());
		produto.setValorPresente(
				produto.getCotacao() == 0 ? produto.getPrecoTotal() : produto.getQuantidade() * produto.getCotacao());
		produto.setResultado(produto.getValorPresente() - produto.getPrecoTotal());
		produto.setRentabilidadeDividendo(getRentabilidade(produto, dataPosicao));
		return produto;
	}

	public List<ProdutoRVVO> run(LocalDate dataPosicao) {

		List<MovimentacaoEntity> movimentacoes = movimentacaoRepository
				.findByTipoMovimentoAndDataLessThan(TipoMovimento.COMPRA, dataPosicao);

		Map<String, ProdutoRVVO> produtos = new HashMap<>();

		for (MovimentacaoEntity entity : movimentacoes) {
			if (produtos.containsKey(entity.getCodigo())) {
				adicionaMovimentacao(entity, produtos.get(entity.getCodigo()));
			} else {
				ProdutoRVVO vo = new ProdutoRVVO(entity.getCodigo());
				adicionaMovimentacao(entity, vo);
				produtos.put(entity.getCodigo(), vo);
			}
		}

		produtos.values().stream().forEach(produto -> {
			produto.setCotacao(dm.get(produto.getCodigo(), dataPosicao));
			produto.setPrecoMedio(produto.getPrecoTotal() / produto.getQuantidade());
			produto.setValorPresente(produto.getCotacao() == 0 ? produto.getPrecoTotal()
					: produto.getQuantidade() * produto.getCotacao());
			produto.setResultado(produto.getValorPresente() - produto.getPrecoTotal());
			produto.setRentabilidadeDividendo(getRentabilidade(produto, dataPosicao));
		});
		return produtos.values().stream().collect(Collectors.toList());
	}

	private void adicionaMovimentacao(MovimentacaoEntity mov, ProdutoRVVO vo) {
		vo.setPrecoTotal(vo.getPrecoTotal() + mov.getValorUnitario() * mov.getQuantidade());
		vo.setQuantidade(vo.getQuantidade() + mov.getQuantidade());
		vo.setTipoInvestimento(mov.getTipoInvestimento());
	}

	private Double getRentabilidade(ProdutoRVVO produto, LocalDate dataPosicao) {
		List<MovimentacaoEntity> dividendos = getDividendos(produto.getCodigo(), dataPosicao);
		Double somaDividendos = dividendos.stream().mapToDouble(MovimentacaoEntity::getValorUnitario).sum();
		Double rentabilidade = somaDividendos / produto.getPrecoMedio();
		if (produto.getTipoInvestimento().equals(TipoInvestimento.ACAO)) {
			return rentabilidade;
		}
		Long quantidade = dividendos.stream().count();
		Double rentabilidadeAnualizada = Math.pow((1 + rentabilidade), 12.0 / quantidade) - 1;
		return rentabilidadeAnualizada;
	}

	public List<MovimentacaoEntity> getDividendos(String codigo, LocalDate dataPosicao) {
		return movimentacaoRepository.findByCodigoAndTipoMovimentoAndDataBetweenOrderByDataDesc(codigo,
				TipoMovimento.DIVIDENDO, dataPosicao.minusYears(1), dataPosicao);
	}

	public List<MovimentacaoEntity> getDividendos(LocalDate dataPosicao) {
		return movimentacaoRepository.findByTipoMovimentoAndDataBetweenOrderByData(TipoMovimento.DIVIDENDO,
				dataPosicao.minusYears(1), dataPosicao);
	}
	
	public List<MovimentacaoEntity> getExtrato(LocalDate dataPosicao) {
		return movimentacaoRepository.findByDataBetweenOrderByData(dataPosicao.minusYears(1), dataPosicao);
	}

	 */
}
