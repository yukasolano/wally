import { Component, OnInit, ViewChild } from '@angular/core';
import { SummaryComponent } from './summary/summary.component';
import { HttpService } from '../services/http.service';
import { PieChartComponent } from '../shared/graficos/pie-chart/pie-chart.component';
import { BarChartComponent } from '../shared/graficos/bar-chart/bar-chart.component';
import { StackedBarChartComponent } from '../shared/graficos/stacked-bar-chart/stacked-bar-chart.component';
import { LineChartComponent } from '../shared/graficos/line-chart/line-chart.component';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MatRadioChange } from '@angular/material';
import { ProporcaoComponent } from './proporcao/proporcao.component';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss']
  })
  export class DashboardComponent implements OnInit {

    constructor(private httpService: HttpService, private fb: FormBuilder) { }

    date = new Date();
    formDividendos: FormGroup;

    @ViewChild('summary', {static: true}) summary: SummaryComponent;
    @ViewChild('proporcao', {static: true}) proporcao: PieChartComponent;
    @ViewChild('proporcaoRV', {static: true}) proporcaoRV: BarChartComponent;
    @ViewChild('instituicoes', {static: true}) instituicoes: BarChartComponent;
    @ViewChild('liquidez', {static: true}) liquidez: BarChartComponent;
    @ViewChild('dividendos', {static: true}) dividendos: StackedBarChartComponent;
    @ViewChild('evolucao', {static: true}) evolucao: LineChartComponent;
    @ViewChild('rentabilidade', {static: true}) rentabilidade: LineChartComponent;
    @ViewChild('proporcoes', {static: true}) proporcoes: ProporcaoComponent;


    dateChanged() {
      this.update();
    }

    ngOnInit() {
      this.formDividendos = this.fb.group({
        option: ['TUDO'],
      });
      this.update();
    }

    onChangeDividendoOption($event: MatRadioChange) {
      const tipo = $event.value;
      let url = `portfolio/dividendos?date=${this.date.toISOString().split('T')[0]}`;
      if (tipo !== 'TUDO') {
        url = url + `&tipo=${tipo}`;
      }
      this.httpService.get<any>(url).subscribe( resp => {
        this.dividendos.update(resp.data, resp.labels, resp.series);
      });
    }

    update() {
      if (this.date) {


        this.httpService.get<any>(`portfolio/evolucao?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
          this.evolucao.update(resp.data, resp.labels, resp.series);
          this.httpService.get<any>(`portfolio/rentabilidade?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
            this.rentabilidade.update(resp.data, resp.labels, resp.series);
          });
          this.httpService.get<any>(`portfolio/liquidez?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
            this.liquidez.update(resp.valores, resp.legendas);
          });

          this.httpService.get<any>(`portfolio/instituicoes?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
            this.instituicoes.update(resp.valores, resp.legendas);
          });

          this.httpService.get<any>(`portfolio/dividendos?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
            this.dividendos.update(resp.data, resp.labels, resp.series);
          });
          this.httpService.get<any>(`portfolio/resumo?date=${this.date.toISOString().split('T')[0]}`).subscribe( resp => {
            this.summary.update(resp.variacao, resp.patrimonioTotal);

            for (let i = 0; i < resp.proporcao.legendas.length; i++) {
              const porcentage = resp.proporcao.valores[i] / resp.patrimonioTotal * 100;
              resp.proporcao.legendas[i] = `${resp.proporcao.legendas[i]}: ${porcentage.toFixed(1)}%`;
            }
            this.proporcao.update(resp.proporcao.valores, resp.proporcao.legendas);
            this.proporcoes.update(resp.proporcoes);
          });
        });
      }
    }
}
