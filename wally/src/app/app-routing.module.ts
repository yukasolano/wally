import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ProductsComponent } from './products/products.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { DadosMercadoComponent } from './dados-mercado/dados-mercado.component';


const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'produtcs', component: ProductsComponent },
  { path: 'cadastro', component: CadastroComponent },
  { path: 'dados-mercado', component: DadosMercadoComponent },
  { path: '', redirectTo: '/dashboard', pathMatch: 'full'},
  { path: '**', component: ProductsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
