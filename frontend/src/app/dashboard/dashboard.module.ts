import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DemoMaterialModule } from '../material-module';

import { SummaryComponent } from './summary/summary.component';
import { DashboardComponent } from './dashboard.component';
import { PipesModule } from '../shared/pipes/pipes.module';
import { GraficosModule } from '../shared/graficos/graficos.module';

@NgModule({
    declarations: [
        SummaryComponent,
        DashboardComponent
    ],
    imports: [
        CommonModule,
        DemoMaterialModule,
        FormsModule,
        PipesModule,
        GraficosModule,
    ],
    exports: [
    ],
})
export class DashboardModule {}
