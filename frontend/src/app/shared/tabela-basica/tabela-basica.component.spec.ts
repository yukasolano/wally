import { TabelaBasicaComponent } from './tabela-basica.component';
import { ComponentFixture, async, TestBed } from '@angular/core/testing';
import { DemoMaterialModule } from 'src/app/material-module';
import { PipesModule } from '../pipes/pipes.module';
import { DecimalPipe, registerLocaleData } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import localePt from '@angular/common/locales/pt';


registerLocaleData(localePt, 'pt');

class FakeData {
    codigo: string;
    quantidade: number;

    getTableInfo() {
        return {
            codigo: {
                name: 'Código',
            },
            quantidade: {
                name: 'Quantidade',
                pipe: DecimalPipe,
                args: ['0.0-0']
            },
        }
    }
}


class RouterStub {}

const fakeData = [
    {
        codigo: 'test1',
        quantidade: 1
    },
    {
        codigo: 'test2',
        quantidade: 2
    }
]


describe('TabelaBasicaComponent', () => {
  let component: TabelaBasicaComponent;
  let fixture: ComponentFixture<TabelaBasicaComponent>;

  beforeEach(async(() => {
    const httpServiceSpy = jasmine.createSpyObj('HttpClient', ['post']);

    TestBed.configureTestingModule({
      declarations: [ 
        TabelaBasicaComponent
      ],
      imports: [
        DemoMaterialModule,
        PipesModule,
        NoopAnimationsModule
      ],
      providers: [
          {provide: HttpClient, useValue: httpServiceSpy}, 
          {provide: Router, useValue: RouterStub} ,
        ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TabelaBasicaComponent);
    component = fixture.componentInstance;
    component.tableInfo = new FakeData().getTableInfo();
    component.updateData(fakeData);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have fake data', () => {
    expect(component.dataSource.data).toBe(fakeData);
  })

  it('should have headers', () => {
    fixture.detectChanges();
    const ths = fixture.debugElement.nativeElement.querySelectorAll('.mat-header-cell');

    const tableInfo = new FakeData().getTableInfo();
    const cols = Object.keys(tableInfo);
    let index = 0;
    cols.forEach( col => {
      expect(ths[index].textContent).toContain(tableInfo[col].name);
      index++;
    })
  });
});
