import { Component, OnInit } from '@angular/core';
import { RestServicesService } from '../services/rest-services.service';
import { TagResponse, NoteResponse } from '../models/note.models';

@Component({
  selector: 'app-listar-notas-xetiquetas',
  templateUrl: './listar-notas-xetiquetas.component.html',
  styleUrls: ['./listar-notas-xetiquetas.component.css']
})
export class ListarNotasXEtiquetasComponent implements OnInit {
  etiquetas: TagResponse[] = [];
  etiquetaSeleccionada: number = 0;
  notasPorEtiqueta: NoteResponse[] = [];

  constructor(private restService: RestServicesService) { }

  ngOnInit(): void {
    this.obtenerEtiquetas();
  }

  obtenerEtiquetas(): void {
    this.restService.getAllTags().subscribe(
      (etiquetas) => {
        this.etiquetas = etiquetas;
      },
      (error) => {
        alert('Error al obtener las etiquetas');
        console.error('Error al obtener las etiquetas:', error);
      }
    );
  }

  onSelectEtiqueta(): void {
    if (this.etiquetaSeleccionada > 0) {
      this.restService.getNotesByTagId(this.etiquetaSeleccionada).subscribe(
        (notas) => {
          console.log('Notas obtenidas por etiqueta:', notas);
          this.notasPorEtiqueta = notas;
        },
        (error) => {
          alert('Error al obtener notas por etiqueta');
          console.error('Error al obtener notas por etiqueta:', error);
        }
      );
    }
  }
}
