package dgpejbf.portal.service.dto;

import java.time.Instant;

public class PejRaActualDTO {

    private Integer tablaId;
    private Integer ruc;
    private String dv;
    private String razonSocial;
    private String tipo;
    private Integer tramiteId;
    private String tipoComunicacion;
    private Instant fechaComunicacion;

    // Getters y Setters

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

    public Instant getFechaComunicacion() {
        return fechaComunicacion;
    }

    public void setFechaComunicacion(Instant fechaComunicacion) {
        this.fechaComunicacion = fechaComunicacion;
    }
}
