package dgpejbf.portal.service;

import dgpejbf.portal.service.MetabaseEmbedService;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metabase")
public class MetabaseResource {

    private final MetabaseEmbedService metabaseEmbedService;

    public MetabaseResource(MetabaseEmbedService metabaseEmbedService) {
        this.metabaseEmbedService = metabaseEmbedService;
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("entities/dashboard/{id}")
    public Map<String, String> getDashboardUrl(@PathVariable Long id) {
        String embedUrl = metabaseEmbedService.generateSignedDashboardUrl(id, Map.of());
        return Map.of("url", embedUrl);
    }
}
