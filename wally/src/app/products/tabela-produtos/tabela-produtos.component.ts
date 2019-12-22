import { OnInit, ViewChild, Component, Input } from '@angular/core';
import { ProdutoRF } from '../produtoRF';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { saveAs } from 'file-saver';


@Component({
  selector: 'app-tabela-produtos',
  templateUrl: './tabela-produtos.component.html',
  styleUrls: ['./tabela-produtos.component.scss']
})
export class TabelaProdutosComponent implements OnInit {

  @Input() tableInfo;
  @Input() downloadPath = '';

  displayedColumns: string[];
  dataSource = new MatTableDataSource();

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private http: HttpClient) {}
  ngOnInit() {
    this.displayedColumns = Object.keys(this.tableInfo);
  }

  updateData(data) {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
  }

  onDownload() {
    this.http.get(`${environment.baseUrl}${this.downloadPath}`,
    { observe: 'response' as 'response', responseType: 'arraybuffer' as 'blob'}).subscribe( response => {
      const blob = new Blob([response.body], { type: response.headers.get('Content-Type') });
      const headerName = response.headers.get('content-disposition');
      let filename;
      if (headerName) {
        filename = headerName.split('=')[1];
      }
      saveAs(blob, filename);
    });
  }

}
