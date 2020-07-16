import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../material-module';
import { NgModule } from '@angular/core';
import { TabelaBasicaModule } from '../shared/tabela-basica/tabela-basica.module';
import { ReactiveFormsModule } from '@angular/forms';
import { CadastroComponent } from './cadastro.component';
import { MaterialFileInputModule } from 'ngx-material-file-input';

@NgModule({
    declarations: [
        CadastroComponent
    ],
    imports: [
        CommonModule,
        DemoMaterialModule,
        TabelaBasicaModule,
        ReactiveFormsModule,
        MaterialFileInputModule,
    ],
    exports: [
    ],
})
export class CadastroModule {}
