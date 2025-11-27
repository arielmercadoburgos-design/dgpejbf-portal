import { Component, OnInit } from '@angular/core';
import { PejRaActualService, IPejRaActual, IPage } from '../list/pej-ra-actual.service';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
import { ChangeDetectionStrategy, signal } from '@angular/core';
import * as XLSX from 'xlsx';
import { saveAs } from 'file-saver';

@Component({
  selector: 'jhi-pej-ra-actual-list',
  templateUrl: './pej-ra-actual-list.component.html',
  standalone: true,
  imports: [CommonModule, DatePipe, FormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush, // Recomendado para Angular moderno
})
export class PejRaActualListComponent implements OnInit {
  // Variables de Estado
  Math = Math;
  busqueda = '';
  // Usamos signals para los datos para mejor rendimiento y reactividad
  itemsFiltrados = signal<IPejRaActual[]>([]);

  loading = signal(false);
  totalItems = signal(0);

  // Variables de Paginación y Carga
  page = 0; // Página actual
  size = 10;
  sort = ['tablaId,desc'];

  hasMore = signal(false); // NEW: Bandera para saber si hay más páginas para cargar

  filtroRuc = '';
  filtroRazon = '';

  constructor(private pejRaActualService: PejRaActualService) {}

  ngOnInit(): void {
    this.load(true); // Carga inicial
  }

  /**
   * Cargar con o sin filtros. Implementa el patrón "Cargar Más".
   * @param isNewSearch Indica si es una nueva búsqueda (resetear lista) o una carga adicional.
   */
  load(isNewSearch: boolean): void {
    if (!isNewSearch && this.loading()) {
      return; // No cargar si ya está cargando
    }

    if (!isNewSearch && !this.hasMore()) {
      console.warn('No hay más páginas para cargar.');
      return; // Detener la paginación si no hay más
    }

    this.loading.set(true);

    // Si es una nueva búsqueda, reiniciamos la página a 0
    if (isNewSearch) {
      this.page = 0;
    }

    const params: any = {
      page: this.page,
      size: this.size,
      sort: this.sort,
    };

    // Lógica de Filtrado
    if (this.busqueda.trim() !== '') {
      // Detectar si es un número => ruc, si no => razonSocial
      if (!isNaN(Number(this.busqueda.trim()))) {
        params.ruc = this.busqueda.trim();
      } else {
        params.razonSocial = this.busqueda.trim();
      }
    }

    this.pejRaActualService.buscar(params).subscribe({
      next: (res: HttpResponse<IPage<IPejRaActual>>) => {
        this.loading.set(false);
        const body = res.body;

        if (body?.content) {
          // *** CORRECCIÓN CLAVE: ANEXAR EN LUGAR DE REEMPLAZAR ***
          if (isNewSearch) {
            this.itemsFiltrados.set(body.content);
          } else {
            // Añade los nuevos items al final de la lista existente
            this.itemsFiltrados.update(items => [...items, ...body.content]);
          }
        } else if (isNewSearch) {
          // Si no hay contenido en la primera búsqueda, la lista queda vacía
          this.itemsFiltrados.set([]);
        }

        this.totalItems.set(body?.totalElements ?? 0);

        // CORRECCIÓN CLAVE PARA DETENER LA PAGINACIÓN
        // Si la página actual del backend es menor que (total de páginas - 1)
        // significa que hay más páginas disponibles.
        const totalPages = body?.totalPages ?? 0;
        this.hasMore.set(this.page < totalPages - 1);

        // Si la carga fue exitosa y hay más páginas, preparamos la siguiente página
        if (this.hasMore()) {
          this.page++;
        }
      },
      error: (err: HttpErrorResponse) => {
        console.error('Error al cargar datos:', err);
        this.loading.set(false);
        this.hasMore.set(false);
      },
    });
  }

  buscar(): void {
    // Si la búsqueda cambia, siempre iniciamos la carga en la página 0 y reiniciamos la lista.
    this.load(true);
  }

  // Nuevo método para cargar la siguiente página
  loadNextPage(): void {
    this.load(false);
  }
  exportToExcel(): void {
    const data = this.itemsFiltrados(); // ✅ Tomamos directamente del signal

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
}
