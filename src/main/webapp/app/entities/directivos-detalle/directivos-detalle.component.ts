import { Component, OnInit, signal } from '@angular/core'; // Agregamos signal
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { PejRaActualDirectivoService } from './directivos-detalle-directivo.service';

@Component({
  selector: 'jhi-directivos-detalle',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="container mt-4">
      <div class="card shadow-lg border-0">
        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
          <h4 class="mb-0">Detalle de Directivos</h4>
          <span class="badge bg-light text-primary">RUC: {{ ruc }}</span>
        </div>
        <div class="card-body p-4">
          <div *ngIf="loading()" class="text-center my-4">
            <div class="spinner-border text-primary" role="status"></div>
            <p>Cargando directivos...</p>
          </div>

          <div class="table-responsive" *ngIf="!loading() && directivos().length > 0">
            <table class="table table-hover align-middle">
              <thead class="table-light">
                <tr>
                  <th>Nombre y Apellido</th>
                  <th>Cargo</th>
                  <th>Documento</th>
                  <th>Fecha de Asunción</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let d of directivos()">
                  <td class="ps-4 text-dark">{{ d.nombre }}</td>
                  <td>
                    <span class="text-primary fw-medium">{{ d.cargo }}</span>
                  </td>
                  <td class="text-muted">{{ d.cedula }}</td>
                  <td class="text-muted">{{ d.fechaAsuncion }}</td>
                </tr>
              </tbody>
            </table>
          </div>

          <div *ngIf="!loading() && directivos().length === 0" class="alert alert-warning text-center">
            No se encontraron directivos registrados para este RUC.
          </div>

          <div class="mt-4">
            <button class="btn btn-outline-secondary" [routerLink]="['/pej-ra-actual']">
              <i class="fa fa-arrow-left"></i> Volver a la lista
            </button>
          </div>
        </div>
      </div>
    </div>
  `, // <--- Corregido el cierre del template
})
export class DirectivosDetalleComponent implements OnInit {
  standalone = true;
  imports = [CommonModule, RouterModule];
  ruc: string | null = null;
  directivos = signal<any[]>([]); // Para almacenar los datos de portal_dgpejbf.pej_ra_actual_directivo
  loading = signal(false);

  constructor(
    private route: ActivatedRoute,
    private directivoService: PejRaActualDirectivoService,
    // private service: PejRaActualService // Inyecta tu servicio aquí
  ) {}

  ngOnInit(): void {
    this.ruc = this.route.snapshot.paramMap.get('ruc');
    if (this.ruc) {
      this.cargarDirectivos(this.ruc);
    }
  }

  cargarDirectivos(ruc: string): void {
    this.loading.set(true);

    // Aquí llamas a tu servicio para obtener los directivos por RUC
    this.directivoService.findByRuc(Number(ruc)).subscribe({
      next: (res: any[]) => {
        this.directivos.set(res);
        this.loading.set(false);
      },
      error: err => {
        console.error('Error cargando directivos:', err);
        this.loading.set(false);
        this.directivos.set([]); // Limpiar en caso de error
      },
    });
  }
}
