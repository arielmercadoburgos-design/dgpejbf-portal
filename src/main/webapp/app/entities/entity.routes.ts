import { Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'monolitoApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  // grila portal de datos PEJ
  {
    path: 'pej-ra-actual',
    data: { authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_RECURRENTE'], pageTitle: 'Registros Administrativos' },
    canActivate: [UserRouteAccessService],
    loadComponent: () => import('./pej-ra-actual/list/pej-ra-actual-list.component').then(m => m.PejRaActualListComponent),
  },
  // detalle de directivos
  {
    path: 'directivos/:ruc',
    data: { authorities: ['ROLE_USER', 'ROLE_ADMIN'], pageTitle: 'Detalle de Directivos' },
    canActivate: [UserRouteAccessService],
    loadComponent: () => import('./directivos-detalle/directivos-detalle.component').then(m => m.DirectivosDetalleComponent),
  },
  // detalle de socios
  {
    path: 'socios/:ruc',
    data: { authorities: ['ROLE_USER', 'ROLE_ADMIN'], pageTitle: 'Detalle de Socios' },
    canActivate: [UserRouteAccessService],
    loadComponent: () => import('./socios-detalles/pej-ra-actual-socios.component').then(m => m.PejRaActualSociosListComponent),
  },
  // 📁 RUta grilla beneficiarios finales
  {
    path: 'bf-ra-actual',
    data: { authorities: ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_RECURRENTE'], pageTitle: 'Beneficiarios Finales' },
    canActivate: [UserRouteAccessService],
    loadComponent: () => import('../entities/bf-ra-actual/bf-ra-actual-list.component').then(m => m.BfRaActualListComponent),
  },
  // detalle beneficiarios finales
  {
    path: 'detalle/:ruc',
    data: { authorities: ['ROLE_USER', 'ROLE_ADMIN'], pageTitle: 'Detalle de Beneficiarios Finales' },
    canActivate: [UserRouteAccessService],
    loadComponent: () => import('./bf-ra-actual-detalle/bf-ra-actual-detalle.component').then(m => m.BfRaActualDetalleComponent),
  },
];

export default routes;
