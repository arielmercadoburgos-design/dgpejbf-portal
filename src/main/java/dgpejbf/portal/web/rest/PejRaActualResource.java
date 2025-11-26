package dgpejbf.portal.web.rest;

import dgpejbf.portal.service.PejRaActualService;
import dgpejbf.portal.service.dto.PejRaActualDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pej-ra-actual")
public class PejRaActualResource {

    private final PejRaActualService service;

    public PejRaActualResource(PejRaActualService pejRaActualService) {
        this.service = pejRaActualService;
    }

    /**
     * Buscar con botón (filtra RUC y Razón Social)
     */
    @GetMapping("/buscar")
    public Page<PejRaActualDTO> buscar(
        @RequestParam(required = false) String ruc,
        @RequestParam(required = false) String razonSocial,
        Pageable pageable
    ) {
        return service.buscar(ruc, razonSocial, pageable);
    }
}
