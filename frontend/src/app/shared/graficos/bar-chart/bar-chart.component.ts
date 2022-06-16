import { Component, OnInit, Input } from '@angular/core';

@Component({
    selector: 'app-bar-chart',
    templateUrl: './bar-chart.component.html',
    styleUrls: ['./bar-chart.component.scss']
  })
  export class BarChartComponent implements OnInit {

    @Input() titulo;
    @Input() direction = 'horizontal';

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
      this.barChartType = this.direction === 'horizontal' ? 'horizontalBar' : 'bar';
    }

    update(dados: number[], legendas?: string[]) {
        this.barChartLabels = legendas;
        this.barChartData = [{data: dados, label: this.titulo}];
    }

    temDados() {
        return this.barChartLabels && this.barChartLabels.length > 0;
    }
}
