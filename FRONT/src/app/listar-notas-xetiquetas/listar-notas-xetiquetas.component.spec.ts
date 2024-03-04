import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarNotasXEtiquetasComponent } from './listar-notas-xetiquetas.component';

describe('ListarNotasXEtiquetasComponent', () => {
  let component: ListarNotasXEtiquetasComponent;
  let fixture: ComponentFixture<ListarNotasXEtiquetasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListarNotasXEtiquetasComponent]
    });
    fixture = TestBed.createComponent(ListarNotasXEtiquetasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
