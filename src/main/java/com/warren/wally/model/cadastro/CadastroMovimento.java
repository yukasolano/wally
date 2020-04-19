package com.warren.wally.model.cadastro;

import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;


@Component
public class CadastroMovimento implements CadastroProduto<MovimentoInfoVO> {


    @Resource
    private ProdutoRepository produtoRepository;

    @Resource
    private MovimentacaoRepository movimentacaoRepository;

    @Override
    public void save(MovimentoInfoVO movimento) {
        List<ProdutoEntity> produtos = produtoRepository.findByCodigo(movimento.getCodigo());
        if (produtos.isEmpty()) {
            System.out.println("Produto não cadastrado " + movimento.getCodigo());
        } else {
            MovimentacaoEntity entity = new MovimentacaoEntity();
            entity.setTipoMovimento(movimento.getTipoMovimento());
            entity.setValorUnitario(movimento.getValorUnitario());
            entity.setQuantidade(movimento.getQuantidade());
            entity.setData(movimento.getData());
            entity.setCodigo(movimento.getCodigo());
            movimentacaoRepository.save(entity);
        }
    }

    @Override
    public void saveGeneric(ProdutoInfoVO vo) {
        List<ProdutoEntity> produtos = produtoRepository.findByCodigo(vo.getCodigo());
        if (produtos.isEmpty()) {
            System.out.println("Produto não cadastrado " + vo.getCodigo());
        } else {
            MovimentacaoEntity entity = new MovimentacaoEntity();
            entity.setTipoMovimento(vo.getTipoMovimento());
            entity.setValorUnitario(vo.getValorUnitario());
            entity.setQuantidade(vo.getQuantidade());
            entity.setData(vo.getData());
            entity.setCodigo(vo.getCodigo());
            entity.setCorretora(vo.getCorretora());
            movimentacaoRepository.save(entity);
        }
    }
}