package dgpejbf.portal.web.rest;

import dgpejbf.portal.domain.secundaria.PejRaActual;
import dgpejbf.portal.repository.secundaria.PejRaActualRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

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
    public ResponseEntity<List<PejRaActual>> getAll(@PageableDefault(size = 20) Pageable pageable, UriComponentsBuilder uriBuilder) {
        Page<PejRaActual> page = repository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder, page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    // Obtener por ID
    @GetMapping("/pej-ra-actual/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<PejRaActual> getById(@PathVariable Integer id) {
        Optional<PejRaActual> maybe = repository.findById(id);
        return maybe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/pej-ra-actual/search")
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<List<PejRaActual>> search(
        @RequestParam(required = false) String ruc,
        @RequestParam(required = false) String razonSocial,
        @PageableDefault(size = 20) Pageable pageable,
        UriComponentsBuilder uriBuilder
    ) {
        // Use repository.findAll(pageable) and apply in-memory filtering to avoid requiring custom repository methods.
        Page<PejRaActual> page = repository.findAll(pageable);

        // If no filters provided, return page as-is.
        if ((ruc == null || ruc.isEmpty()) && (razonSocial == null || razonSocial.isEmpty())) {
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder, page);
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        }

        // Filter the page content in-memory (case-insensitive)
        List<PejRaActual> filtered = page
            .getContent()
            .stream()
            .filter(p -> {
                boolean matches = true;
                if (ruc != null && !ruc.isEmpty()) {
                    String val = p.getRuc() == null ? "" : p.getRuc().toString();
                    matches = val.toLowerCase().contains(ruc.toLowerCase());
                }
                if (matches && razonSocial != null && !razonSocial.isEmpty()) {
                    String val = p.getRazonSocial() == null ? "" : p.getRazonSocial();
                    matches = val.toLowerCase().contains(razonSocial.toLowerCase());
                }
                return matches;
            })
            .collect(Collectors.toList());

        Page<PejRaActual> filteredPage = new org.springframework.data.domain.PageImpl<>(filtered, pageable, filtered.size());
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder, filteredPage);

        return ResponseEntity.ok().headers(headers).body(filteredPage.getContent());
    }
}
