package com.warren.wally.model.cadastro;

import com.warren.wally.controller.ExtratoVO;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import com.warren.wally.model.investimento.repository.MovimentacaoRepository;
import com.warren.wally.model.investimento.repository.ProdutoEntity;
import com.warren.wally.model.investimento.repository.ProdutoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExtratoActor {

    @Resource
    private MovimentacaoRepository movimentacaoRepository;

    @Resource
    private ProdutoRepository produtoRepository;


    public void limpa() {
        movimentacaoRepository.deleteAll();
        produtoRepository.deleteAll();
    }

    public List<ExtratoVO> getExtrato() {

        List<ExtratoVO> extrato = new ArrayList<>();
        Map<String, ProdutoEntity> produtos = new HashMap<>();
        produtoRepository.findAll().forEach(it -> produtos.put(it.getCodigo(), it));

        Iterable<MovimentacaoEntity> movEntities = movimentacaoRepository.findAll();
        movEntities.forEach(it -> {
            ExtratoVO vo = new ExtratoVO();
            ProdutoEntity produto = produtos.get(it.getCodigo());
            if (it.getTipoMovimento().equals(TipoMovimento.COMPRA)) {
                vo.setInstituicao(produto.getInstituicao());
                vo.setTipoRentabilidade(produto.getTipoRentabilidade());
                vo.setTaxa(produto.getTaxa());
                vo.setVencimento(produto.getVencimento());
            }
            vo.setTipoInvestimento(produto.getTipoInvestimento());
            vo.setTipoMovimento(it.getTipoMovimento());
            vo.setCodigo(it.getCodigo());
            vo.setData(it.getData());
            vo.setQuantidade(it.getQuantidade());
            vo.setValor(it.getValorUnitario());
            vo.setCorretora(it.getCorretora());
            extrato.add(vo);
        });
        return extrato;
    }
}
