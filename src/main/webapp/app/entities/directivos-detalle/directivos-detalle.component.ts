import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { PejRaActualDirectivoService } from './directivos-detalle-directivo.service';
import { IDirectivo } from '../pej-ra-actual/list/pej-ra-actual.service';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';

@Component({
  selector: 'jhi-directivos-detalle',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="container mt-4">
      <div class="card shadow-lg border-0">
        <!-- HEADER -->
        <div class="card-header bg-primary text-white">
          <!-- FILA 1: Título izq / Badges der (como Socios) -->
          <div class="d-flex justify-content-between align-items-center flex-wrap gap-2">
            <h4 class="mb-0">Detalle de Directivos</h4>

            <div class="d-flex gap-2 flex-wrap">
              <span class="badge bg-light text-primary">RUC Empresa: {{ ruc }}</span>
              <span class="badge bg-light text-primary">Razón Social: {{ razonSocial || '-' }}</span>
            </div>
          </div>

          <!-- FILA 2: Botones abajo a la izquierda -->
          <div class="mt-2 d-flex gap-2 flex-wrap">
            <button class="btn btn-light btn-sm" (click)="exportarExcel()" [disabled]="directivos().length === 0">Exportar .xls</button>

            <button class="btn btn-light btn-sm" (click)="exportarCSV()" [disabled]="directivos().length === 0">Exportar .csv</button>
          </div>
        </div>

        <!-- BODY -->
        <div class="card-body p-4">
          <div *ngIf="loading()" class="text-center my-4">
            <div class="spinner-border text-primary"></div>
            <p>Cargando directivos...</p>
          </div>

          <div class="table-responsive" *ngIf="!loading() && directivos().length > 0">
            <table class="table table-hover align-middle">
              <thead class="table-light">
                <tr>
                  <th>Nombre y Apellido</th>
                  <th>Cargo</th>
                  <th class="text-end">Documento</th>
                  <th class="text-center">Fecha de Asunción</th>
                </tr>
              </thead>

              <tbody>
                <tr *ngFor="let d of directivos()">
                  <td>{{ d.nombre }}</td>
                  <td>{{ d.cargo }}</td>
                  <td class="text-end">{{ d.cedula }}</td>
                  <td class="text-center">{{ d.fechaAsuncion }}</td>
                </tr>
              </tbody>
            </table>
          </div>

          <div *ngIf="!loading() && directivos().length === 0" class="alert alert-warning text-center">
            No se encontraron directivos registrados para este RUC.
          </div>

          <div class="mt-4">
            <button class="btn btn-outline-secondary" [routerLink]="['/pej-ra-actual']">Volver a la lista</button>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class DirectivosDetalleComponent implements OnInit {
  ruc: string | null = null;
  razonSocial: string | null = null;

  directivos = signal<IDirectivo[]>([]);
  loading = signal(false);

  constructor(
    private route: ActivatedRoute,
    private directivoService: PejRaActualDirectivoService,
  ) {}

  ngOnInit(): void {
    this.ruc = this.route.snapshot.paramMap.get('ruc');
    this.razonSocial = this.route.snapshot.queryParamMap.get('razonSocial');

    if (this.ruc) {
      this.cargarDirectivos(this.ruc);
    }
  }

  cargarDirectivos(ruc: string): void {
    this.loading.set(true);

    this.directivoService.findByRuc(Number(ruc)).subscribe({
      next: (res: IDirectivo[]) => {
        this.directivos.set(res);
        this.loading.set(false);
      },
      error: err => {
        console.error('Error cargando directivos:', err);
        this.directivos.set([]);
        this.loading.set(false);
      },
    });
  }

  // EXPORT CSV
  exportarCSV(): void {
    const data = this.directivos();
    if (!data.length) return;

    const separator = ';';
    const headers = ['RUC', 'RAZON SOCIAL', 'TIPO', 'NOMBRE', 'CARGO', 'DOCUMENTO', 'FECHA ASUNCION'];

    const rows = data.map(d =>
      [d.ruc, d.razonSocial, d.tipo, d.nombre, d.cargo, d.cedula, d.fechaAsuncion].map(v => `"${v ?? ''}"`).join(separator),
    );

    const csvContent = [headers.join(separator), ...rows].join('\n');
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });

    saveAs(blob, `directivos_${this.ruc}.csv`);
  }

  // EXPORT EXCEL
  exportarExcel(): void {
    const data = this.directivos();
    if (!data.length) return;

    const worksheet = XLSX.utils.json_to_sheet(
      data.map(d => ({
        RUC: d.ruc,
        'Razón Social': d.razonSocial,
        Tipo: d.tipo,
        Nombre: d.nombre,
        Cargo: d.cargo,
        Documento: d.cedula,
        'Fecha Asunción': d.fechaAsuncion,
      })),
    );

    const workbook = {
      Sheets: { Directivos: worksheet },
      SheetNames: ['Directivos'],
    };

    const buffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    const blob = new Blob([buffer], { type: 'application/octet-stream' });

    saveAs(blob, `directivos_${this.ruc}.xlsx`);
  }
}
