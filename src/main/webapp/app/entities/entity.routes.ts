import { Routes } from '@angular/router';
import { User } from 'app/admin/user-management/user-management.model';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'monolitoApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'dashboard', // mi dashboard
    data: { pageTitle: 'Portal de Datos' },
    loadChildren: () => import('./dashboard/dashboard.routes'),
  },
  {
    path: 'pej-ra-actual',
    data: { pageTitle: 'Registros Administrativos' },
    loadComponent: () => import('./pej-ra-actual/list/pej-ra-actual-list.component').then(m => m.PejRaActualListComponent),
  },
  {
    path: 'directivos-detalle/:ruc',
    loadComponent: () => import('./directivos-detalle/directivos-detalle.component').then(m => m.DirectivosDetalleComponent),
    canActivate: [UserRouteAccessService],
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN'],
      pageTitle: 'Detalle de Directivos',
    },
  },
  {
    path: 'hola',
    loadComponent: () => import('./pej-ra-actual/list/pej-ra-actual-list.component').then(m => m.PejRaActualListComponent),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
