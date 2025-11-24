import { NgModule, Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

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
        // la protección por ruta la cubrimos en el navbar (visibilidad),
        // pero si quieres, puedes añadir un guard similar a UserRouteAccessService
      },
    ]),
  ],
})
export class PejRaActualModule {}
