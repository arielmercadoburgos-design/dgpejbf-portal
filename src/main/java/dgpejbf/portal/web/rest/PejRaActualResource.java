package dgpejbf.portal.web.rest;

import dgpejbf.portal.service.PejRaActualService;
import dgpejbf.portal.service.dto.PejRaActualDTO;
import java.util.List;
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
    @GetMapping("/export")
    public List<PejRaActualDTO> exportAll(
        @RequestParam(required = false) String ruc,
        @RequestParam(required = false) String razonSocial
) {
    Integer rucAsInteger = null;
        if (ruc != null && !ruc.isEmpty()) {
            try {
                // Conversión manual y segura a Integer
                rucAsInteger = Integer.valueOf(ruc);
            } catch (NumberFormatException e) {
                // Si la conversión falla (por ejemplo, "?ruc=texto"), rucAsInteger queda en null.
            }
        }
    return service.exportAll(razonSocial, rucAsInteger); 
}
}
