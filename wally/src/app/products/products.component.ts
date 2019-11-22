import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ProdutoRF } from './produtoRF';
import { TabelaProdutosComponent } from './tabela-produtos/tabela-produtos.component';


@Component({
    selector: 'app-products',
    templateUrl: './products.component.html',
    styleUrls: ['./products.component.scss']
  })
  export class ProductsComponent implements OnInit {

   @ViewChild(TabelaProdutosComponent, {static: true}) produtosRF: TabelaProdutosComponent;

    constructor(private http: HttpClient ) { }

    ngOnInit() {

      this.http.get<ProdutoRF[]>(`${environment.baseUrl}produtosRF`).subscribe( resp => {
        this.produtosRF.updateData(resp);
      });
    }

  }

  // https://stackoverflow.com/questions/53198805/angular-pass-pipe-as-variable
