import { Component, OnInit, signal } from '@angular/core'; // Agregamos signal
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';

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
                  <th>Nacionalidad</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let d of directivos()">
                  <td class="fw-bold">{{ d.nombre }} {{ d.apellido }}</td>
                  <td>
                    <span class="badge bg-info-emphasis">{{ d.cargo }}</span>
                  </td>
                  <td>{{ d.nroDocumento }}</td>
                  <td>{{ d.nacionalidad }}</td>
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
  ruc: string | null = null;
  directivos = signal<any[]>([]); // Para almacenar los datos de portal_dgpejbf.pej_ra_actual_directivo
  loading = signal(false);

  constructor(
    private route: ActivatedRoute,
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

    // Simulación de carga para que veas cómo queda:
    setTimeout(() => {
      this.loading.set(false);
    }, 800);
  }
}
