import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from 'src/app/services/http.service';
import { MatTableDataSource } from '@angular/material';
import { ProdutoRF } from '../produtoRF';
import { LineChartComponent } from 'src/app/dashboard/line-chart/line-chart.component';


export class CodigoValor {
    constructor(private codigo: string, private valor: string) {}
}
@Component({
    templateUrl: './renda-fixa-details.component.html',
    styleUrls: ['./renda-fixa-details.component.scss']
})
export class RendaFixaDetailsComponent implements OnInit {

    displayedColumns = ['codigo', 'valor'];
    dataSource = new MatTableDataSource();
    details = [];

    codigo = '';
    data = '';

    produtoRF = new ProdutoRF();

    @ViewChild('evolucao', {static: true}) evolucao: LineChartComponent;
    @ViewChild('rentabilidade', {static: true}) rentabilidade: LineChartComponent;

    constructor(private activatedRoute: ActivatedRoute, private httpService: HttpService) { }

    ngOnInit(): void {

        this.codigo = this.activatedRoute.snapshot.params.codigo;
        this.data = this.activatedRoute.snapshot.params.data;

        this.httpService.get<ProdutoRF>(`produtos/renda-fixa/${this.codigo}?date=${this.data}`).subscribe( response => {
            console.log(response);

            const keys = Object.keys(this.produtoRF.getTableInfo());
            keys.forEach (it => {
                this.details.push(new CodigoValor( this.produtoRF.getTableInfo()[it].name, response[it]));
            });
            this.dataSource = new MatTableDataSource(this.details);

            this.update();
        });
    }

    update() {

        this.httpService.get<any>(`produtos/evolucao/${this.codigo}?date=${this.data}`).subscribe( resp => {
        this.evolucao.update(resp.data, resp.labels, resp.series);
        });

        this.httpService.get<any>(`produtos/rentabilidade/${this.codigo}?date=${this.data}`).subscribe( resp => {
        this.rentabilidade.update(resp.data, resp.labels, resp.series);
        });

    }

}
