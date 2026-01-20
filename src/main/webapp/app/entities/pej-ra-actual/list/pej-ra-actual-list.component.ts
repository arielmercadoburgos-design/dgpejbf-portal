import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectionStrategy, signal } from '@angular/core';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { PejRaActualService, IPejRaActual, IPage } from '../list/pej-ra-actual.service';
import SharedModule from 'app/shared/shared.module';

@Component({
  selector: 'jhi-pej-ra-actual-list',
  templateUrl: './pej-ra-actual-list.component.html',
  standalone: true,
  imports: [CommonModule, DatePipe, FormsModule, FontAwesomeModule, SharedModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PejRaActualListComponent implements OnInit {
  Math = Math;
  busqueda = '';
  readonly MAX_PAGES_VISIBLE = 10;

  itemsFiltrados = signal<IPejRaActual[]>([]);
  totalItemsCount = signal(0);
  loading = signal(false);

  page = 0;
  size = 20;
  sort = ['tablaId,desc'];

  constructor(private pejRaActualService: PejRaActualService) {}

  ngOnInit(): void {
    this.loadPage(0); // carga inicial
  }

  // 📄 Paginación
  getPages(): number[] {
    const totalPages = Math.ceil(this.totalItemsCount() / this.size);
    const currentPage = this.page;
    let startPage: number, endPage: number;

    if (totalPages <= this.MAX_PAGES_VISIBLE) {
      // Si hay menos del máximo, mostrar todas
      startPage = 0;
      endPage = totalPages - 1;
    } else {
      // Calcular páginas adelante y atrás de la actual
      const maxPagesBeforeCurrentPage = Math.floor(this.MAX_PAGES_VISIBLE / 2);
      const maxPagesAfterCurrentPage = Math.ceil(this.MAX_PAGES_VISIBLE / 2) - 1;

      if (currentPage <= maxPagesBeforeCurrentPage) {
        startPage = 0;
        endPage = this.MAX_PAGES_VISIBLE - 1;
      } else if (currentPage + maxPagesAfterCurrentPage >= totalPages) {
        startPage = totalPages - this.MAX_PAGES_VISIBLE;
        endPage = totalPages - 1;
      } else {
        startPage = currentPage - maxPagesBeforeCurrentPage;
        endPage = currentPage + maxPagesAfterCurrentPage;
      }
    }

    // Crear el array de números (ej: [4, 5, 6, 7, 8])
    return Array.from({ length: endPage + 1 - startPage }, (_, i) => startPage + i);
  }

  // Función auxiliar para saber si hay muchas páginas y mostrar el "..."
  getTotalPages(): number {
    return Math.ceil(this.totalItemsCount() / this.size);
  }

  goToPage(pageNumber: number): void {
    this.loadPage(pageNumber);
  }

  buscar(): void {
    this.loadPage(0); // Siempre volver a la pág 0 al buscar
  }

  // ✅ Carga de página
  loadPage(page: number): void {
    this.loading.set(true);
    this.page = page;

    const params: any = {
      page: this.page,
      size: this.size,
      sort: this.sort,
    };

    const valor = this.busqueda.trim();

    if (valor !== '') {
      if (!isNaN(Number(valor))) {
        // Si es número, filtramos solo por RUC
        params['ruc'] = valor;
      } else {
        // Si es texto, filtramos por Razón Social
        params['razonSocial'] = valor;
        params['tipo'] = valor;
      }
    }
    this.pejRaActualService.buscar(params).subscribe({
      next: (res: HttpResponse<IPage<IPejRaActual>>) => {
        this.itemsFiltrados.set(res.body?.content ?? []);
        this.totalItemsCount.set(res.body?.totalElements ?? 0);
        this.loading.set(false);
      },
      error: err => {
        this.loading.set(false);
        this.itemsFiltrados.set([]);
      },
    });
  }

  // 🔹 Exportación de Excel filtrada
  exportToExcelAll(): void {
    const valor = this.busqueda.trim();
    const filtros: any = {};

    if (valor) {
      if (!isNaN(Number(valor))) filtros['ruc'] = valor;
      else filtros['razonSocial'] = valor;
      filtros['tipo'] = valor;
    }

    this.pejRaActualService.exportAll(filtros).subscribe({
      next: (data: IPejRaActual[]) => {
        if (data.length > 0) {
          this.exportDataToExcelFile(data, 'PejRaActual_Completo.xlsx');
        }
      },
    });
  }
  exportarCSV(): void {
    const valor = this.busqueda.trim();
    const filtros: any = {};
    if (valor !== '') {
      if (!isNaN(Number(valor))) {
        filtros['ruc'] = valor;
      } else {
        filtros['razonSocial'] = valor;
        filtros['tipo'] = valor;
      }
    }
    this.pejRaActualService.exportToCsv(filtros).subscribe({
      // Sintaxis corregida (Method Shorthand)
      next(blob: Blob) {
        const fileName = `reporte_${new Date().getTime()}.csv`;
        saveAs(blob, fileName);
      },
      error(error) {
        console.error('Error al exportar CSV', error);
      },
    });
  }

  private exportDataToExcelFile(data: IPejRaActual[], fileName: string): void {
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    const workbook: XLSX.WorkBook = { Sheets: { Datos: worksheet }, SheetNames: ['Datos'] };
    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    const blob: Blob = new Blob([excelBuffer], { type: 'application/octet-stream' });
    saveAs(blob, fileName);
  }
}
