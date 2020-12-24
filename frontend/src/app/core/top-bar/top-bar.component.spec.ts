import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopBarComponent } from './top-bar.component';
import { LoaderComponent } from '../loader/loader.component';
import { DemoMaterialModule } from 'src/app/material-module';
import { Directive, Input, HostListener, DebugElement } from '@angular/core';
import { By } from '@angular/platform-browser';

@Directive({selector: '[routerLink]'})
export class RouterLinkDirectiveStub {
  @Input('routerLink') linkParams: any;

  navigatedTo: any = null;

  @HostListener('click')
  onClick() {
    this.navigatedTo = this.linkParams;
  }
}


describe('TopBarComponent', () => {
  let component: TopBarComponent;
  let fixture: ComponentFixture<TopBarComponent>;
  let routerLinks: RouterLinkDirectiveStub[];
  let linkDes: DebugElement[];

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        TopBarComponent, 
        LoaderComponent ,
        RouterLinkDirectiveStub
      ],
      imports: [
        DemoMaterialModule
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    linkDes = fixture.debugElement.queryAll(By.directive(RouterLinkDirectiveStub))
    routerLinks = linkDes.map(de => de.injector.get(RouterLinkDirectiveStub))
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('can get RouterLink from template', () => {
    expect(routerLinks.length).toBe(5, 'should have 5 routerLinks');
    expect(routerLinks[1].linkParams).toEqual(['dashboard']);
  }) 


  it('can click investimento link in template',  () => {
    const dashboardLinkDe = linkDes[1];
    const dashboardLink = routerLinks[1];
    expect(dashboardLink.navigatedTo).toBeNull('should not have navigate yet');
    dashboardLinkDe.triggerEventHandler('click', null);
    fixture.detectChanges();
    expect(dashboardLink.navigatedTo).toEqual(['dashboard']);
  })

  it('should contain menu Investimentos',  () => {
    const element: HTMLElement = fixture.debugElement.nativeElement;
    const nav = element.querySelector('nav');
    expect(nav.textContent).toContain('Investimentos');
  })
});
