import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { PejRaActualSociosService } from './pej-ra-actual-socios.service'; // Asegúrate de crear este servicio
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { PejRaActualService, ISocios } from '../pej-ra-actual/list/pej-ra-actual.service';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';

@Component({
  selector: 'jhi-pej-ra-actual-socios-list',
  standalone: true,
  imports: [CommonModule, RouterModule, FontAwesomeModule],
  template: `
    <div class="container mt-4">
      <div class="card shadow-lg border-0">
        <!-- HEADER -->
        <div class="card-header bg-primary text-white">
          <!-- FILA 1: Título izq / Badges der (como Socios) -->
          <div class="d-flex justify-content-between align-items-center flex-wrap gap-2">
            <h4 class="mb-0">Detalle de Socios / Accionistas</h4>

            <div class="d-flex gap-2 flex-wrap">
              <span class="badge bg-light text-primary">RUC Empresa: {{ ruc }}</span>
              <span class="badge bg-light text-primary">Razón Social: {{ razonSocial || '-' }}</span>
            </div>
          </div>

          <!-- FILA 2: Botones abajo a la izquierda -->
          <div class="mt-2 d-flex gap-2 flex-wrap">
            <button class="btn btn-light btn-sm" (click)="exportarExcel()" [disabled]="socios().length === 0">Exportar .xls</button>

            <button class="btn btn-light btn-sm" (click)="exportarCSV()" [disabled]="socios().length === 0">Exportar .csv</button>
          </div>
        </div>

        <!-- BODY -->
        <div class="card-body p-4">
          <div *ngIf="loading()" class="text-center my-4">
            <div class="spinner-border text-primary"></div>
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
  tipo: string | null = null;
  socios = signal<ISocios[]>([]);
  loading = signal(false);

  constructor(
    private route: ActivatedRoute,
    private sociosService: PejRaActualService,
  ) {}

  ngOnInit(): void {
    this.ruc = this.route.snapshot.paramMap.get('ruc');
    // LOGS DE DIAGNÓSTICO
    console.warn('QUERY PARAMS COMPLETOS:', this.route.snapshot.queryParamMap.keys);
    console.warn('VALOR CAPTURADO RAZON:', this.route.snapshot.queryParamMap.get('razonSocial'));
    console.warn('VALOR CAPTURADO TIPO:', this.route.snapshot.queryParamMap.get('tipo'));
    this.razonSocial = this.route.snapshot.queryParamMap.get('razonSocial');
    this.tipo = this.route.snapshot.queryParamMap.get('tipo');

    if (this.ruc) {
      this.cargarSocios(this.ruc);
    }
  }

  cargarSocios(ruc: string): void {
    this.loading.set(true); // Empieza a cargar
    this.sociosService.findByRucS(Number(ruc)).subscribe({
      next: (res: ISocios[]) => {
        this.socios.set(res);
        // Rescate del tipo si no vino por URL
        if (!this.tipo && res.length > 0) {
          this.tipo = res[0].tipo ?? null;
        }

        this.loading.set(false); // Detiene el cargando
      },
      error: err => {
        console.error('Error cargando socios:', err);
        this.loading.set(false);
        this.socios.set([]);
      },
    });
  }

  exportarCSV(): void {
    if (!this.ruc) return;

    this.sociosService.getSociosByRuc(this.ruc).subscribe({
      next: (data: ISocios[]) => {
        if (!data.length) return;

        const mapped = this.mapDatosParaExport(data);

        const separator = ';';
        const headers = Object.keys(mapped[0]);
        const rows = mapped.map(row => headers.map(h => `"${row[h] ?? ''}"`).join(separator));

        const csvContent = [headers.join(separator), ...rows].join('\n');
        const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
        saveAs(blob, `socios_${this.ruc}.csv`);
      },
      error: (err: unknown) => console.error('Error exportando CSV:', err),
    });
  }

  exportarExcel(): void {
    if (!this.ruc) return;

    this.sociosService.getSociosByRuc(this.ruc).subscribe({
      next: (data: ISocios[]) => {
        if (!data.length) return;

        const mapped = this.mapDatosParaExport(data);
        const worksheet = XLSX.utils.json_to_sheet(mapped);
        const workbook = { Sheets: { Socios: worksheet }, SheetNames: ['Socios'] };
        const buffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
        const blob = new Blob([buffer], { type: 'application/octet-stream' });
        saveAs(blob, `socios_${this.ruc}.xlsx`);
      },
      error: (err: unknown) => console.error('Error exportando Excel:', err),
    });
  }

  private mapDatosParaExport(data: ISocios[]): any[] {
    return data.map(d => {
      // 1. Validamos el Tipo: Si no viene en el socio 'd', usamos el del componente 'this.tipo'
      const tipoFinal = d.tipo && d.tipo.trim() !== '' ? d.tipo : (this.tipo ?? '-');

      return {
        RUC: d.ruc ?? this.ruc,
        'Razon Social': d.razonSocial ?? this.razonSocial,
        Tipo: tipoFinal,
        Nombre: d.nombre,
        Documento: d.cedula,
        'Cantidad de acciones': d.cantidadAcciones,
        'Valor de acciones': d.valorAcciones,
        'Cantidad de votos': d.cantidadVotos,
      };
    });
  }
}
