export interface IUser {
  id: number | null;
  login?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string;
  activated?: boolean;
  langKey?: string;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  fechaExpiracion?: string; // El campo extra fecha expiración
  telefono?: string | null;

  // AÑADE TUS CAMPOS DE USER EXTRA AQUÍ
  nroCedula?: string | null; // El campo que da error
  institucion?: string | null;
  cargo?: string | null;
  area?: string | null;
  telefono_ins?: string | null;
  celular?: string | null;
}

export class User implements IUser {
  constructor(
    public id: number | null,
    public login?: string,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string,
    public activated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public telefono?: string | null,
    public fechaExpiracion?: string, // El campo extra fecha expiración
  ) {}
}
