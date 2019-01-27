package com.warren.wally.model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;




@Controller
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;


	@RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("produtos")
    public String produtos(Model model){

    	List<IProduto> produtos = new ArrayList<>();
        repository.findAll().forEach(entity -> {
        	IProduto produto = ProdutoFactory.getProduto(entity);
        	if(produto != null) {
        		produto.calculaAccrual(LocalDate.now());
        		produtos.add(produto);
        	}
        });
        model.addAttribute("produtos", produtos);
        model.addAttribute("hoje", LocalDate.now());
        return "produtos";
    }
    
    @RequestMapping(value= "salvar", method = RequestMethod.POST)
    public String salvar(
    		@RequestParam("corretora") String corretora, 
    		@RequestParam("instituicao") String instituicao, 
    		@RequestParam("tipoInvestimento") String tipoInvestimento,
    		@RequestParam("tipoRentabilidade") String tipoRentabilidade,
    		@RequestParam("vencimento") String vencimento,
    		@RequestParam("dtAplicacao") String dtAplicacao,
    		@RequestParam("taxa") double taxa,
    		@RequestParam("valorAplicado") double valorAplicado,
            Model model ){

    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		
        ProdutoEntity novoProduto = new ProdutoEntity();
        novoProduto.setCorretora(corretora);
        novoProduto.setInstituicao(instituicao);
        novoProduto.setTipoInvestimento(TipoInvestimento.valueOf(tipoInvestimento));
        novoProduto.setTipoRentabilidade(TipoRentabilidade.valueOf(tipoRentabilidade));
        novoProduto.setVencimento(LocalDate.parse(vencimento, dtf));
        novoProduto.setDtAplicacao(LocalDate.parse(dtAplicacao, dtf));
        novoProduto.setTaxa(taxa);
        novoProduto.setValorAplicado(valorAplicado);
        repository.save(novoProduto);
        
        List<IProduto> produtos = new ArrayList<>();
        repository.findAll().forEach(entity -> {
        	IProduto produto = ProdutoFactory.getProduto(entity);
        	if(produto != null) {
        		produto.calculaAccrual(LocalDate.now());
        		produtos.add(produto);
        	}
        });
        model.addAttribute("produtos", produtos);
        model.addAttribute("hoje", LocalDate.now());

        return "produtos";
    }
    
}
