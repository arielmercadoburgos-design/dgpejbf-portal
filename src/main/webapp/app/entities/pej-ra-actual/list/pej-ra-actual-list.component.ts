import { Component, OnInit } from '@angular/core';
import { PejRaActualService, IPejRaActual } from '../pej-ra-actual.service';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

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
  datos: IPejRaActual[] = [];
  loading = false;
  busqueda = '';

  constructor(private service: PejRaActualService) {}

  ngOnInit(): void {
    this.loadPage();
  }

  loadPage(page: number = this.page): void {
    this.loading = true;

    console.warn('Cargando datos de PejRaActual...');

    this.service.query().subscribe({
      next: (res: IPejRaActual[]) => {
        this.items = res;
        this.datos = res;
        this.totalItems = this.datos.length;
        this.page = page;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  get itemsFiltrados(): IPejRaActual[] {
    const termino = this.busqueda.toLowerCase();

    return this.items.filter(
      item => item.ruc?.toString().includes(termino) || item.razonSocial?.toLowerCase().includes(termino), // ⚡ si ruc o razonSocial son null, se ignora
    );
  }
}
