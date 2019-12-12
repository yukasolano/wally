import { registerLocaleData, DecimalPipe, CommonModule, DatePipe, PercentPipe } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts';

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
import { BarChartComponent } from './dashboard/bar-chart/bar-chart.component';
import { PieChartComponent } from './dashboard/pie-chart/pie-chart.component';
import { SummaryComponent } from './dashboard/summary/summary.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MAT_DATE_LOCALE, DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import {
  MAT_MOMENT_DATE_FORMATS,
  MomentDateAdapter,
  MAT_MOMENT_DATE_ADAPTER_OPTIONS,
} from '@angular/material-moment-adapter';
import { MaterialFileInputModule } from 'ngx-material-file-input';

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
    BarChartComponent,
    PieChartComponent,
    SummaryComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    MaterialFileInputModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    DemoMaterialModule,
    BrowserModule,
    ChartsModule
  ],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'pt'},
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
    DecimalPipe,
    DatePipe,
    PercentPipe
  ],
  entryComponents: [
    CadastroComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor(private adapter: DateAdapter<any>) {

    this.adapter.setLocale('pt');
  }
}
