import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class PejRaActualService {
  private resourceUrl = 'api/pej-ra-actual';

  constructor(private http: HttpClient) {}

  query(page: number, size: number, sort: string[]): Observable<HttpResponse<any[]>> {
    let params = new HttpParams().set('page', page).set('size', size);

    sort.forEach(val => {
      params = params.append('sort', val);
    });

    return this.http.get<any[]>(this.resourceUrl, {
      params,
      observe: 'response',
    });
  }
}
