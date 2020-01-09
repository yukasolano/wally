package com.warren.wally.model.investimento;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warren.wally.repository.DividendoEntity;
import com.warren.wally.repository.DividendoRepository;
import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;

@Component
public class ProdutoRVActor {

	@Resource
	private MovimentacaoRepository movimentacaoRepository;

	@Resource
	private DividendoRepository dividendoRepository;

	@Autowired
	private DataMarketEquities dm;

	// TODO filtrar por tipo de movimentacao
	public ProdutoRVVO run(LocalDate dataPosicao, String codigo) {
		List<MovimentacaoEntity> movimentacoes = movimentacaoRepository.findByCodigoAndDataLessThan(codigo,
				dataPosicao);
		ProdutoRVVO produto = new ProdutoRVVO(codigo);
		movimentacoes.stream().forEach(it -> adicionaMovimentacao(it, produto));
		produto.setCotacao(dm.get(produto.getCodigo(), dataPosicao));
		produto.setPrecoMedio(produto.getPrecoTotal() / produto.getQuantidade());
		produto.setValorPresente(produto.getCotacao() == 0 ? produto.getPrecoTotal() : produto.getQuantidade() * produto.getCotacao());
		produto.setResultado(produto.getValorPresente() - produto.getPrecoTotal());
		produto.setRentabilidadeDividendo(getRentabilidade(produto, dataPosicao));
		return produto;
	}

	public List<ProdutoRVVO> run(LocalDate dataPosicao) {

		List<MovimentacaoEntity> movimentacoes = movimentacaoRepository.findByDataLessThan(dataPosicao);

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
			produto.setValorPresente(produto.getCotacao() == 0 ? produto.getPrecoTotal() : produto.getQuantidade() * produto.getCotacao());
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
		List<DividendoEntity> dividendos = getDividendos(produto.getCodigo(), dataPosicao);
		Double somaDividendos = dividendos.stream().mapToDouble(DividendoEntity::getValorUnitario).sum();
		Double rentabilidade = somaDividendos / produto.getPrecoMedio();
		if (produto.getTipoInvestimento().equals(TipoInvestimento.ACAO)) {
			return rentabilidade;
		}
		Long quantidade = dividendos.stream().count();
		Double rentabilidadeAnualizada = Math.pow((1 + rentabilidade), 12.0 / quantidade) - 1;
		return rentabilidadeAnualizada;
	}

	public List<DividendoEntity> getDividendos(String codigo, LocalDate dataPosicao) {
		return dividendoRepository.findByCodigoAndDataBetweenOrderByDataDesc(codigo, dataPosicao.minusYears(1),
				dataPosicao);
	}

	public List<DividendoEntity> getDividendos(LocalDate dataPosicao) {
		return dividendoRepository.findByDataBetweenOrderByData(dataPosicao.minusYears(1), dataPosicao);
	}
}
