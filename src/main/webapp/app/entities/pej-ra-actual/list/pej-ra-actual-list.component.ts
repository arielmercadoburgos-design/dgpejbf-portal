import { Component, OnInit } from '@angular/core';
import { PejRaActualService, IPejRaActual } from '../pej-ra-actual.service';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
  selector: 'jhi-pej-ra-actual-list',
  templateUrl: '../list/pej-ra-actual-list.component.html',
  standalone: true,
  imports: [CommonModule, DatePipe],
})
export class PejRaActualListComponent implements OnInit {
  items: IPejRaActual[] = [];
  loading = false;

  constructor(private service: PejRaActualService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.loading = true;
    this.service.query().subscribe({
      next: data => {
        this.items = data;
        this.loading = false;
      },
      error: () => (this.loading = false),
    });
  }
}
