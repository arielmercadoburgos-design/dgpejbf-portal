import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common'; // Para que funcione ngIf
import { MetabaseService } from 'app/metabase/metabase.service'; // <-- 1. Importar el servicio
import { SafeUrlPipe } from 'app/metabase/safeUrl'; // <-- 2. Importar el pipe (si es standalone)

@Component({
  selector: 'jhi-dashboard-publico',
  imports: [CommonModule, SafeUrlPipe],
  templateUrl: './dashboard-publico.component.html',
  styleUrl: './dashboard-publico.component.scss',
  standalone: true,
})
export class DashboardPublicoComponent implements OnInit {
  titulo = 'Dashboard Público';

  // 4. Propiedad para almacenar la URL del iframe
  metabaseUrl: string | null = null;

  // 5. Inyección del servicio
  private readonly metabaseService = inject(MetabaseService);

  constructor(private router: Router) {}

  // 6. Implementar la lógica de carga en ngOnInit
  ngOnInit(): void {
    // ID del Dashboard de Metabase que quieres incrustar.
    // ¡Asegúrate de cambiar 123 por el ID real!
    const dashboardId = 9;

    this.loadDashboard(dashboardId);
  }

  // Método para cargar el dashboard
  loadDashboard(id: number): void {
    this.metabaseService.getEmbedUrl('dashboard', id).subscribe({
      next: (urlDescifrada: string) => {
        // <-- CAMBIO AQUÍ: Espera un string (la URL)

        // Almacena la URL descifrada directamente
        this.metabaseUrl = urlDescifrada; // <-- CAMBIO AQUÍ: Usa el valor directo
        console.warn('Metabase URL cargada con éxito'); // Usa console.log para desarrollo, cambia a warn/error para producción
      },
      error(err) {
        console.error('Error al cargar la URL de Metabase:', err);
        // Opcional: Mostrar un mensaje de error al usuario
      },
    });
  }

  navegarA(ruta: string): void {
    this.router.navigate([ruta]);
  }
}
