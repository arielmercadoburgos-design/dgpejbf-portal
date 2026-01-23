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

          <div class="d-flex align-items-center" style="gap: 8px;">
            <span class="badge bg-light text-success">RUC Empresa: {{ ruc }}</span>
            <span class="badge bg-light text-success">Razón Social: {{ razonSocial || '-' }}</span>
          </div>
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
                  <th class="text-start ps-4">Nombres y Apellidos</th>
                  <th class="text-end">Número de Documento</th>
                  <th class="text-end">Cantidad de Acciones</th>
                  <th class="text-center">Valor de Acciones</th>
                  <th class="text-end">Cantidad de Votos</th>
                </tr>
              </thead>

              <tbody>
                <tr *ngFor="let s of socios()">
                  <td class="ps-4 text-start">{{ s.nombre }}</td>

                  <td class="text-end text-muted">{{ s.cedula }}</td>

                  <td class="text-end">{{ s.cantidadAcciones }}</td>

                  <td class="text-center">{{ s.valorAcciones }}</td>

                  <td class="text-end">{{ s.cantidadVotos }}</td>
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
  razonSocial: string | null = null;
  socios = signal<any[]>([]);
  loading = signal(false);

  constructor(
    private route: ActivatedRoute,
    private sociosService: PejRaActualSociosService,
  ) {}

  ngOnInit(): void {
    this.ruc = this.route.snapshot.paramMap.get('ruc');
    this.razonSocial = this.route.snapshot.queryParamMap.get('razonSocial');

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
