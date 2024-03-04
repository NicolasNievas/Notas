import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NoteResponse, TagResponse } from '../models/note.models';
import { RestServicesService } from '../services/rest-services.service';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'app-gestion-etiquetas',
  templateUrl: './gestion-etiquetas.component.html',
  styleUrls: ['./gestion-etiquetas.component.css'],
})
export class GestionEtiquetasComponent implements OnInit {
  noteId: number = 0;
  note: NoteResponse | null = null;
  allTags: TagResponse[] = [];
  selectedTags: TagResponse[] = [];
  isOperationPending: boolean = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private restService: RestServicesService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      this.noteId = +params['id'];
      this.loadNoteAndTags();
    });
  }

   loadNoteAndTags(): void {
    this.restService.getNoteById(this.noteId).subscribe(
      (note: NoteResponse) => {
        this.note = note;
      },
      (error) => {
        alert('Error al obtener la nota');
        console.error('Error al obtener la nota:', error);
      }
    );

    this.restService.getAllTags().subscribe(
      (tags: TagResponse[]) => {
        this.allTags = tags;
      },
      (error) => {
        alert('Error al obtener las etiquetas');
        console.error('Error al obtener las etiquetas:', error);
      }
    );
  }

  goBackToList(): void {
    this.router.navigate(['/listar']);
  }

  addTags(note: NoteResponse): void {
    if (this.note && this.selectedTags.length > 0) {
      this.isOperationPending = true;
  
      this.restService
        .addTagsToNote(this.note.id, this.selectedTags)
        .pipe(finalize(() => (this.isOperationPending = false)))
        .subscribe(
          () => {
            this.loadNoteAndTags();
            console.log('Etiquetas añadidas a la nota');
          },
          (error) => {
            console.error('Error al agregar etiquetas:', error);
            console.error('Full error response:', error.error);
          }
        );
    } else {
      console.log('No se seleccionaron etiquetas');
    }
  }
  removeTags(note: NoteResponse): void {
    if (this.note && this.selectedTags.length > 0) {
      this.isOperationPending = true;
    
      this.restService
        .removeTagsFromNote(this.note.id, this.selectedTags)
        .pipe(finalize(() => (this.isOperationPending = false)))
        .subscribe(
          () => {
            this.loadNoteAndTags();
            console.log('Etiquetas eliminadas de la nota');
          },
          (error) => {
            console.error('Error al eliminar etiquetas:', error);
            console.error('Full error response:', error.error);
          }
        );
    } else {
      console.log('No se seleccionaron etiquetas');
    }
  }
  
  
}
