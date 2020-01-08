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
public class ProdutoFIIActor {

	@Resource
	private MovimentacaoRepository movimentacaoRepository;

	@Resource
	private DividendoRepository dividendoRepository;

	@Autowired
	private DataMarketEquities dm;

	// TODO filtrar por tipo de movimentacao
	public ProdutoFIIVO run(LocalDate dataPosicao, String codigo) {
		List<MovimentacaoEntity> movimentacoes = movimentacaoRepository.findByCodigoAndDataLessThan(codigo,
				dataPosicao);
		ProdutoFIIVO produto = new ProdutoFIIVO(codigo);
		movimentacoes.stream().forEach(it -> adicionaMovimentacao(it, produto));
		produto.setRentabilidadeDividendo(getRentabilidade(produto, dataPosicao));
		return produto;
	}

	public List<ProdutoFIIVO> run(LocalDate dataPosicao) {

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

		produtos.values().stream()
				.forEach(produto -> produto.setRentabilidadeDividendo(getRentabilidade(produto, dataPosicao)));
		return produtos.values().stream().collect(Collectors.toList());
	}

	private void adicionaMovimentacao(MovimentacaoEntity mov, ProdutoFIIVO vo) {
		vo.setPrecoTotal(vo.getPrecoTotal() + mov.getValorUnitario() * mov.getQuantidade());
		vo.setQuantidade(vo.getQuantidade() + mov.getQuantidade());
	}

	private Double getRentabilidade(ProdutoFIIVO produto, LocalDate dataPosicao) {
		List<DividendoEntity> dividendos = getDividendos(produto.getCodigo(), dataPosicao);
		Double somaDividendos = dividendos.stream().mapToDouble(DividendoEntity::getValorUnitario).sum();
		Double rentabilidade = somaDividendos / produto.getPrecoMedio();
		Long quantidade = dividendos.stream().count();
		Double rentabilidadeAnualizada = Math.pow((1 + rentabilidade), 12.0 / quantidade) - 1;
		return rentabilidadeAnualizada;
	}

 	public List<DividendoEntity> getDividendos(String codigo, LocalDate dataPosicao) {
		return dividendoRepository.findByCodigoAndDataBetweenOrderByDataDesc(codigo,
				dataPosicao.minusYears(1), dataPosicao);
	}
 	
 	public List<DividendoEntity> getDividendos(LocalDate dataPosicao) {
		return dividendoRepository.findByDataBetweenOrderByData(dataPosicao.minusYears(1), dataPosicao);
	}
}
