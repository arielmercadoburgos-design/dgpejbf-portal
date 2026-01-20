package dgpejbf.portal.domain.secundaria;

import jakarta.persistence.*; // Si usas Spring Boot 3 usa 'jakarta.persistence'

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidad que mapea la tabla portal_dgpejbf.pej_ra_actual_directivo
 */
@Entity
@Table(name = "pej_ra_actual_directivo", schema = "portal_dgpejbf")
public class PejRaActualDirectivo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tabla_id")
    private Integer tablaId;

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

    @Column(name = "domicilio")
    private String domicilio;

    @Column(name = "cargo")
    private String cargo;

    @Column(name = "fecha_asuncion")
    private String fechaAsuncion;

    @Column(name = "vigencia")
    private String vigencia;

    @Column(name = "profesion_ocupacion")
    private String profesionOcupacion;

    @Column(name = "tramite_id")
    private Integer tramiteId;

    @Column(name = "fecha_comunicacion")
    private LocalDateTime fechaComunicacion;

    // --- Constructor Vacío (Requerido por JPA) ---
    public PejRaActualDirectivo() {
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

    public String getDomicilio() { return domicilio; }
    public void setDomicilio(String domicilio) { this.domicilio = domicilio; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public String getFechaAsuncion() { return fechaAsuncion; }
    public void setFechaAsuncion(String fechaAsuncion) { this.fechaAsuncion = fechaAsuncion; }

    public String getVigencia() { return vigencia; }
    public void setVigencia(String vigencia) { this.vigencia = vigencia; }

    public String getProfesionOcupacion() { return profesionOcupacion; }
    public void setProfesionOcupacion(String profesionOcupacion) { this.profesionOcupacion = profesionOcupacion; }

    public Integer getTramiteId() { return tramiteId; }
    public void setTramiteId(Integer tramiteId) { this.tramiteId = tramiteId; }

    public LocalDateTime getFechaComunicacion() { return fechaComunicacion; }
    public void setFechaComunicacion(LocalDateTime fechaComunicacion) { this.fechaComunicacion = fechaComunicacion; }
}