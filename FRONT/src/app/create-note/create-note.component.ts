import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RestServicesService } from '../services/rest-services.service';
import { NoteRequest, NoteResponse, TagResponse } from '../models/note.models';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-note',
  templateUrl: './create-note.component.html',
  styleUrls: ['./create-note.component.css'],
})
export class CreateNoteComponent {
  crearNotaForm: FormGroup = this.fb.group({});

  notes: NoteResponse[] = [];
  newNote: NoteRequest = { content: '', tags: [] };
  etiquetas: TagResponse[] = [];


  constructor(
    private restService: RestServicesService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit() {
    this.crearNotaForm = this.fb.group({
      contenido: ['', [Validators.required, Validators.minLength(3)]],
      etiqueta: [''],
    });
    this.restService.getAllTags().subscribe(
      (etiquetas) => {
        this.etiquetas = etiquetas;
      },
      (error) => {
        console.error('Error al obtener las etiquetas:', error);
      }
    );
  }

  Guardar(): void {
    if (!this.crearNotaForm) {
      return;
    }
    if (this.crearNotaForm.invalid) {
      return;
    }

    const contenidoControl = this.crearNotaForm.get('contenido');
    const etiquetaControl = this.crearNotaForm.get('etiqueta');

    if (contenidoControl && etiquetaControl) {
      this.newNote.content = contenidoControl.value;

      const etiqueta = etiquetaControl.value;

      if (etiqueta) {
        this.newNote.tags = [{ name: etiqueta }];
      }

      this.restService.createNoteRequest(this.newNote).subscribe(
        (createdNote) => {
          this.notes.push(createdNote);
          this.newNote = { content: '', tags: [] };
          this.crearNotaForm.reset();
          this.router.navigate(['/listar']);
        },
        (error) => {
          alert('Error al crear la nota');
          console.error('Error al crear la nota:', error);
        }
      );
    }
  }
}
