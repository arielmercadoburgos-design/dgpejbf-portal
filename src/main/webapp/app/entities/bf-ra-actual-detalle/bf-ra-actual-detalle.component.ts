import { Component, Output, EventEmitter, OnInit, signal, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common'; // Agregado
import { ActivatedRoute, RouterModule } from '@angular/router'; // Agregado RouterModule
import { IBfRaActualDetalle } from './bf-ra-actual-detalle.model';
import { BfRaActualService } from '../bf-ra-actual/bf-ra-actual.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';

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
                  <th class="text-end">Documento</th>
                  <th class="text-center">Fecha de Asunción</th>
                </tr>
              </thead>

              <tbody>
                <tr *ngFor="let b of beneficiarios()">
                  <td class="fw-bold">{{ b.nombre }}</td>
                  <td class="text-end">{{ b.condicion }}</td>
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
            condicion: b.condicion, // del backend
            participacion: b.participacion,
            control: b.control,
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
  ejecutarExport(tipo: 'xls' | 'csv'): void {
    const dataExport = this.beneficiarios().map(b => ({
      'RUC Empresa': this.ruc(),
      'Razón Social': this.razonSocial(),
      'Nombre y Apellido': b.nombre,
      Cédula: b.cedula,
      Condición: b.condicion,
      Participación: b.participacion,
      Control: b.control,
      'Fecha de Asunción': b.fechaComunicacion ?? '',
    }));

    const worksheet = XLSX.utils.json_to_sheet(dataExport);

    if (tipo === 'xls') {
      const workbook: XLSX.WorkBook = {
        Sheets: { Detalle: worksheet },
        SheetNames: ['Detalle'],
      };

      const excelBuffer = XLSX.write(workbook, {
        bookType: 'xlsx',
        type: 'array',
      });

      this.saveAsExcelFile(excelBuffer, 'BfRaActualDetalle');
    } else {
      const csvData = XLSX.utils.sheet_to_csv(worksheet, { FS: ';' });
      const blob = new Blob([csvData], { type: 'text/csv;charset=utf-8;' });
      saveAs(blob, `BfRaActualDetalle_${Date.now()}.csv`);
    }
  }
  private saveAsExcelFile(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8',
    });
    saveAs(data, `${fileName}_${Date.now()}.xlsx`);
  }
}
