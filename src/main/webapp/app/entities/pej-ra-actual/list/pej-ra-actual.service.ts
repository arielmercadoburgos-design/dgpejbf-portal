import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

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

@Injectable({ providedIn: 'root' })
export class PejRaActualService {
  private resourceUrl = 'api/pej-ra-actual';

  constructor(private http: HttpClient) {}

  /** Traer todos sin filtros */
  query(): Observable<HttpResponse<IPage<IPejRaActual>>> {
    return this.http.get<IPage<IPejRaActual>>(this.resourceUrl, { observe: 'response' });
  }

  /** Traer con filtros y paginación */
  buscar(filtros?: {
    ruc?: string | number;
    razonSocial?: string;
    page?: number;
    size?: number;
    sort?: string[];
  }): Observable<HttpResponse<IPage<IPejRaActual>>> {
    let params = new HttpParams();

    if (filtros) {
      Object.keys(filtros).forEach(key => {
        const value = filtros[key as keyof typeof filtros];
        if (value !== undefined) {
          params = params.set(key, value.toString());
        }
      });
    }

    return this.http.get<IPage<IPejRaActual>>(`${this.resourceUrl}/buscar`, {
      params,
      observe: 'response',
    });
  }
}
