import { NgModule } from '@angular/core';
import { TabelaBasicaComponent } from './tabela-basica.component';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from 'src/app/material-module';
import { PipesModule } from '../pipes/pipes.module';

@NgModule({
    declarations: [
        TabelaBasicaComponent
    ],
    imports: [
        CommonModule,
        DemoMaterialModule,
        PipesModule
    ],
    exports: [
        TabelaBasicaComponent
    ]
})
export class TabelaBasicaModule {}
