import { Routes } from '@angular/router';
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
  // detalle de directivos
  {
    path: 'directivos/:ruc',
    loadComponent: () => import('./directivos-detalle/directivos-detalle.component').then(m => m.DirectivosDetalleComponent),
    canActivate: [UserRouteAccessService],
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN'],
      pageTitle: 'Detalle de Directivos',
    },
  },
  // detalle de socios
  {
    path: 'socios/:ruc',
    loadComponent: () => import('./socios-detalles/pej-ra-actual-socios.component').then(m => m.PejRaActualSociosListComponent),
    canActivate: [UserRouteAccessService],
    data: {
      authorities: ['ROLE_USER', 'ROLE_ADMIN'],
      pageTitle: 'Detalle de Socios',
    },
  },
];

export default routes;
