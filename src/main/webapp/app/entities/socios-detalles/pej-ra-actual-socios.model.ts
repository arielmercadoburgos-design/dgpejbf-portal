export interface IPejRaActualSocios {
  tablaId?: number;
  ruc?: number;
  razonSocial?: string;
  nombre?: string;
  cedula?: string;
  rucSocio?: string;
  porcentaje?: string;
  cantidadAcciones?: string;
  valorAcciones?: string;
  cantidadVotos?: string;
  tramiteId?: number;
}

export class PejRaActualSocios implements IPejRaActualSocios {
  constructor(
    public tablaId?: number,
    public ruc?: number,
    public razonSocial?: string,
    public nombre?: string,
    public cedula?: string,
    public rucSocio?: string,
    public porcentaje?: string,
    public cantidadAcciones?: string,
    public valorAcciones?: string,
    public cantidadVotos?: string,
    public tramiteId?: number,
  ) {}
}
