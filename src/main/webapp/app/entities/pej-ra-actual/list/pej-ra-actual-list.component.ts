import { Component, OnInit } from '@angular/core';
import { PejRaActualService, IPejRaActual } from '../list/pej-ra-actual.service';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-pej-ra-actual-list',
  templateUrl: '../list/pej-ra-actual-list.component.html',
  standalone: true,
  imports: [CommonModule, DatePipe, FormsModule],
})
export class PejRaActualListComponent implements OnInit {
  Math = Math;
  totalItems = 0;
  page = 0;
  size = 10;
  sort = ['tablaId,desc'];
  items: IPejRaActual[] = [];
  loading = false;
  busqueda = '';

  constructor(private service: PejRaActualService) {}

  ngOnInit(): void {
    this.loadPage(0);
  }

  loadPage(page: number): void {
    if (page < 0) return;

    this.loading = true;
    this.page = page;

    const req = {
      page: this.page,
      size: this.size,
      sort: this.sort,
    };

    this.service.query(req).subscribe((res: HttpResponse<IPejRaActual[]>) => {
      this.loading = false;

      this.items = res.body ?? [];

      const totalCountHeader = res.headers.get('X-Total-Count');
      this.totalItems = totalCountHeader ? Number(totalCountHeader) : this.items.length;

      console.warn('Página actual:', this.page + 1);
      console.warn('Items en página:', this.items.length);
      console.warn('Total Items:', this.totalItems);
    });
  }
  // En PejRaActualListComponent.ts

  // ... (resto de variables)

  get pageNumbers(): number[] {
    const totalPages = Math.ceil(this.totalItems / this.size);
    const currentPage = this.page;
    const maxPagesToShow = 9; // Puedes ajustar este número
    let startPage: number, endPage: number;

    if (totalPages <= maxPagesToShow) {
      // Mostrar todas las páginas
      startPage = 0;
      endPage = totalPages;
    } else {
      // Calcular inicio y fin para mostrar el máximo de páginas
      const maxPagesBeforeCurrentPage = Math.floor(maxPagesToShow / 2);
      const maxPagesAfterCurrentPage = Math.ceil(maxPagesToShow / 2) - 1;

      if (currentPage <= maxPagesBeforeCurrentPage) {
        // Cerca del inicio
        startPage = 0;
        endPage = maxPagesToShow;
      } else if (currentPage + maxPagesAfterCurrentPage >= totalPages) {
        // Cerca del final
        startPage = totalPages - maxPagesToShow;
        endPage = totalPages;
      } else {
        // En el medio
        startPage = currentPage - maxPagesBeforeCurrentPage;
        endPage = currentPage + maxPagesAfterCurrentPage;
      }
    }

    // Asegurar que startPage no sea negativo
    startPage = Math.max(startPage, 0);

    // Crear el array de números de página
    const pages = [];
    for (let i = startPage; i < endPage; i++) {
      pages.push(i);
    }
    return pages;
  }
}
