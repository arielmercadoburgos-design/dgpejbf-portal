import { Component, Output, EventEmitter, OnInit, signal, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common'; // Agregado
import { ActivatedRoute, RouterModule } from '@angular/router'; // Agregado RouterModule
import { IBfRaActualDetalle } from './bf-ra-actual-detalle.model';
import { BfRaActualService } from '../bf-ra-actual/bf-ra-actual.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-bf-ra-actual-detalle',
  standalone: true,
  imports: [CommonModule, RouterModule, FontAwesomeModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
  template: `
    <div class="container mt-4">
      <div class="card shadow-lg border-0">
        <div class="card-header bg-primary text-white">
          <div class="d-flex justify-content-between align-items-center flex-wrap gap-2">
            <h4 class="mb-0">Detalle de Beneficiarios Finales</h4>

            <div class="d-flex gap-2 flex-wrap">
              <span class="badge bg-light text-primary">RUC Empresa: {{ ruc() }}</span>
              <span class="badge bg-light text-primary">Razón Social: {{ razonSocial() }}</span>
            </div>
          </div>

          <div class="mt-2 d-flex gap-2 flex-wrap">
            <button class="btn btn-light btn-sm" (click)="ejecutarExport('xls')" [disabled]="beneficiarios().length === 0">
              <i class="fa fa-file-excel me-1"></i> Exportar .xls
            </button>

            <button class="btn btn-light btn-sm" (click)="ejecutarExport('csv')" [disabled]="beneficiarios().length === 0">
              <i class="fa fa-file-csv me-1"></i> Exportar .csv
            </button>
          </div>
        </div>

        <div class="card-body p-4">
          <div *ngIf="loading()" class="text-center my-4">
            <div class="spinner-border text-primary"></div>
            <p>Cargando beneficiarios...</p>
          </div>

          <div class="table-responsive" *ngIf="!loading() && beneficiarios().length > 0">
            <table class="table table-hover align-middle mb-0">
              <thead class="thead-azul">
                <tr>
                  <th>Nombre y Apellido</th>
                  <th>Condición</th>
                  <th class="text-end">Nacionalidad</th>
                  <th class="text-end">Documento</th>
                  <th class="text-center">Fecha de Asunción</th>
                </tr>
              </thead>

              <tbody>
                <tr *ngFor="let b of beneficiarios()">
                  <td class="fw-bold">{{ b.nombre }}</td>
                  <td>{{ b.condicion }}</td>
                  <td class="text-end">{{ b.nacionalidad }}</td>
                  <td class="text-end">{{ b.cedula }}</td>
                  <td class="text-center">{{ b.fechaComunicacion }}</td>
                </tr>
              </tbody>
            </table>
          </div>

          <div *ngIf="!loading() && beneficiarios().length === 0" class="alert alert-warning text-center">
            No se encontraron beneficiarios registrados para este RUC.
          </div>

          <div class="mt-4">
            <button class="btn btn-outline-secondary" [routerLink]="['/bf-ra-actual']">
              <i class="fa fa-arrow-left me-1"></i> Volver a la lista
            </button>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class BfRaActualDetalleComponent implements OnInit {
  ruc = signal<string | null>(null);
  razonSocial = signal<string | null>(null);
  beneficiarios = signal<IBfRaActualDetalle[]>([]);
  loading = signal(false);
  @Output() Export = new EventEmitter<'csv' | 'xls'>();

  constructor(
    private route: ActivatedRoute,
    private bfRaActualService: BfRaActualService,
  ) {}

  ngOnInit(): void {
    // Capturamos el RUC de la URL: /bf-ra-actual/:ruc/detalle
    this.ruc.set(this.route.snapshot.paramMap.get('ruc'));
    // Capturamos la Razón Social de los Query Params
    this.razonSocial.set(this.route.snapshot.queryParamMap.get('razonSocial'));

    if (this.ruc()) {
      this.cargarBeneficiarios();
    }
  }

  ejecutarExport(formato: 'csv' | 'xls'): void {
    // Aquí emitimos 'csv' o 'xlsx' hacia el padre
    this.Export.emit(formato);
  }

  cargarBeneficiarios(): void {
    this.loading.set(true);
    const rucBusqueda = this.ruc();

    if (rucBusqueda) {
      this.bfRaActualService.getDetalleByRuc(rucBusqueda).subscribe({
        next: res => {
          console.warn('Datos recibidos del backend:', res);
          const beneficiariosMapeados = res.map(b => ({
            nombre: b.nombre,
            cedula: b.cedula,
            nacionalidad: b.nacionalidad,
            condicion: b.condicion, // del backend
            fechaComunicacion: b.fechaComunicacion ? new Date(b.fechaComunicacion).toLocaleDateString() : '',
            // si quieres otros campos, agregalos aquí
          }));

          this.beneficiarios.set(beneficiariosMapeados);
          this.loading.set(false);
        },
        error: () => {
          this.beneficiarios.set([]);
          this.loading.set(false);
        },
      });
    }
  }
}
