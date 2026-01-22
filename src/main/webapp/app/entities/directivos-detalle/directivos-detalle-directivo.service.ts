import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({ providedIn: 'root' })
export class PejRaActualDirectivoService {
  // Esta URL debe coincidir con tu Resource de Java
  private resourceUrl = '/api/pej-ra-actual';

  constructor(private http: HttpClient) {}

  findByRuc(ruc: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.resourceUrl}/directivos/${ruc}`);
  }
}
