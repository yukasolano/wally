import { Component, OnInit, ViewChild } from '@angular/core';
import { SummaryComponent } from './summary/summary.component';
import { HttpService } from '../services/http.service';
import { PieChartComponent } from '../shared/graficos/pie-chart/pie-chart.component';
import { BarChartComponent } from '../shared/graficos/bar-chart/bar-chart.component';
import { StackedBarChartComponent } from '../shared/graficos/stacked-bar-chart/stacked-bar-chart.component';
import { LineChartComponent } from '../shared/graficos/line-chart/line-chart.component';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss']
  })
  export class DashboardComponent implements OnInit {

    constructor(private httpService: HttpService) { }

    date = new Date();

    @ViewChild('summary', {static: true}) summary: SummaryComponent;
    @ViewChild('proporcao', {static: true}) proporcao: PieChartComponent;
    @ViewChild('proporcaoRV', {static: true}) proporcaoRV: BarChartComponent;
    @ViewChild('instituicoes', {static: true}) instituicoes: BarChartComponent;
    @ViewChild('liquidez', {static: true}) liquidez: BarChartComponent;
    @ViewChild('dividendos', {static: true}) dividendos: StackedBarChartComponent;
    @ViewChild('evolucao', {static: true}) evolucao: LineChartComponent;
    @ViewChild('rentabilidade', {static: true}) rentabilidade: LineChartComponent;


    dateChanged() {
      this.update();
    }

    ngOnInit() {
      this.update();
    }


    update() {
      if (this.date) {

        this.httpService.get<any>(`portfolio/liquidez?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
          this.liquidez.update(resp.valores, resp.legendas);
        });

        this.httpService.get<any>(`portfolio/instituicoes?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
          this.instituicoes.update(resp.valores, resp.legendas);
        });

        this.httpService.get<any>(`portfolio/dividendos?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
          this.dividendos.update(resp.data, resp.labels, resp.series);
        });

        this.httpService.get<any>(`portfolio/evolucao?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
          this.evolucao.update(resp.data, resp.labels, resp.series);
        });

        this.httpService.get<any>(`portfolio/rentabilidade?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
          this.rentabilidade.update(resp.data, resp.labels, resp.series);
        });

        this.httpService.get<any>(`portfolio/resumo?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
          this.summary.update(resp.variacao, resp.patrimonioTotal);
          this.proporcao.update(resp.proporcao.valores, resp.proporcao.legendas);
        });
      }
    }
}
