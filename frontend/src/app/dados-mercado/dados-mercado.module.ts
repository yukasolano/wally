import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../material-module';

import { DadosMercadoComponent } from './dados-mercado.component';
import { TabelaBasicaModule } from '../shared/tabela-basica/tabela-basica.module';

@NgModule({
    declarations: [
      DadosMercadoComponent,
    ],
    imports: [
        CommonModule,
        DemoMaterialModule,
        TabelaBasicaModule
    ],
    exports: [
    ],
})
export class DadosMercadoModule {}
