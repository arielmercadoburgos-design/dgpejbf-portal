import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface IPejRaActual {
  tablaId?: number;
  ruc?: number;
  dv?: string;
  razonSocial?: string;
  tipo?: string;
  tramiteId?: number;
  tipoComunicacion?: string;
  fechaComunicacion?: string; // ISO
}

@Injectable({ providedIn: 'root' })
export class PejRaActualService {
  private resourceUrl = 'api/pej-ra-actual';

  constructor(private http: HttpClient) {}

  query(): Observable<IPejRaActual[]> {
    return this.http.get<IPejRaActual[]>(this.resourceUrl);
  }

  find(id: number): Observable<IPejRaActual> {
    return this.http.get<IPejRaActual>(`${this.resourceUrl}/${id}`);
  }
}
