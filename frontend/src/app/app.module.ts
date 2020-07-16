import { registerLocaleData, DecimalPipe, DatePipe, PercentPipe } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import localePt from '@angular/common/locales/pt';
import { MAT_DATE_LOCALE, DateAdapter, MAT_DATE_FORMATS } from '@angular/material/core';
import {
  MAT_MOMENT_DATE_FORMATS,
  MomentDateAdapter,
  MAT_MOMENT_DATE_ADAPTER_OPTIONS,
} from '@angular/material-moment-adapter';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DemoMaterialModule } from './material-module';
import { CoreModule } from './core/core.module';
import { GraficosModule } from './shared/graficos/graficos.module';
import { ProductsModule } from './products/products.module';
import { DadosMercadoModule } from './dados-mercado/dados-mercado.module';
import { CadastroModule } from './cadastro/cadastro.module';
import { DashboardModule } from './dashboard/dashboard.module';

registerLocaleData(localePt, 'pt');

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    DemoMaterialModule,
    CoreModule,
    GraficosModule,
    ProductsModule,
    DadosMercadoModule,
    CadastroModule,
    DashboardModule
  ],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'pt'},
    {
      provide: DateAdapter,
      useClass: MomentDateAdapter,
      deps: [MAT_DATE_LOCALE, MAT_MOMENT_DATE_ADAPTER_OPTIONS]
    },
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor(private adapter: DateAdapter<any>) {

    this.adapter.setLocale('pt');
  }
}
