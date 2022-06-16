import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-line-chart',
    templateUrl: './line-chart.component.html',
    styleUrls: ['./line-chart.component.scss']
  })
  export class LineChartComponent implements OnInit {

    @Input() titulo;


    public barChartOptions = {
      scaleShowVerticalLines: false,
      responsive: true,
      legend: {
        display: false
      }
    };
    public barChartType;
    public barChartLegend = true;
    public barChartLabels = [];
    public barChartData = [];

    constructor() {}

    ngOnInit() {
      this.barChartType = 'line';
    }

    update(dados: number[], legendas?: string[],  series?: string[]) {
      if (dados) {
        this.barChartLabels = legendas;
        this.barChartData = [];
        for ( let i = 0; i < series.length; i++) {
            this.barChartData.push({data: dados[i], label: series[i]});
        }
      }
    }

    temDados() {
        return this.barChartLabels && this.barChartLabels.length > 0;
    }
}
