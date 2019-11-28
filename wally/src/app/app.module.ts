import { registerLocaleData, DecimalPipe, CommonModule, DatePipe, PercentPipe } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DemoMaterialModule } from './material-module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProductsComponent } from './products/products.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { HttpClientModule } from '@angular/common/http';
import { DynamicPipe } from './dynamic.pipe';

import localePt from '@angular/common/locales/pt';
import { TabelaProdutosComponent } from './products/tabela-produtos/tabela-produtos.component';

registerLocaleData(localePt, 'pt');

@NgModule({
  declarations: [
    AppComponent,
    TopBarComponent,
    DynamicPipe,
    DashboardComponent,
    ProductsComponent,
    CadastroComponent,
    TabelaProdutosComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    DemoMaterialModule
  ],
  providers: [
    DecimalPipe,
    DatePipe,
    PercentPipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
