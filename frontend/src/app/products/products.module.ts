import { CommonModule } from '@angular/common';
import { DemoMaterialModule } from '../material-module';
import { NgModule } from '@angular/core';
import { ProductsComponent } from './products.component';
import { TabelaProdutosComponent } from './tabela-produtos/tabela-produtos.component';
import { RendaFixaDetailsComponent } from './renda-fixa-details/renda-fixa-details.component';
import { RendaVariavelDetailsComponent } from './renda-variavel-details/renda-variavel-details.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AppRoutingModule } from '../app-routing.module';
import { GraficosModule } from '../shared/graficos/graficos.module';
import { PipesModule } from '../shared/pipes/pipes.module';

@NgModule({
    declarations: [
        ProductsComponent,
        TabelaProdutosComponent,
        RendaFixaDetailsComponent,
        RendaVariavelDetailsComponent
    ],
    imports: [
        CommonModule,
        DemoMaterialModule,
        ReactiveFormsModule,
        AppRoutingModule,
        FormsModule,
        GraficosModule,
        PipesModule
    ],
    exports: [

    ],
})
export class ProductsModule {}
