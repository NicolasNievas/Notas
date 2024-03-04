import { Component, OnInit } from '@angular/core';
import { NoteResponse, TagResponse } from '../models/note.models';
import { RestServicesService } from '../services/rest-services.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-listar-notas',
  templateUrl: './listar-notas.component.html',
  styleUrls: ['./listar-notas.component.css'],
})
export class ListarNotasComponent implements OnInit {
  notas: NoteResponse[] = [];
  showAddTagsForm: boolean = false;
  showRemoveTagsForm: boolean = false;
  selectedNote: NoteResponse | null = null;
  selectedTags: TagResponse[] = []; 

  constructor(private restService: RestServicesService, private router: Router) {}

  ngOnInit(): void {
    this.obtenerNotas();
  }

  obtenerNotas(): void {
    this.restService.getAllNotes().subscribe(
      (notas: NoteResponse[]) => {
        this.notas = notas;
      },
      (error) => {
        alert('Error al obtener las notas');
        console.error('Error al obtener las notas:', error);
      }
    );
  }

  toggleArchiveStatus(note: NoteResponse): void {
    if (note.active) {
      this.restService.archiveNote(note.id).subscribe(() => {
        this.obtenerNotas();
      });
    } else {
      this.restService.unarchiveNote(note.id).subscribe(() => {
        this.obtenerNotas();
      });
    }
  }

  deleteNote(noteId: number): void {
    this.restService.deleteNote(noteId).subscribe(() => {
      this.obtenerNotas();
    });
  }

  gestionarEtiqueta(noteId: number): void {
    this.router.navigate(['/gestion-etiquetas', noteId]);
  }
editarNota(id: number): void {
  this.router.navigate(['/editar', id]);
}

}
