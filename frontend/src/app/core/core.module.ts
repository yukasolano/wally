import { NgModule } from '@angular/core';
import { TopBarComponent } from './top-bar/top-bar.component';
import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { DemoMaterialModule } from '../material-module';
import { LoaderInterceptorService } from './loader/loader-interceptor.service';
import { LoaderComponent } from './loader/loader.component';
import { AppRoutingModule } from '../app-routing.module';

@NgModule({
    declarations: [
      TopBarComponent,
      LoaderComponent
    ],
    imports: [
        CommonModule,
        DemoMaterialModule,
        AppRoutingModule
    ],
    exports: [
        TopBarComponent
    ],
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: LoaderInterceptorService,
            multi: true
        }
    ]
})
export class CoreModule {}
