import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ProdutoRF } from './produtoRF';
import { TabelaProdutosComponent } from './tabela-produtos/tabela-produtos.component';
import { ProdutoRV } from './produtoRV';


@Component({
    selector: 'app-products',
    templateUrl: './products.component.html',
    styleUrls: ['./products.component.scss']
  })
  export class ProductsComponent implements OnInit {

    date = new Date();
    produtoRF = new ProdutoRF();
    produtoRV = new ProdutoRV();

    @ViewChild('tabelaProdutosRF', {static: true}) tabelaProdutosRF: TabelaProdutosComponent;
    @ViewChild('tabelaProdutosRV', {static: true}) tabelaProdutosRV: TabelaProdutosComponent;

    constructor(private http: HttpClient ) { }


    ngOnInit() {
      this.update();
    }

    dateChanged() {
      this.update();
    }

    update() {
      this.http.get<any>(`${environment.baseUrl}produtos/renda-fixa?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
        this.tabelaProdutosRF.updateData(resp, this.date.toISOString().split('T')[0]);
      });

      this.http.get<any>(`${environment.baseUrl}produtos/renda-variavel?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
        this.tabelaProdutosRV.updateData(resp, this.date.toISOString().split('T')[0]);
      });
    }

  }
