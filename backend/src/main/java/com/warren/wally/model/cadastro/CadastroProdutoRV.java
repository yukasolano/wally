package com.warren.wally.model.cadastro;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import com.warren.wally.model.investimento.repository.MovimentacaoRepository;
import com.warren.wally.model.investimento.repository.ProdutoEntity;
import com.warren.wally.model.investimento.repository.ProdutoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CadastroProdutoRV implements CadastroProduto<ProdutoRVInfoVO> {


    @Resource
    private ProdutoRepository produtoRepository;

    @Resource
    private MovimentacaoRepository movimentacaoRepository;

    @Override
    public void save(ProdutoRVInfoVO produto) {

        List<ProdutoEntity> produtos = produtoRepository.findByCodigo(produto.getCodigo());
        if(produtos.isEmpty()) {
            //cadastra produto
            ProdutoEntity entity = new ProdutoEntity();

            entity.setCodigo(produto.getCodigo());
            entity.setTipoInvestimento(produto.getTipoInvestimento());
            entity.setTipoRentabilidade(getTipoRentabilidade(produto.getTipoInvestimento()));
            entity.setInstituicao(produto.getInstituicao() == null ? produto.getCodigo() : produto.getInstituicao());
            produtoRepository.save(entity);
        }

        //adiciona movimentacao de compra
        MovimentacaoEntity mov = new MovimentacaoEntity();
        mov.setTipoMovimento(TipoMovimento.COMPRA);
        mov.setCodigo(produto.getCodigo());
        mov.setData(produto.getData());
        mov.setQuantidade(produto.getQuantidade());
        mov.setValorUnitario(produto.getValorUnitario());
        movimentacaoRepository.save(mov);
    }

    @Override
    public void saveGeneric(ProdutoInfoVO vo) {
        ProdutoEntity entity = new ProdutoEntity();
        List<ProdutoEntity> produtos = produtoRepository.findByCodigo(vo.getCodigo());
        if (produtos.isEmpty()) {
            //cadastra produto
            entity.setCodigo(vo.getCodigo());
            entity.setTipoInvestimento(vo.getTipoInvestimento());
            entity.setTipoRentabilidade(getTipoRentabilidade(vo.getTipoInvestimento()));
            entity.setInstituicao(vo.getInstituicao() == null ? vo.getCodigo() : vo.getInstituicao());
            produtoRepository.save(entity);
        }


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

    private TipoRentabilidade getTipoRentabilidade(TipoInvestimento tipoInvestimento) {
        for (TipoRentabilidade tipoRentabilidade : TipoRentabilidade.values()) {
            if (tipoRentabilidade.toString().equals(tipoInvestimento.toString())) {
                return tipoRentabilidade;
            }
        }
        return null;
    }
}