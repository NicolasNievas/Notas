import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarNotasArchivadasComponent } from './listar-notas-archivadas.component';

describe('ListarNotasArchivadasComponent', () => {
  let component: ListarNotasArchivadasComponent;
  let fixture: ComponentFixture<ListarNotasArchivadasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListarNotasArchivadasComponent]
    });
    fixture = TestBed.createComponent(ListarNotasArchivadasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
