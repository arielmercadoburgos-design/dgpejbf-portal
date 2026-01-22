import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { PejRaActualSociosService } from './pej-ra-actual-socios.service'; // Asegúrate de crear este servicio
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

// Define ISocios interface (adjust fields as needed)
export interface ISocios {
  nombre: string;
  cedula?: string;
  rucSocio?: string;
  cantidadAcciones: number;
  porcentaje: number;
  cantidadVotos: number;
  profesion?: string;
}

@Component({
  selector: 'jhi-pej-ra-actual-socios-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FontAwesomeModule],
  template: `
    <div class="container mt-4">
      <div class="card shadow-lg border-0">
        <div class="card-header bg-success text-white d-flex justify-content-between align-items-center">
          <h4 class="mb-0">Detalle de Socios / Accionistas</h4>
          <span class="badge bg-light text-success">RUC Empresa: {{ ruc }}</span>
        </div>
        <div class="card-body p-4">
          <div *ngIf="loading()" class="text-center my-4">
            <div class="spinner-border text-success" role="status"></div>
            <p>Cargando socios...</p>
          </div>

          <div class="table-responsive" *ngIf="!loading() && socios().length > 0">
            <table class="table table-hover align-middle">
              <thead class="table-light">
                <tr>
                  <th>NOMBRES Y APELLIDOS</th>
                  <th>NUMERO DE DOCUMENTO</th>
                  <th class="text-center">Acciones</th>
                  <th class="text-center">% Part.</th>
                  <th class="text-center">Votos</th>
                  <th>Profesión</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngFor="let s of socios()">
                  <td class="text-dark fw-medium">{{ s.nombre }}</td>
                  <td class="text-muted">{{ s.cedula || s.rucSocio }}</td>
                  <td class="text-center">{{ s.cantidadAcciones }}</td>
                  <td class="text-center">
                    <span class="badge rounded-pill bg-info text-dark">{{ s.porcentaje }}%</span>
                  </td>
                  <td class="text-center">{{ s.cantidadVotos }}</td>
                  <td class="text-muted small">{{ s.profesion }}</td>
                </tr>
              </tbody>
            </table>
          </div>

          <div *ngIf="!loading() && socios().length === 0" class="alert alert-info text-center">
            No se encontraron socios registrados para este RUC.
          </div>

          <div class="mt-4">
            <button class="btn btn-outline-secondary" [routerLink]="['/pej-ra-actual']">
              <fa-icon icon="arrow-left"></fa-icon> Volver a la lista
            </button>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class PejRaActualSociosListComponent implements OnInit {
  ruc: string | null = null;
  socios = signal<any[]>([]);
  loading = signal(false);

  constructor(
    private route: ActivatedRoute,
    private sociosService: PejRaActualSociosService,
  ) {}

  ngOnInit(): void {
    this.ruc = this.route.snapshot.paramMap.get('ruc');
    if (this.ruc) {
      this.cargarSocios(this.ruc);
    }
  }

  cargarSocios(ruc: string): void {
    this.loading.set(true);
    this.sociosService.findByRuc(Number(ruc)).subscribe({
      next: (res: ISocios[]) => {
        this.socios.set(res);
        this.loading.set(false);
      },
      error: err => {
        console.error('Error cargando socios:', err);
        this.loading.set(false);
        this.socios.set([]);
      },
    });
  }
}
