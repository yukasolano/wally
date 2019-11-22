import { OnInit, ViewChild, Component } from '@angular/core';
import { ProdutoRF } from '../produtoRF';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';


@Component({
  selector: 'app-tabela-produtos',
  templateUrl: './tabela-produtos.component.html',
  styleUrls: ['./tabela-produtos.component.scss']
})
export class TabelaProdutosComponent implements OnInit {

  displayedColumns: string[];
  dataSource = new MatTableDataSource();

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  ngOnInit() {
    this.displayedColumns = ['instituicao', 'tipoInvestimento', 'tipoRentabilidade', 'valorAplicado', 'taxa',
    'dtAplicacao', 'dtVencimento', 'valorPresente', 'taxaMensalLiquida'];
  }

  updateData(data: ProdutoRF[]) {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
  }

}
