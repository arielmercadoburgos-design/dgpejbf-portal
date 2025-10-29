import NavbarItem from 'app/layouts/navbar/navbar-item.model';

export const EntityNavbarItems: NavbarItem[] = [
  {
    name: 'Portal de Datos', // 👈 Nombre visible en el menú
    route: '/entities/dashboard', // 👈 Ruta completa hacia tu módulo
    icon: 'chart-bar', // 👈 Icono de Font Awesome
    translationKey: 'global.menu.entities.dashboard', // 👈 Clave de traducción (opcional)
  },
];
