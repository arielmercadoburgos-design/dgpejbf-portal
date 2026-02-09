package dgpejbf.portal.web.rest;

import dgpejbf.portal.service.BfRaActualService;
import dgpejbf.portal.service.dto.secundaria.BfRaActualDTO;
import dgpejbf.portal.service.dto.secundaria.BfRaActualDetalleDTO;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/bf-ra-actual")

public class BfRaActualResource {

    private final Logger log = LoggerFactory.getLogger(BfRaActualResource.class);
    private final BfRaActualService service;

    public BfRaActualResource(BfRaActualService service) {
        this.service = service;       
    }

    /**
     * Buscar con filtros dinámicos y paginación
     */
    @GetMapping("/buscar")
    public Page<BfRaActualDTO> buscar(
        @RequestParam(required = false) String ruc,
        @RequestParam(required = false) String razonSocial,
        @RequestParam(required = false) String tipo,
        Pageable pageable
    ) {
        return service.buscar(ruc, razonSocial, tipo, pageable);
    }

    /**
     * Exportar todos los registros filtrados (sin paginación)
     */
    @GetMapping("/export")
    public List<BfRaActualDTO> exportAll(
        @RequestParam(required = false) String ruc,
        @RequestParam(required = false) String razonSocial,
        @RequestParam(required = false) String tipo
    ) {
        Integer rucAsInteger = null;
        if (ruc != null && !ruc.isEmpty()) {
            try {
                rucAsInteger = Integer.valueOf(ruc);
            } catch (NumberFormatException e) {
                // Se mantiene null si no se puede parsear
            }
        }

        return service.exportAll(razonSocial, tipo, rucAsInteger);
    }
    
    @GetMapping("/detalles")
    public ResponseEntity<List<BfRaActualDetalleDTO>> getDetalles(@RequestParam String ruc) {
        log.debug("Solicitud REST para obtener BfRaActualDetalles por RUC: {}", ruc);
    // Usamos el método que creamos en el service
        List<BfRaActualDetalleDTO> detalles = service.buscarDetallesPorRuc(ruc);
        return ResponseEntity.ok().body(detalles);
}
    /**
     * Exportar CSV
     */
    @GetMapping("/export-csv")
    public ResponseEntity<Resource> exportCSV(
        @RequestParam(required = false) String ruc,
        @RequestParam(required = false) String razonSocial,
        @RequestParam(required = false) String tipo
    ) {
        log.info("REST request to export CSV: ruc={}, razonSocial={}, tipo={}", ruc, razonSocial, tipo); // 👈 LOG 1
        Integer rucAsInteger = null;
        if (ruc != null && !ruc.isEmpty()) {
            try {
                rucAsInteger = Integer.valueOf(ruc);
            } catch (NumberFormatException e) {
                log.error("Error al convertir RUC: {}", ruc); // 👈 LOG 2
            }
        }

        List<BfRaActualDTO> datos = service.exportAll(razonSocial, tipo, rucAsInteger);
        log.info("Datos obtenidos en CSV: {}", datos != null ? datos.size() : "null"); // 👈 LOG 3

        StringBuilder csv = new StringBuilder();
        // Cabeceras (ajustar según los campos de BfRaActualDTO)
        csv.append("RUC;RazonSocial;Tipo;Comunicacion;FechaComunicacion\n");

        if (datos != null && !datos.isEmpty()) {
        for (BfRaActualDTO dto : datos) {
            csv.append(dto.getRuc() != null ? dto.getRuc().toString() : "").append(";")
               .append(dto.getRazonSocial() != null ? dto.getRazonSocial() : "").append(";")
               .append(dto.getTipo() != null ? dto.getTipo() : "").append(";")
               .append(dto.getTipoComunicacion()!= null ? dto.getTipoComunicacion() : "").append(";")
               .append(dto.getFechaComunicacion()!= null ? dto.getFechaComunicacion().toString() : "").append("\n");

        }
        }
            else {
                log.warn("La lista de datos está vacía."); // 👈 LOG 4
    }

        byte[] csvBytes = csv.toString().getBytes(StandardCharsets.UTF_8);
        ByteArrayResource resource = new ByteArrayResource(csvBytes);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bf_ra_actual_export.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(resource);
    }

    /**
     * Obtener un registro por ID
     */
    @GetMapping("/{id}")
    public BfRaActualDTO findOne(@PathVariable Long id) {
        return service.findOne(id);
    }

    /**
     * Obtener todos los registros sin filtros (si lo necesitás)
     */
    @GetMapping("/all")
    public List<BfRaActualDTO> findAll() {
        return service.findAll();
    }
}
