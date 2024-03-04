import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarNotasActivasComponent } from './listar-notas-activas.component';

describe('ListarNotasActivasComponent', () => {
  let component: ListarNotasActivasComponent;
  let fixture: ComponentFixture<ListarNotasActivasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ListarNotasActivasComponent]
    });
    fixture = TestBed.createComponent(ListarNotasActivasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
