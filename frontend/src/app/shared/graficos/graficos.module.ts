import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from 'src/app/material-module';
import { BarChartComponent } from './bar-chart/bar-chart.component';
import { StackedBarChartComponent } from './stacked-bar-chart/stacked-bar-chart.component';
import { LineChartComponent } from './line-chart/line-chart.component';
import { PieChartComponent } from './pie-chart/pie-chart.component';
import { ChartsModule } from 'ng2-charts';
import { PipesModule } from '../pipes/pipes.module';

@NgModule({
    declarations: [
        BarChartComponent,
        PieChartComponent,
        StackedBarChartComponent,
        LineChartComponent,
    ],
    imports: [
        CommonModule,
        DemoMaterialModule,
        ChartsModule,
        PipesModule
    ],
    exports: [
        BarChartComponent,
        PieChartComponent,
        StackedBarChartComponent,
        LineChartComponent,
    ]
})
export class GraficosModule {}
