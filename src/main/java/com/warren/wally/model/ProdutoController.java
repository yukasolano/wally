package com.warren.wally.model;


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

        Iterable<ProdutoEntity> produtos = repository.findAll();
        model.addAttribute("produtos", produtos);

        return "produtos";
    }
    
}
