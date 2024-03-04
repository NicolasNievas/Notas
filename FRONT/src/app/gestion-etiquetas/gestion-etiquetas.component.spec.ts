import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionEtiquetasComponent } from './gestion-etiquetas.component';

describe('GestionEtiquetasComponent', () => {
  let component: GestionEtiquetasComponent;
  let fixture: ComponentFixture<GestionEtiquetasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GestionEtiquetasComponent]
    });
    fixture = TestBed.createComponent(GestionEtiquetasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
