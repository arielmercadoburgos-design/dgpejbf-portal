package dgpejbf.portal.domain.secundaria;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "pej_ra_actual_socio", schema = "portal_dgpejbf")
public class PejRaActualSocios implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "pej_ra_actual_socio_tabla_id_seq", allocationSize = 1)
    private Integer tabla_id;

    @Column(name = "ruc")
    private Integer ruc;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "ruc_socio")
    private String rucSocio;

    @Column(name = "domicilio")
    private String domicilio;

    @Column(name = "profesion")
    private String profesion;

    @Column(name = "cantidad_acciones")
    private String cantidadAcciones;

    @Column(name = "valor_acciones")
    private String valorAcciones;

    @Column(name = "porcentaje")
    private String porcentaje;

    @Column(name = "cantidad_votos")
    private String cantidadVotos;

    @Column(name = "tramite_id")
    private Integer tramiteId;

    @Column(name = "fecha_comunicacion")
    private LocalDate fechaComunicacion;

    // --- Getters y Setters ---

    public Integer getTablaId() { return tabla_id; }
    public void setTablaId(Integer tablaId) { this.tabla_id = tablaId; }

    public Integer getRuc() { return ruc; }
    public void setRuc(Integer ruc) { this.ruc = ruc; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getPorcentaje() { return porcentaje; }
    public void setPorcentaje(String porcentaje) { this.porcentaje = porcentaje; }

    public String getCantidadAcciones() { return cantidadAcciones; }
    public void setCantidadAcciones(String cantidadAcciones) { this.cantidadAcciones = cantidadAcciones; }

    public String getValorAcciones() { return valorAcciones; }
    public void setValorAcciones(String valorAcciones) { this.valorAcciones = valorAcciones; }

    public String getRucSocio() { return rucSocio; }
    public void setRucSocio(String rucSocio) { this.rucSocio = rucSocio; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public String getProfesion() { return profesion; }
    public void setProfesion(String profesion) { this.profesion = profesion; }

    public String getCantidadVotos() { return cantidadVotos; }
    public void setCantidadVotos(String cantidadVotos) { this.cantidadVotos = cantidadVotos; }

    public Integer getTramiteId() { return tramiteId; }
    public void setTramiteId(Integer tramiteId) { this.tramiteId = tramiteId; }

    public LocalDate getFechaComunicacion() { return fechaComunicacion; }
    public void setFechaComunicacion(LocalDate fechaComunicacion) { this.fechaComunicacion = fechaComunicacion; }

    
    // (Agrega los demás getters y setters para los otros campos siguiendo este patrón)
}