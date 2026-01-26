import { Component, OnInit, ChangeDetectionStrategy, signal } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import SharedModule from 'app/shared/shared.module';

import { BfRaActualService, IBfRaActual, IPage } from './bf-ra-actual.service';

@Component({
  selector: 'jhi-bf-ra-actual-list',
  templateUrl: './bf-ra-actual-list.component.html',
  standalone: true,
  imports: [CommonModule, DatePipe, FormsModule, FontAwesomeModule, SharedModule],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BfRaActualListComponent implements OnInit {
  Math = Math;

  busqueda = '';
  readonly MAX_PAGES_VISIBLE = 10;

  itemsFiltrados = signal<IBfRaActual[]>([]);
  totalItemsCount = signal(0);
  loading = signal(false);

  page = 0;
  size = 15;
  sort = ['tablaId,desc'];

  constructor(
    private bfRaActualService: BfRaActualService,
    private router: Router,
    private route: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.loadPage(0); // carga inicial
  }

  // 🔍 Buscar
  buscar(): void {
    this.loadPage(0);
  }

  // 📄 Carga de página con filtros
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
        params['ruc'] = valor;
      } else {
        params['razonSocial'] = valor;
        params['tipo'] = valor;
      }
    }

    this.bfRaActualService.buscar(params).subscribe({
      next: (res: HttpResponse<IPage<IBfRaActual>>) => {
        this.itemsFiltrados.set(res.body?.content ?? []);
        this.totalItemsCount.set(res.body?.totalElements ?? 0);
        this.loading.set(false);
      },
      error: () => {
        this.itemsFiltrados.set([]);
        this.loading.set(false);
      },
    });
  }

  // 👁️ Ver detalle del Beneficiario Final
  verDetalle(item: IBfRaActual): void {
    const ruc = item.ruc;
    if (ruc) {
      this.router.navigate(['/bf-ra-actual', ruc, 'detalle'], {
        queryParams: {
          razonSocial: item.razonSocial ?? '',
          tipo: item.tipo ?? '',
        },
      });
    }
  }

  // 📄 Paginación
  getTotalPages(): number {
    return Math.ceil(this.totalItemsCount() / this.size);
  }

  getPages(): number[] {
    const totalPages = this.getTotalPages();
    const currentPage = this.page;

    let startPage: number;
    let endPage: number;

    if (totalPages <= this.MAX_PAGES_VISIBLE) {
      startPage = 0;
      endPage = totalPages - 1;
    } else {
      const maxPagesBefore = Math.floor(this.MAX_PAGES_VISIBLE / 2);
      const maxPagesAfter = Math.ceil(this.MAX_PAGES_VISIBLE / 2) - 1;

      if (currentPage <= maxPagesBefore) {
        startPage = 0;
        endPage = this.MAX_PAGES_VISIBLE - 1;
      } else if (currentPage + maxPagesAfter >= totalPages) {
        startPage = totalPages - this.MAX_PAGES_VISIBLE;
        endPage = totalPages - 1;
      } else {
        startPage = currentPage - maxPagesBefore;
        endPage = currentPage + maxPagesAfter;
      }
    }

    return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
  }

  goToPage(pageNumber: number): void {
    this.loadPage(pageNumber);
  }

  // 📤 Exportar Excel (todo filtrado)
  exportToExcelAll(): void {
    const valor = this.busqueda.trim();
    const filtros: any = {};

    if (valor !== '') {
      if (!isNaN(Number(valor))) filtros['ruc'] = valor;
      else {
        filtros['razonSocial'] = valor;
        filtros['tipo'] = valor;
      }
    }

    this.bfRaActualService.exportAll(filtros).subscribe({
      next: (data: IBfRaActual[]) => {
        if (data.length > 0) {
          this.bfRaActualService.exportToExcel(data, 'BfRaActual_Completo.xlsx');
        }
      },
    });
  }

  // 📤 Exportar CSV
  exportarCSV(): void {
    const valor = this.busqueda.trim();
    const filtros: any = {};

    if (valor !== '') {
      if (!isNaN(Number(valor))) filtros['ruc'] = valor;
      else {
        filtros['razonSocial'] = valor;
        filtros['tipo'] = valor;
      }
    }
  }
}
