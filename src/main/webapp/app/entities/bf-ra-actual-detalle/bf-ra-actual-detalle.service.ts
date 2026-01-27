import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBfRaActualDetalle } from './bf-ra-actual-detalle.model';

type EntityArrayResponseType = HttpResponse<IBfRaActualDetalle[]>;

@Injectable({ providedIn: 'root' })
export class BfRaActualDetalleService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/bf-ra-detalle');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  /**
   * Busca los detalles vinculados a un trámite específico.
   * Este es el que usa tu componente para la grilla de detalles.
   */
  findByTramite(tramiteId: number): Observable<EntityArrayResponseType> {
    return this.http.get<IBfRaActualDetalle[]>(`${this.resourceUrl}/tramite/${tramiteId}`, { observe: 'response' });
  }

  /**
   * Busca por RUC con soporte para paginación (opcional)
   */
  findByRuc(ruc: number, req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBfRaActualDetalle[]>(`${this.resourceUrl}/ruc/${ruc}`, { params: options, observe: 'response' });
  }

  /**
   * Método genérico para búsqueda con filtros (si decides usar JpaSpecificationExecutor)
   */
  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBfRaActualDetalle[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  find(id: number): Observable<HttpResponse<IBfRaActualDetalle>> {
    return this.http.get<IBfRaActualDetalle>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
