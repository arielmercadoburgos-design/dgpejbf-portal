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

    // getters y setters (los generás igual que en las otras entidades)
}
