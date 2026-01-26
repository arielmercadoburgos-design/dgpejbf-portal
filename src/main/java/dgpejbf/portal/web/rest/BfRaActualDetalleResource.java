package dgpejbf.portal.web.rest;

import dgpejbf.portal.service.BfRaActualDetalleService;
import dgpejbf.portal.service.dto.secundaria.BfRaActualDetalleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tech.jhipster.web.util.PaginationUtil;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


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
        @RequestParam Integer ruc,
        @RequestParam(required = false) String nombre,
        Pageable pageable
    ) {
        Page<BfRaActualDetalleDTO> page = detalleService.buscar(ruc, nombre, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            page
        );

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
