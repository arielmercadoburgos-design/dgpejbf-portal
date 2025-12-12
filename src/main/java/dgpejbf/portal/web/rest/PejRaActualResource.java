package dgpejbf.portal.web.rest;

import dgpejbf.portal.domain.secundaria.PejRaActual;
import dgpejbf.portal.repository.secundaria.PejRaActualRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PejRaActualResource {

    private final PejRaActualRepository repository;

    public PejRaActualResource(PejRaActualRepository repository) {
        this.repository = repository;
    }

    // Lista completa (podrías paginar en el futuro)
    @GetMapping("/pej-ra-actual")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public List<PejRaActual> getAll() {
        return repository.findAll();
    }

    // Obtener por ID
    @GetMapping("/pej-ra-actual/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<PejRaActual> getById(@PathVariable Integer id) {
        Optional<PejRaActual> maybe = repository.findById(id);
        return maybe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
