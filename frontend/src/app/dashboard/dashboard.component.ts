import { Component, OnInit, ViewChild } from '@angular/core';
import { BarChartComponent } from './bar-chart/bar-chart.component';
import { PieChartComponent } from './pie-chart/pie-chart.component';
import { SummaryComponent } from './summary/summary.component';
import { StackedBarChartComponent } from './stacked-bar-chart/stacked-bar-chart.component';
import { LineChartComponent } from './line-chart/line-chart.component';
import { HttpService } from '../services/http.service';

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
      const offset = this.date.getTimezoneOffset();
      this.date = new Date(this.date.getTime() + (offset * 60 * 1000));
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
          console.log(resp);
          this.rentabilidade.update(resp.data, resp.labels, resp.series);
        });

        this.httpService.get<any>(`portfolio/resumo?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
          this.summary.update(resp.variacao, resp.patrimonioTotal);
          this.proporcao.update(resp.proporcao.valores, resp.proporcao.legendas);
        });
      }
    }
}
