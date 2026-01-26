package dgpejbf.portal.service.dto;

import java.io.Serializable;
import java.time.Instant;

public class PejRaActualSociosDTO implements Serializable {

    private Integer tablaId;
    private Integer ruc;
    private String razonSocial;
    private String tipo;
    private String nombre;
    private String cedula;
    private String rucSocio;
    private String domicilio;
    private String profesion;
    private String cantidadAcciones;
    private String valorAcciones;
    private String porcentaje;
    private String cantidadVotos;
    private Instant fechaActualizacion;

    

    // --- Constructor Vacío ---
    public PejRaActualSociosDTO() {
    }

    // --- Getters y Setters ---

    public Integer getTablaId() { return tablaId; }
    public void setTablaId(Integer tablaId) { this.tablaId = tablaId; }

    public Integer getRuc() { return ruc; }
    public void setRuc(Integer ruc) { this.ruc = ruc; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getRucSocio() { return rucSocio; }
    public void setRucSocio(String rucSocio) { this.rucSocio = rucSocio; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public String getProfesion() { return profesion; }
    public void setProfesion(String profesion) { this.profesion = profesion; }

    public String getCantidadAcciones() { return cantidadAcciones; }
    public void setCantidadAcciones(String cantidadAcciones) { this.cantidadAcciones = cantidadAcciones; }

    public String getValorAcciones() { return valorAcciones; }
    public void setValorAcciones(String valorAcciones) { this.valorAcciones = valorAcciones; }

    public String getPorcentaje() { return porcentaje; }
    public void setPorcentaje(String porcentaje) { this.porcentaje = porcentaje; }

    public String getCantidadVotos() { return cantidadVotos; }
    public void setCantidadVotos(String cantidadVotos) { this.cantidadVotos = cantidadVotos; }

    public Instant getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(Instant fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

}