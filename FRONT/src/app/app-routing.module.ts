import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateNoteComponent } from './create-note/create-note.component';
import { ListarNotasComponent } from './listar-notas/listar-notas.component';
import { ListarNotasActivasComponent } from './listar-notas-activas/listar-notas-activas.component';
import { ListarNotasArchivadasComponent } from './listar-notas-archivadas/listar-notas-archivadas.component';
import { ListarNotasXEtiquetasComponent } from './listar-notas-xetiquetas/listar-notas-xetiquetas.component';
import { GestionEtiquetasComponent } from './gestion-etiquetas/gestion-etiquetas.component';
import { UpdateNoteComponent } from './update-note/update-note.component';

const routes: Routes = [
  { path: 'crear-nota', component: CreateNoteComponent },
  { path: 'listar', component: ListarNotasComponent },
  { path: 'listar-archivadas', component: ListarNotasArchivadasComponent },
  { path: 'gestion-etiquetas/:id', component: GestionEtiquetasComponent },
  { path: 'listar-activas', component: ListarNotasActivasComponent },
  { path: 'listar-etiqueta', component: ListarNotasXEtiquetasComponent },
  { path: 'editar/:id', component: UpdateNoteComponent },
  { path: '', redirectTo: '/listar', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
