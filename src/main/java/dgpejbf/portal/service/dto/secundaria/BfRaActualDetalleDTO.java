package dgpejbf.portal.service.dto.secundaria;

import java.io.Serializable;
import java.time.Instant;

public class BfRaActualDetalleDTO implements Serializable {

    private Integer id;
    private Integer ruc;
    private String razonSocial;
    private String tipo;
    private String nombre;
    private String cedula;
    private String rucBf;
    private String domicilio;
    private String nacionalidad;
    private String pais;
    private String fecNac;
    private String profesionOcupacion;
    private String condicion;
    private String participacion;
    private String derechoVotacion;
    private String info;
    private String control;
    private String fecha;
    private String idPais;
    private String nacPais;
    private Integer tramiteId;
    private Instant fechaComunicacion;

    // Getters y Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getRucBf() {
        return rucBf;
    }

    public void setRucBf(String rucBf) {
        this.rucBf = rucBf;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFecNac() {
        return fecNac;
    }

    public void setFecNac(String fecNac) {
        this.fecNac = fecNac;
    }

    public String getProfesionOcupacion() {
        return profesionOcupacion;
    }

    public void setProfesionOcupacion(String profesionOcupacion) {
        this.profesionOcupacion = profesionOcupacion;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getParticipacion() {
        return participacion;
    }

    public void setParticipacion(String participacion) {
        this.participacion = participacion;
    }

    public String getDerechoVotacion() {
        return derechoVotacion;
    }

    public void setDerechoVotacion(String derechoVotacion) {
        this.derechoVotacion = derechoVotacion;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdPais() {
        return idPais;
    }

    public void setIdPais(String idPais) {
        this.idPais = idPais;
    }

    public String getNacPais() {
        return nacPais;
    }

    public void setNacPais(String nacPais) {
        this.nacPais = nacPais;
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
