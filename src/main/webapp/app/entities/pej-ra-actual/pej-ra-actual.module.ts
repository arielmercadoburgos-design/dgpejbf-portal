import { NgModule, Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { DirectivosDetalleComponent } from '../directivos-detalle/directivos-detalle.component';
@Component({
  selector: 'jhi-pej-ra-actual-list',
  standalone: true,
  template: `<p>PejRaActualListComponent works!</p>`,
})
export class PejRaActualListComponent {}
@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild([
      {
        path: 'pej-ra-actual',
        component: PejRaActualListComponent,
      },
      {
        path: 'directivos-detalle/:ruc',
        component: DirectivosDetalleComponent, // En lugar de loadComponent
        // pero si quieres, puedes añadir un guard similar a UserRouteAccessService
      },
    ]),
  ],
})
export class PejRaActualModule {}
