import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material';
import { ProdutoRV } from '../produtoRV';
import { LineChartComponent } from 'src/app/dashboard/line-chart/line-chart.component';
import { ActivatedRoute } from '@angular/router';
import { HttpService } from 'src/app/services/http.service';
import { CodigoValor } from '../codigo-valor';

@Component({
    templateUrl: './renda-variavel-details.component.html',
    styleUrls: ['./renda-variavel-details.component.scss']
})
export class RendaVariavelDetailsComponent implements OnInit {

    displayedColumns = ['codigo', 'valor'];
    dataSource = new MatTableDataSource();
    details = [];

    codigo = '';
    data = '';

    produtoRV = new ProdutoRV();

    @ViewChild('evolucao', {static: true}) evolucao: LineChartComponent;
    @ViewChild('rentabilidade', {static: true}) rentabilidade: LineChartComponent;

    constructor(private activatedRoute: ActivatedRoute, private httpService: HttpService) { }

    ngOnInit(): void {

        this.codigo = this.activatedRoute.snapshot.params.codigo;
        this.data = this.activatedRoute.snapshot.params.data;

        this.httpService.get<ProdutoRV>(`produtos/renda-variavel/${this.codigo}?date=${this.data}`).subscribe( response => {
            console.log(response);

            const keys = Object.keys(this.produtoRV.getTableInfo());
            keys.forEach (it => {
                this.details.push(new CodigoValor( this.produtoRV.getTableInfo()[it].name, response[it]));
            });
            this.dataSource = new MatTableDataSource(this.details);

        });
        this.update();
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
