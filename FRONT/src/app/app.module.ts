import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateNoteComponent } from './create-note/create-note.component';
import { ListarNotasComponent } from './listar-notas/listar-notas.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { ListarNotasActivasComponent } from './listar-notas-activas/listar-notas-activas.component';
import { ListarNotasArchivadasComponent } from './listar-notas-archivadas/listar-notas-archivadas.component';
import { ListarNotasXEtiquetasComponent } from './listar-notas-xetiquetas/listar-notas-xetiquetas.component';
import { GestionEtiquetasComponent } from './gestion-etiquetas/gestion-etiquetas.component';
import { UpdateNoteComponent } from './update-note/update-note.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateNoteComponent,
    ListarNotasComponent,
    ListarNotasActivasComponent,
    ListarNotasArchivadasComponent,
    ListarNotasXEtiquetasComponent,
    GestionEtiquetasComponent,
    UpdateNoteComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
