import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { createRequestOption } from 'app/core/request/request-util';

// Interfaz para un registro individual
export interface IPejRaActual {
  tablaId?: number;
  ruc?: number;
  dv?: number;
  razonSocial?: string;
  tipo?: string;
  tramiteId?: number;
  tipoComunicacion?: string;
  fechaComunicacion?: Date;
}

// Interfaz para la respuesta paginada
export interface IPage<T> {
  content: T[];
  totalElements: number;
  size: number;
  number: number; // página actual
  totalPages: number;
}
// Interfaz para la directivos
export interface IDirectivo {
  ruc?: string;
  razonSocial?: string;
  tipo?: string;
  nombre?: string;
  apellido?: string;
  nacionalidad?: string;
  fecha_comunicacion?: Date;
  cargo?: string;
  cedula?: string;
  fechaAsuncion?: string;
  // Añade aquí más campos si existen en portal_dgpejbf.pej_ra_actual_directivo
}
// Interfaz para la socios
export interface ISocios {
  ruc?: string;
  razonSocial?: string;
  tipo?: string;
  nombre: string;
  cedula?: string;
  rucSocio?: string;
  cantidadAcciones: number;
  porcentaje: number;
  valorAcciones: number;
  cantidadVotos: number;
  profesion?: string;
}
@Injectable({ providedIn: 'root' })
export class PejRaActualService {
  private resourceUrl = 'api/pej-ra-actual';

  constructor(private http: HttpClient) {}

  /** Trae directivos por RUC */

  getDirectivosBuyRuc(ruc: string): Observable<IDirectivo[]> {
    return this.http.get<IDirectivo[]>(`${this.resourceUrl}/directivos/${ruc}`);
  }
  /** Trae socios por RUC */
  getSociosByRuc(ruc: string): Observable<ISocios[]> {
    return this.http.get<ISocios[]>(`${this.resourceUrl}/socios/${ruc}`);
  }

  findByRucS(ruc: number | string): Observable<ISocios[]> {
    return this.getSociosByRuc(ruc.toString());
  }
  /** Traer todos sin filtros */
  buscar(req?: any): Observable<HttpResponse<IPage<IPejRaActual>>> {
    const options = createRequestOption(req);
    return this.http.get<IPage<IPejRaActual>>(`${this.resourceUrl}/buscar`, { params: options, observe: 'response' });
  }
  /** Exportar todos los registros (sin paginación) */
  exportAll(req?: any): Observable<IPejRaActual[]> {
    // Construimos los parámetros manualmente para evitar el error de createRequestOption
    let params = new HttpParams();

    if (req) {
      // Aseguramos traer todo asignando un tamaño grande si no viene definido
      if (!req.size) {
        params = params.set('size', '999999');
      }

      Object.keys(req).forEach(key => {
        const value = req[key];
        if (value !== undefined && value !== null) {
          params = params.set(key, value.toString());
        }
      });
    }
    return this.http.get<IPejRaActual[]>(`${this.resourceUrl}/export`, { params });
  }
  exportToCsv(req?: any): Observable<Blob> {
    const options = createRequestOption(req); // Asegúrate de que esta función esté importada
    return this.http.get(`${this.resourceUrl}/export-csv`, {
      params: options,
      responseType: 'blob',
    });
  }
}
