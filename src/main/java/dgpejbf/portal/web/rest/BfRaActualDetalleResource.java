package dgpejbf.portal.web.rest;

import dgpejbf.portal.service.BfRaActualDetalleService;
import dgpejbf.portal.service.dto.secundaria.BfRaActualDetalleDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BfRaActualDetalleResource {

    private final BfRaActualDetalleService detalleService;

    public BfRaActualDetalleResource(BfRaActualDetalleService detalleService) {
        this.detalleService = detalleService;
    }

    /**
     * GET /bf-ra-actual/detalle : detalle por empresa (botón Detalle)
     */
    @GetMapping("/bf-ra-actual/detalle")
    public ResponseEntity<java.util.List<BfRaActualDetalleDTO>> getDetalle(
    @PathVariable Integer ruc
) {
    List<BfRaActualDetalleDTO> lista = detalleService.findByRuc(ruc);
    return ResponseEntity.ok(lista);
}
}
