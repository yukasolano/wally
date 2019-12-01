import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-pie-chart',
    templateUrl: './pie-chart.component.html',
    styleUrls: ['./pie-chart.component.scss']
  })
  export class PieChartComponent implements OnInit {

    @Input() titulo;

    public chartType = 'doughnut';
    public chartLabels = [];
    public chartData = [];

    constructor() {}

    ngOnInit() {}

    update(dados: number[], legendas?: string[]) {
        this.chartLabels = legendas;
        this.chartData = dados;
    }

    temDados() {
        return this.chartData.length > 0;
    }
}
