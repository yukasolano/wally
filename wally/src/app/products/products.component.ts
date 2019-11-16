import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { ProdutoRF } from './produtoRF';


@Component({
    selector: 'app-products',
    templateUrl: './products.component.html',
    styleUrls: ['./products.component.scss']
  })
  export class ProductsComponent implements OnInit {

    courses$: Observable<ProdutoRF[]>;
    displayedColumns: string[];
    dataSource = new MatTableDataSource();

    @ViewChild(MatSort, {static: true}) sort: MatSort;

    constructor(private http: HttpClient ) { }

    ngOnInit() {
      this.displayedColumns = ['instituicao', 'tipoInvestimento', 'tipoRentabilidade', 'valorAplicado'];

      this.http.get<ProdutoRF[]>(`${environment.baseUrl}produtosRF`).subscribe( resp => {
          this.dataSource = new MatTableDataSource(resp);
          this.dataSource.sort = this.sort;
      });
    }

  }
