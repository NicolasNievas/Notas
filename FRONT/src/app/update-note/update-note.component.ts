import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { RestServicesService } from '../services/rest-services.service';

@Component({
  selector: 'app-update-note',
  templateUrl: './update-note.component.html',
  styleUrls: ['./update-note.component.css'],
})
export class UpdateNoteComponent implements OnInit {
  form: FormGroup = this.fb.group({});
  notaAnterior: string | null = null;

  constructor(
    private fb: FormBuilder,
    private restService: RestServicesService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      notaAnterior: [{ value: '', disabled: true }],
      nuevaNota: ['', [Validators.required, Validators.minLength(3)]],
    });

    const noteId = this.obtenerIdDeLaRuta();

    this.restService.getNoteById(noteId).subscribe(
      (nota) => {
        this.notaAnterior = nota.content;
        this.form.get('notaAnterior')?.setValue(this.notaAnterior);
      },
      (error) => {
        console.error('Error al obtener la nota anterior:', error);
      }
    );
  }

  update(): void {
    if (this.form && this.form.valid && this.notaAnterior !== null) {
      const nuevaNota = this.form.get('nuevaNota')?.value;
      const noteId = this.obtenerIdDeLaRuta();
  
      if (nuevaNota !== null) {
        this.restService.updateNote(noteId, { content: nuevaNota }).subscribe(
          () => {
            this.router.navigate(['/listar']);
          },
          (error) => {
            console.error('Error al actualizar la nota:', error);
          }
        );
      }
    }
  }

  private obtenerIdDeLaRuta(): number {
    return +this.route.snapshot.paramMap.get('id')!;
  }
}
