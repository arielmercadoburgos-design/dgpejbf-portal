package dgpejbf.portal.domain.secundaria;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "bf_ra_actual_detalle", schema = "portal_dgpejbf")
public class BfRaActualDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bf_ra_actual_detalle_seq")
    @SequenceGenerator(
        name = "bf_ra_actual_detalle_seq",
        sequenceName = "bf_ra_actual_detalle_tabla_id_seq",
        schema = "portal_dgpejbf",
        allocationSize = 1
    )
    @Column(name = "tabla_id")
    private Integer id;

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

    @Column(name = "ruc_bf")
    private String rucBf;

    @Column(name = "domicilio")
    private String domicilio;

    @Column(name = "nacionalidad")
    private String nacionalidad;

    @Column(name = "pais")
    private String pais;

    @Column(name = "fec_nac")
    private String fecNac;

    @Column(name = "profesion_ocupacion")
    private String profesionOcupacion;

    @Column(name = "condicion")
    private String condicion;

    @Column(name = "participacion")
    private String participacion;

    @Column(name = "derecho_votacion")
    private String derechoVotacion;

    @Column(name = "info")
    private String info;

    @Column(name = "control")
    private String control;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "id_pais")
    private String idPais;

    @Column(name = "nac_pais")
    private String nacPais;

    @Column(name = "tramite_id")
    private Integer tramiteId;

    @Column(name = "fecha_comunicacion")
    private Instant fechaComunicacion;

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
