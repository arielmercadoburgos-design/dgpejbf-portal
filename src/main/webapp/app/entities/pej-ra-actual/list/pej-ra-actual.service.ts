import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
// ! Importa la función auxiliar de JHipster, la ruta puede variar:
import { createRequestOption } from 'app/core/request/request-util';
import { Observable } from 'rxjs';

// Interfaz para la entidad (Asegúrate de que esta esté definida)
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

@Injectable({ providedIn: 'root' })
export class PejRaActualService {
  private resourceUrl = 'api/pej-ra-actual';

  constructor(private http: HttpClient) {}

  query(req?: any): Observable<HttpResponse<IPejRaActual[]>> {
    const options = createRequestOption(req);
    return this.http.get<IPejRaActual[]>(this.resourceUrl, {
      params: options,
      observe: 'response',
    });
  }
}
