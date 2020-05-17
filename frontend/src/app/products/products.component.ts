import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ProdutoRF } from './produtoRF';
import { TabelaProdutosComponent } from './tabela-produtos/tabela-produtos.component';
import { ProdutoRV } from './produtoRV';
import { Extrato } from './extrato';


@Component({
    selector: 'app-products',
    templateUrl: './products.component.html',
    styleUrls: ['./products.component.scss']
  })
  export class ProductsComponent implements OnInit {

    produtoRF = new ProdutoRF();
    produtoRV = new ProdutoRV();
    extrato = new Extrato();

    @ViewChild('tabelaProdutosRF', {static: true}) tabelaProdutosRF: TabelaProdutosComponent;
    @ViewChild('tabelaProdutosRV', {static: true}) tabelaProdutosRV: TabelaProdutosComponent;
    @ViewChild('tabelaExtrato', {static: true}) tabelaExtrato: TabelaProdutosComponent;
    constructor(private http: HttpClient ) { }

    ngOnInit() {
      this.http.get<any>(`${environment.baseUrl}produtos`).subscribe( resp => {
        console.log(resp);
        this.tabelaProdutosRF.updateData(resp.produtosRF);
        this.tabelaProdutosRV.updateData(resp.produtosRV);
        this.tabelaExtrato.updateData(resp.extrato);
      });
    }

  }
