import { Component, OnInit, signal } from '@angular/core';
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

          <div class="d-flex align-items-center" style="gap: 8px;">
            <span class="badge bg-light text-primary">RUC: {{ ruc }}</span>
            <span class="badge bg-light text-primary">Razón Social: {{ razonSocial || '-' }}</span>
          </div>
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
  `,
})
export class DirectivosDetalleComponent implements OnInit {
  ruc: string | null = null;
  razonSocial: string | null = null;

  directivos = signal<any[]>([]);
  loading = signal(false);

  constructor(
    private route: ActivatedRoute,
    private directivoService: PejRaActualDirectivoService,
  ) {}

  ngOnInit(): void {
    this.ruc = this.route.snapshot.paramMap.get('ruc');
    if (this.ruc) {
      this.cargarDirectivos(this.ruc);
    }
  }

  cargarDirectivos(ruc: string): void {
    this.loading.set(true);

    this.directivoService.findByRuc(Number(ruc)).subscribe({
      next: (res: any[]) => {
        this.directivos.set(res ?? []);

        // Intentar obtener razón social desde el primer registro (ajusta la clave si tu backend usa otro nombre)
        this.razonSocial =
          res?.[0]?.razonSocial ??
          res?.[0]?.razon_social ??
          res?.[0]?.razonSocialEmpresa ??
          res?.[0]?.empresa ??
          res?.[0]?.denominacion ??
          null;

        this.loading.set(false);

        // Si querés ver qué campos vienen realmente:
        // console.log('primer registro:', res?.[0]);
      },
      error: err => {
        console.error('Error cargando directivos:', err);
        this.loading.set(false);
        this.directivos.set([]);
        this.razonSocial = null;
      },
    });
  }
}
