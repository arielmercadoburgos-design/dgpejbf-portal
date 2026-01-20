package dgpejbf.portal.web.rest;

import dgpejbf.portal.domain.secundaria.PejRaActualDirectivo;
import dgpejbf.portal.repository.secundaria.PejRaActualDirectivoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pej-ra-actual-directivo")
public class PejRaActualDirectivoResource {

    private final Logger log = LoggerFactory.getLogger(PejRaActualDirectivoResource.class);

    private final PejRaActualDirectivoRepository directivoRepository;

    public PejRaActualDirectivoResource(PejRaActualDirectivoRepository directivoRepository) {
        this.directivoRepository = directivoRepository;
    }

    /**
     * GET /directivos/ruc/:ruc : Obtener directivos por RUC.
     */
    @GetMapping("/directivos/ruc/{ruc}")
    public List<PejRaActualDirectivo> getDirectivosByRuc(@PathVariable Integer ruc) {
        log.debug("REST request para obtener directivos del RUC : {}", ruc);
        return directivoRepository.findByRuc(ruc);
    }
}