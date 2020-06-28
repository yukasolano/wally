import { OnInit, ViewChild, Component, Input } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { saveAs } from 'file-saver';
import { HttpService } from 'src/app/services/http.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-tabela-produtos',
  templateUrl: './tabela-produtos.component.html',
  styleUrls: ['./tabela-produtos.component.scss']
})
export class TabelaProdutosComponent implements OnInit {

  @Input() tableInfo;
  @Input() downloadPath = '';
  @Input() showDetails;

  @Input() set dados(dados: []) {
    this.updateData(dados);
  }

  date = '';
  infoColumns: string[];
  displayedColumns: string[];
  dataSource = new MatTableDataSource();

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(private http: HttpClient, private httpService: HttpService, private router: Router) {}
  ngOnInit() {
    this.infoColumns = Object.keys(this.tableInfo);
    this.displayedColumns = this.showDetails !== undefined ? this.infoColumns.concat('details') : this.infoColumns;
  }

  updateData(data, date: string = '') {
      this.date = date;
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
  }

  onDownload() {
    this.http.post(`${environment.baseUrl}${this.downloadPath}`,  this.dataSource.data ,
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

  onDetails(codigo) {
      console.log('hey' + codigo);
      this.router.navigate(['produtcs', 'renda-fixa', codigo, this.date]);
  }

}
