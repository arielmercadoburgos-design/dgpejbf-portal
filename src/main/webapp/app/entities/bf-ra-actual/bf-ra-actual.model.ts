export interface IBfRaActualDetalles {
  tablaId?: number;
  nombre?: string;
  cedula?: string;
  cargo?: string;
  profesionOcupacion?: string;
  domicilio?: string;
  fechaAsuncion?: string;
  vigencia?: string;
  // Agrega aquí los campos adicionales que tenga tu tabla BfRaActualDetalles
}

export class BfRaActualDetalles implements IBfRaActualDetalles {
  constructor(
    public tablaId?: number,
    public nombre?: string,
    public cedula?: string,
    public cargo?: string,
    public profesionOcupacion?: string,
    public domicilio?: string,
    public fechaAsuncion?: string,
    public vigencia?: string,
  ) {}
}
