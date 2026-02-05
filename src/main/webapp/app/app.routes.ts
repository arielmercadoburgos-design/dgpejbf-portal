import { Component } from '@angular/core';
import { Routes } from '@angular/router';
import { Authority } from 'app/config/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import HomeComponent from './home/home.component';

const routes: Routes = [
  // 🏠 Ruta principal (inicio)
  {
    path: '',
    loadComponent: () => import('./home/home.component'),
    title: 'home.title',
  },
  // Navbar persistente (usa outlet)
  {
    path: '',
    loadComponent: () => import('./layouts/navbar/navbar.component'),
    outlet: 'navbar',
  },
  // 🔒 Administración (solo ADMIN)
  {
    path: 'admin',
    data: {
      authorities: [Authority.ADMIN],
    },
    canActivate: [UserRouteAccessService],
    loadChildren: () => import('./admin/admin.routes'),
  },
  // 👤 Cuentas (login, register, settings, etc.)
  {
    path: 'account',
    loadChildren: () => import('./account/account.route'),
  },
  // 🔑 Página de login
  {
    path: 'login',
    loadComponent: () => import('./login/login.component'),
    title: 'login.title',
  },
  {
    path: '',
    loadChildren: () => import('./entities/entity.routes'),
  },
  // 🌐 Página pública
  {
    path: 'public',
    component: HomeComponent,
    data: { pageTitle: 'Bienvenido al portal público' },
  },
  {
    path: '**',
    redirectTo: '', // redirige a la página de inicio para rutas no encontradas
  },
];

export default routes;
