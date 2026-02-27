package dgpejbf.portal.web.rest;


import dgpejbf.portal.domain.secundaria.Pais;
import dgpejbf.portal.domain.secundaria.Ciudad;
import dgpejbf.portal.service.PaisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parametros") // URL base para tus combos/formularios
public class PaisResource {

    private final PaisService paisService;

    public PaisResource(PaisService paisService) {
        this.paisService = paisService;
    }

    // 1. Endpoint para cargar el combo de PAÍSES
    @GetMapping("/paises")
    public List<Pais> getAllPaises() {
        return paisService.listarTodosLosPaises();
    }

    // 2. Endpoint para cargar el combo de CIUDADES filtrado por país
    // Se usa así: /api/parametros/paises/1/ciudades
    @GetMapping("/paises/{paisId}/ciudades")
    public List<Ciudad> getCiudadesByPais(@PathVariable Integer paisId) {
        return paisService.listarCiudadesPorPais(paisId);
    }
}