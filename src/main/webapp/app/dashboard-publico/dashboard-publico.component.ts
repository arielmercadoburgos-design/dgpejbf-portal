import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'jhi-dashboard-publico',
  imports: [],
  templateUrl: './dashboard-publico.component.html',
  styleUrl: './dashboard-publico.component.scss',
})
export class DashboardPublicoComponent implements OnInit {
  titulo = 'Dashboard Público';

  constructor(private router: Router) {}
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  navegarA(ruta: string): void {
    this.router.navigate([ruta]);
  }
}
