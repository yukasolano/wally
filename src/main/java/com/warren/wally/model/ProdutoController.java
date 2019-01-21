package com.warren.wally.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
        	produto.calculaAccrual(LocalDate.now());
        	produtos.add(produto);
        });
        model.addAttribute("produtos", produtos);
        model.addAttribute("hoje", LocalDate.now());
        return "produtos";
    }
    
}
