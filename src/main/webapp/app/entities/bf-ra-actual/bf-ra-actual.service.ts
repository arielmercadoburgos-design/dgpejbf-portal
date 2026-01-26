import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { saveAs } from 'file-saver';
import * as XLSX from 'xlsx';
import { createRequestOption } from 'app/core/request/request-util';

/**
 * Registro principal – BF RA ACTUAL
 */
export interface IBfRaActual {
  tablaId?: number;
  ruc?: number;
  dv?: string;
  razonSocial?: string;
  tipo?: string;
  tramiteId?: number;
  tipoComunicacion?: string;
  fechaComunicacion?: Date;
}

/**
 * Respuesta paginada
 */
export interface IPage<T> {
  content: T[];
  totalElements: number;
  size: number;
  number: number;
  totalPages: number;
}

/**
 * Detalle del Beneficiario Final
 */
export interface IBfRaActualDetalle {
  tablaId?: number;
  ruc?: number;
  razonSocial?: string;
  tipo?: string;
  nombre?: string;
  cedula?: string;
  rucBf?: string;
  domicilio?: string;
  nacionalidad?: string;
  pais?: string;
  fecNac?: string;
  profesionOcupacion?: string;
  condicion?: string;
  participacion?: string;
  derechoVotacion?: string;
  info?: string;
  control?: string;
  fecha?: string;
  idPais?: string;
  nacPais?: string;
  tramiteId?: number;
  fechaComunicacion?: Date;
}

@Injectable({ providedIn: 'root' })
export class BfRaActualService {
  private resourceUrl = 'api/bf-ra-actual';

  constructor(private http: HttpClient) {}

  // 🔍 Buscar con filtros y paginación
  buscar(req?: any): Observable<HttpResponse<IPage<IBfRaActual>>> {
    const options = createRequestOption(req);
    return this.http.get<IPage<IBfRaActual>>(`${this.resourceUrl}/buscar`, {
      params: options,
      observe: 'response',
    });
  }

  // 📥 Exportar todos (sin paginación)
  exportAll(req?: any): Observable<IBfRaActual[]> {
    let params = new HttpParams();

    if (req) {
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

    return this.http.get<IBfRaActual[]>(`${this.resourceUrl}/export`, { params });
  }

  // 📥 Exportar CSV
  exportToCsv(req?: any): Observable<Blob> {
    const options = createRequestOption(req);
    return this.http.get(`${this.resourceUrl}/export-csv`, {
      params: options,
      responseType: 'blob',
    });
  }

  // 📊 Exportar a Excel (helper local)
  exportToExcel(data: IBfRaActual[], fileName: string): void {
    const worksheet: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    const workbook: XLSX.WorkBook = {
      Sheets: { Datos: worksheet },
      SheetNames: ['Datos'],
    };

    const excelBuffer: any = XLSX.write(workbook, {
      bookType: 'xlsx',
      type: 'array',
    });

    const blob: Blob = new Blob([excelBuffer], {
      type: 'application/octet-stream',
    });

    saveAs(blob, fileName);
  }

  // 👁️ Traer detalle por RUC
  getDetalleByRuc(ruc: number | string): Observable<IBfRaActualDetalle[]> {
    return this.http.get<IBfRaActualDetalle[]>(`${this.resourceUrl}/detalle/${ruc}`);
  }
}
