package com.warren.wally.model.cadastro;

import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CadastroProdutoResolver {

    @Resource
    private CadastroProdutoRF produtoRF;

    @Resource
    private CadastroProdutoRV produtoRV;

    @Resource
    private CadastroMovimento movimento;


    public CadastroProduto resolve(TipoInvestimento tipoInvestimento,
                                   TipoMovimento tipoMovimento) {
        if (tipoMovimento.equals(TipoMovimento.COMPRA)) {

            if (tipoInvestimento.equals(TipoInvestimento.CDB) || tipoInvestimento.equals(TipoInvestimento.LC) ||
                    tipoInvestimento.equals(TipoInvestimento.TESOURO) || tipoInvestimento.equals(TipoInvestimento.LCI) ||
                    tipoInvestimento.equals(TipoInvestimento.DEBENTURE)) {
                return produtoRF;
            } else if (tipoInvestimento.equals(TipoInvestimento.FII) || tipoInvestimento.equals(TipoInvestimento.ACAO)) {
                return produtoRV;
            }
            else {
                throw new RuntimeException("Tipo de investimento " + tipoInvestimento + " e tipo de movimento " + tipoMovimento + " não tratado.");
            }
        }

        return movimento;

    }
}
