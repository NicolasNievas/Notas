import { Component, OnInit } from '@angular/core';
import { NoteResponse } from '../models/note.models';
import { RestServicesService } from '../services/rest-services.service';


@Component({
  selector: 'app-listar-notas-activas',
  templateUrl: './listar-notas-activas.component.html',
  styleUrls: ['./listar-notas-activas.component.css']
})
export class ListarNotasActivasComponent implements OnInit{
  activeNotes: NoteResponse[] = [];

  constructor(private noteService: RestServicesService) {}

  ngOnInit() {
    this.loadActiveNotes();
  }

  loadActiveNotes(): void {
    this.noteService.getActiveNotes().subscribe(
      (activeNotes) => {
        this.activeNotes = activeNotes;
      },
      (error) => {
        console.error('Error al cargar notas activas:', error);
        // Swal.fire({
        //   icon: 'error',
        //   title: 'Error',
        //   text: 'No se pudieron cargar las notas activas. Inténtelo de nuevo más tarde.'
        // });
      }
    );
  }
  }

