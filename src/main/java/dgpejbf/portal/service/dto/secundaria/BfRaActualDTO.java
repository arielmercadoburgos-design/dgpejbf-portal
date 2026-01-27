package dgpejbf.portal.service.dto.secundaria;
import java.util.List;
import java.time.LocalDate;

public class BfRaActualDTO {

    private Integer id;
    private Integer ruc;
    private String dv;
    private String razonSocial;
    private String tipo;
    private Integer tramiteId;
    private String tipoComunicacion;
    private LocalDate fechaComunicacion;
    private List<BfRaActualDetalleDTO> detalles;

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

    public LocalDate getFechaComunicacion() {
        return fechaComunicacion;
    }

    public void setFechaComunicacion(LocalDate fechaComunicacion) {
        this.fechaComunicacion = fechaComunicacion;
    }
    
    public void setDetalles(List<BfRaActualDetalleDTO> detalles) {
        this.detalles = detalles;
    }
    public List<BfRaActualDetalleDTO> getDetalles() {
        return detalles;
    }
}
