import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-stacked-bar-chart',
    templateUrl: './stacked-bar-chart.component.html',
    styleUrls: ['./stacked-bar-chart.component.scss']
  })
  export class StackedBarChartComponent implements OnInit {

    @Input() titulo;

    public barChartOptions = {
      scaleShowVerticalLines: false,
      responsive: true,
      legend: {
        display: false
     },
      scales: {
        xAxes: [{ stacked: true }],
        yAxes: [{ stacked: true }]
      }
    };
    public barChartType;
    public barChartLegend = true;
    public barChartLabels = [];
    public barChartData = [];


    constructor() {}

    ngOnInit() {
      this.barChartType = 'bar';
    }

    update(dados: number[][], legendas?: string[], series?: string[]) {
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
