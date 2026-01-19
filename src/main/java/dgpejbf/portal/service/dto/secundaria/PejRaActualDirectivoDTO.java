package dgpejbf.portal.service.dto.secundaria;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link portal_dgpejbf.pej_ra_actual_directivo} entity.
 */
public class PejRaActualDirectivoDTO implements Serializable {

    private Integer tablaId;
    private Integer ruc;
    private String razonSocial;
    private String tipo;
    private String nombre;
    private String cedula;
    private String domicilio;
    private String cargo;
    private String fechaAsuncion;
    private String vigencia;
    private String profesionOcupacion;
    private Integer tramiteId;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFechaAsuncion() {
        return fechaAsuncion;
    }

    public void setFechaAsuncion(String fechaAsuncion) {
        this.fechaAsuncion = fechaAsuncion;
    }

    public String getVigencia() {
        return vigencia;
    }

    public void setVigencia(String vigencia) {
        this.vigencia = vigencia;
    }

    public String getProfesionOcupacion() {
        return profesionOcupacion;
    }

    public void setProfesionOcupacion(String profesionOcupacion) {
        this.profesionOcupacion = profesionOcupacion;
    }

    public Integer getTramiteId() {
        return tramiteId;
    }

    public void setTramiteId(Integer tramiteId) {
        this.tramiteId = tramiteId;
    }

    public Instant getFechaComunicacion() {
        return fechaComunicacion;
    }

    public void setFechaComunicacion(Instant fechaComunicacion) {
        this.fechaComunicacion = fechaComunicacion;
    }
}