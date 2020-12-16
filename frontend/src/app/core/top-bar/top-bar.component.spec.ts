import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopBarComponent } from './top-bar.component';
import { LoaderComponent } from '../loader/loader.component';
import { RouterTestingModule } from '@angular/router/testing';
import { DemoMaterialModule } from 'src/app/material-module';

describe('TopBarComponent', () => {
  let component: TopBarComponent;
  let fixture: ComponentFixture<TopBarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        TopBarComponent, 
        LoaderComponent 
      ],
      imports: [
        RouterTestingModule,
        DemoMaterialModule
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should contain menu Investimentos',  () => {
    const element: HTMLElement = fixture.debugElement.nativeElement;
    const nav = element.querySelector('nav');
    expect(nav.textContent).toContain('Investimentos');
  })
});
