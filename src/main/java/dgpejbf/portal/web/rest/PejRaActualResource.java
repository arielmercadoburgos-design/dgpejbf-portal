package dgpejbf.portal.web.rest;

import dgpejbf.portal.service.PejRaActualService;
import dgpejbf.portal.service.dto.PejRaActualDTO;
import dgpejbf.portal.service.dto.PejRaActualDirectivoDTO;
import dgpejbf.portal.service.dto.PejRaActualSociosDTO;
import dgpejbf.portal.service.PejRaActualSociosService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.nio.charset.StandardCharsets;
@RestController
@RequestMapping("/api/pej-ra-actual")
public class PejRaActualResource {

    private final PejRaActualService service;
    private final PejRaActualSociosService pejRaActualSociosService;

    public PejRaActualResource(PejRaActualService pejRaActualService, PejRaActualSociosService pejRaActualSociosService) {
        this.service = pejRaActualService;
        this.pejRaActualSociosService = pejRaActualSociosService;
    }

    /**
     * Buscar con botón (filtra RUC y Razón Social)
     */
    @GetMapping("/buscar")
    public Page<PejRaActualDTO> buscar(
        @RequestParam(required = false) String ruc,
        @RequestParam(required = false) String razonSocial,
        @RequestParam(required = false) String tipo,
        Pageable pageable
    ) {
        return service.buscar(ruc, razonSocial, tipo, pageable);
    }
    @GetMapping("/export")
    public List<PejRaActualDTO> exportAll(
        @RequestParam(required = false) String ruc,
        @RequestParam(required = false) String razonSocial,
        @RequestParam(required = false) String tipo
    ) {
       
        Integer rucAsInteger = null;
        if (ruc != null && !ruc.isEmpty()) {
            try {
                rucAsInteger = Integer.valueOf(ruc);
            } catch (NumberFormatException e) {
                // Manejo de error silencioso igual que en tu exportAll
            }
        }

        return service.exportAll(razonSocial, tipo, rucAsInteger);
    }

    @GetMapping("/export-csv")
    public ResponseEntity<Resource> exportCSV(
        @RequestParam(required = false) String ruc,
        @RequestParam(required = false) String razonSocial,
        @RequestParam(required = false) String tipo
    ) {
        // 1. Reutilizamos tu lógica de conversión de RUC que ya tienes en exportAll
        Integer rucAsInteger = null;
        if (ruc != null && !ruc.isEmpty()) {
            try {
                rucAsInteger = Integer.valueOf(ruc);
            } catch (NumberFormatException e) {
                // Manejo de error silencioso igual que en tu exportAll
            }
        }

        // 2. Obtenemos los datos usando el servicio existente
        List<PejRaActualDTO> datos = service.exportAll(razonSocial, tipo, rucAsInteger);

        // 3. Generamos el contenido del CSV
        StringBuilder csv = new StringBuilder();
        // Cabeceras (ajusta según los campos de tu DTO)
        csv.append("RUC;Razon Social;Tipo\n"); 

        for (PejRaActualDTO dto : datos) {
            csv.append(dto.getRuc() != null ? dto.getRuc() : "").append(";")
               .append(dto.getRazonSocial() != null ? dto.getRazonSocial() : "").append(";")
               .append(dto.getTipo() != null ? dto.getTipo() : "").append("\n");
        }

        // 4. Convertimos a bytes y preparamos la respuesta de descarga
        byte[] csvBytes = csv.toString().getBytes(StandardCharsets.UTF_8);
        ByteArrayResource resource = new ByteArrayResource(csvBytes);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=exportacion.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(resource);
    }
        /**
     * Directivos por RUC
     */
    @GetMapping("/directivos/{ruc}")
    public List<PejRaActualDirectivoDTO> getDirectivos(@PathVariable Integer ruc) {
        return service.findDirectivosByRuc(ruc);
    }

    /**
     * Socios por RUC
     */
    @GetMapping("/socios/{ruc}")
    public List<PejRaActualSociosDTO> getSocios(
        @PathVariable Integer ruc
    ) {
        return pejRaActualSociosService.buscarPorRuc(ruc);
    }
}

