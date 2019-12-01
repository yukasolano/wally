import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-bar-chart',
    templateUrl: './bar-chart.component.html',
    styleUrls: ['./bar-chart.component.scss']
  })
  export class BarChartComponent implements OnInit {

    @Input() titulo;

    public barChartOptions = {
      scaleShowVerticalLines: false,
      responsive: true
    };
    public barChartType = 'horizontalBar';
    public barChartLegend = true;
    public barChartLabels = [];
    public barChartData = [];

    constructor() {}

    ngOnInit() {}

    update(dados: number[], legendas?: string[]) {
        this.barChartLabels = legendas;
        this.barChartData = [{data: dados, label: this.titulo}];
    }

    temDados() {
        return this.barChartData.length > 0;
    }
}
