package dgpejbf.portal.domain.secundaria;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "bf_ra_actual", schema = "portal_dgpejbf")
public class BfRaActual implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bf_ra_actual_seq")
    @SequenceGenerator(
        name = "bf_ra_actual_seq",
        sequenceName = "bf_ra_actual_tabla_id_seq",
        schema = "portal_dgpejbf",
        allocationSize = 1
    )
    @Column(name = "tabla_id")
    private Integer id;

    @Column(name = "ruc")
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
    private Instant fechaComunicacion;

    // getters y setters

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
