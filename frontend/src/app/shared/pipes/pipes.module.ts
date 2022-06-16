import { CommonModule, DecimalPipe, DatePipe, PercentPipe } from '@angular/common';
import { NgModule } from '@angular/core';
import { DynamicPipe } from './dynamic.pipe';

@NgModule({
    declarations: [
        DynamicPipe
    ],
    imports: [
        CommonModule,
    ],
    exports: [
        DynamicPipe
    ],
    providers: [
        DecimalPipe,
        DatePipe,
        PercentPipe,
    ]
})
export class PipesModule {}
