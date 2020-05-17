import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
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
        this.httpService.get<any>(`portfolio-graficos?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
          console.log(resp);
          this.summary.update(resp.variacao, resp.patrimonioTotal);
          this.proporcao.update(resp.proporcao.valores, resp.proporcao.legendas);
          this.proporcaoRV.update(resp.proporcaoRV.valores, resp.proporcaoRV.legendas);
          this.instituicoes.update(resp.instituicoes.valores, resp.instituicoes.legendas);
          this.liquidez.update(resp.liquidez.valores, resp.liquidez.legendas);
          this.dividendos.update(resp.dividendos.data, resp.dividendos.labels, resp.dividendos.series);
          this.evolucao.update(resp.evolucao.data, resp.evolucao.labels, resp.evolucao.series);
        });
      }
    }
}
