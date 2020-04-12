package com.warren.wally.model.cadastro;

import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CadastroProdutoRF implements CadastroProduto<ProdutoRFInfoVO> {


    @Resource
    private ProdutoRepository produtoRepository;

    @Resource
    private MovimentacaoRepository movimentacaoRepository;

    @Override
    public void save(ProdutoRFInfoVO produto) {

        String codigo = String.format("%s-%s-%s-%.4f-%s", produto.getInstituicao(), produto.getTipoInvestimento(),
                produto.getTipoRentabilidade(), produto.getTaxa(), produto.getDtVencimento());
        //cadastra produto
        ProdutoEntity entity = new ProdutoEntity();

        entity.setCodigo(codigo);
        entity.setInstituicao(produto.getInstituicao());
        entity.setVencimento(produto.getDtVencimento());
        entity.setTaxa(produto.getTaxa());
        entity.setTipoInvestimento(produto.getTipoInvestimento());
        entity.setTipoRentabilidade(produto.getTipoRentabilidade());
        produtoRepository.save(entity);

        //adiciona movimentacao de compra
        MovimentacaoEntity mov = new MovimentacaoEntity();
        mov.setTipoMovimento(TipoMovimento.COMPRA);
        mov.setCodigo(codigo);
        mov.setData(produto.getDtAplicacao());
        mov.setQuantidade(1);
        mov.setValorUnitario(produto.getValorAplicado());
        movimentacaoRepository.save(mov);
    }

    @Override
    public void saveGeneric(ProdutoInfoVO vo) {
        ProdutoEntity entity = new ProdutoEntity();

        entity.setCodigo(vo.getCodigo());
        entity.setInstituicao(vo.getInstituicao());
        entity.setVencimento(vo.getDtVencimento());
        entity.setTaxa(vo.getTaxa());
        entity.setTipoInvestimento(vo.getTipoInvestimento());
        entity.setTipoRentabilidade(vo.getTipoRentabilidade());
        produtoRepository.save(entity);

        //adiciona movimentacao de compra
        MovimentacaoEntity mov = new MovimentacaoEntity();
        mov.setTipoMovimento(TipoMovimento.COMPRA);
        mov.setCodigo(vo.getCodigo());
        mov.setData(vo.getData());
        mov.setQuantidade(vo.getQuantidade());
        mov.setValorUnitario(vo.getValorUnitario());
        mov.setCorretora(vo.getCorretora());
        movimentacaoRepository.save(mov);
    }

}