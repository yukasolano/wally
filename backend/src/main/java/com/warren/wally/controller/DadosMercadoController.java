package com.warren.wally.controller;

import com.warren.wally.model.dadosmercado.DadosMercadoActor;
import com.warren.wally.portfolio.PortfolioActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/dados-mercado")
public class DadosMercadoController {

    @Autowired
    private DadosMercadoActor dadosMercadoActor;

    @Autowired
    private PortfolioActor portfolioActor;

    @PostMapping(value = "limpa")
    public MessageOutDTO limparDadosMercado() {
        dadosMercadoActor.limpa();
        return MessageOutDTO.ok("Dados de mercados removidos com sucesso");
    }

    @PostMapping(value = "atualiza")
    public MessageOutDTO atualizaDadosMercado() {
        dadosMercadoActor.atualizaDadosMercado();
        portfolioActor.limpaMapa();
        return MessageOutDTO.ok("Dados de mercados atualizados com sucesso");
    }

    @GetMapping(value = "busca")
    public DadosMercadoVO mostraDadosMercado() {
        return dadosMercadoActor.busca();
    }
}
