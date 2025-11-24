package dgpejbf.portal.domain.secundaria;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pej_ra_actual", schema = "portal_dgpejbf")
public class PejRaActual {

    @Id
    @Column(name = "tabla_id")
    private Integer tablaId;

    @Column(name = "ruc", unique = true)
    private Integer ruc;

    @Column(name = "dv")
    private String dv;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "tramite_id")
    private Integer tramiteId;

    @Column(name = "tipo_comunicacion")
    private String tipoComunicacion;

    @Column(name = "fecha_comunicacion")
    private LocalDateTime fechaComunicacion;

    // getters y setters

    public Integer getTablaId() {
        return tablaId;
    }

    public void setTablaId(Integer tablaId) {
        this.tablaId = tablaId;
    }

    public Integer getRuc() {
        return ruc;
    }

    public void setRuc(Integer ruc) {
        this.ruc = ruc;
    }

    public String getDv() {
        return dv;
    }

    public void setDv(String dv) {
        this.dv = dv;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(Integer tramiteId) {
        this.tramiteId = tramiteId;
    }

    public String getTipoComunicacion() {
        return tipoComunicacion;
    }

    public void setTipoComunicacion(String tipoComunicacion) {
        this.tipoComunicacion = tipoComunicacion;
    }

    public LocalDateTime getFechaComunicacion() {
        return fechaComunicacion;
    }

    public void setFechaComunicacion(LocalDateTime fechaComunicacion) {
        this.fechaComunicacion = fechaComunicacion;
    }
}
