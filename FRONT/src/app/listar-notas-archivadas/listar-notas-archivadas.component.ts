import { Component, OnInit } from '@angular/core';
import { RestServicesService } from '../services/rest-services.service';
import { NoteResponse } from '../models/note.models';

@Component({
  selector: 'app-listar-notas-archivadas',
  templateUrl: './listar-notas-archivadas.component.html',
  styleUrls: ['./listar-notas-archivadas.component.css']
})
export class ListarNotasArchivadasComponent implements OnInit{
  public notasArchivadas: NoteResponse[] = [];

  constructor(private restService: RestServicesService) {}

  ngOnInit(): void {
    this.obtenerNotasArchivadas();
  }

  obtenerNotasArchivadas(): void {
    this.restService.getArchivedNotes().subscribe(
      (data) => {
        this.notasArchivadas = data;
      },
      (error) => {
        alert('Error al obtener las notas archivadas');
        console.error('Error al obtener las notas archivadas:', error);
      }
    );
  }
}
