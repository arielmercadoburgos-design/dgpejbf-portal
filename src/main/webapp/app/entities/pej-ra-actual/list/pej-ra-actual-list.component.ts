import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectionStrategy, signal } from '@angular/core';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';

import { PejRaActualService, IPejRaActual, IPage } from '../list/pej-ra-actual.service';

@Component({
  selector: 'jhi-pej-ra-actual-list',
  templateUrl: './pej-ra-actual-list.component.html',
  standalone: true,
  imports: [CommonModule, DatePipe, FormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PejRaActualListComponent implements OnInit {
  Math = Math;

  // 🔎 Filtros
  busqueda = '';

  // 📊 Datos
  itemsFiltrados = signal<IPejRaActual[]>([]);
  totalItems = signal(0);
  loading = signal(false);

  // 📄 Paginación REAL
  page = 0; // página actual (empieza en 0)
  size = 10; // registros por página
  sort = ['tablaId,desc'];

  constructor(private pejRaActualService: PejRaActualService) {}

  ngOnInit(): void {
    this.loadPage(0); // carga inicial
  }

  // ✅ CARGA POR PÁGINA (PAGINACIÓN REAL)
  loadPage(page: number): void {
    this.loading.set(true);
    this.page = page;

    const params: any = {
      page: this.page,
      size: this.size,
      sort: this.sort,
    };

    // 🔎 Filtro por búsqueda
    if (this.busqueda.trim() !== '') {
      if (!isNaN(Number(this.busqueda.trim()))) {
        params.ruc = this.busqueda.trim();
      } else {
        params.razonSocial = this.busqueda.trim();
      }
    }

    this.pejRaActualService.buscar(params).subscribe({
      next: (res: HttpResponse<IPage<IPejRaActual>>) => {
        const body = res.body;

        // 🔑 CLAVE: REEMPLAZA los datos (NO concatena)
        this.itemsFiltrados.set(body?.content ?? []);
        this.totalItems.set(body?.totalElements ?? 0);

        this.loading.set(false);
      },
      error: (err: HttpErrorResponse) => {
        console.error('Error al cargar datos:', err);
        this.itemsFiltrados.set([]);
        this.loading.set(false);
      },
    });
  }

  // 🔎 Buscar desde página 0
  buscar(): void {
    this.loadPage(0);
  }

  // 📤 Exportar página actual
  exportToExcel(): void {
    const data = this.itemsFiltrados();

    if (data.length === 0) {
      console.warn('No hay datos para exportar');
      return;
    }

    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    const workbook: XLSX.WorkBook = {
      Sheets: { Datos: worksheet },
      SheetNames: ['Datos'],
    };

    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    const blob: Blob = new Blob([excelBuffer], { type: 'application/octet-stream' });

    saveAs(blob, 'PejRaActual.xlsx');
  }

  // 📤 Exportar TODO (sin paginación)
  exportToExcelAll(): void {
    this.pejRaActualService.exportAll().subscribe(data => {
      const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
      const workbook: XLSX.WorkBook = {
        Sheets: { Datos: worksheet },
        SheetNames: ['Datos'],
      };

      const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
      const blob: Blob = new Blob([excelBuffer], { type: 'application/octet-stream' });

      saveAs(blob, 'PejRaActual_TODO.xlsx');
    });
  }

  getPages(): number[] {
    const totalPages = Math.ceil(this.totalItems() / this.size);
    const maxPagesToShow = 10;

    let start = Math.max(0, this.page - Math.floor(maxPagesToShow / 2));
    let end = start + maxPagesToShow;

    if (end > totalPages) {
      end = totalPages;
      start = Math.max(0, end - maxPagesToShow);
    }
    return Array.from({ length: end - start }, (_, i) => start + i);
  }
}
