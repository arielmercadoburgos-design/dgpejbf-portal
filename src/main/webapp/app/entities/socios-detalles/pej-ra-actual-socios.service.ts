import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PejRaActualSociosService {
  // Esta es la URL que definimos en el Resource de Java
  private resourceUrl = '/api/pej-ra-actual';

  constructor(protected http: HttpClient) {}

  /**
   * Método para buscar socios por RUC
   */
  findByRuc(ruc: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.resourceUrl}/socios/${ruc}`);
  }
}
