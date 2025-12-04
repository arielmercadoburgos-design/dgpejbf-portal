package dgpejbf.portal.service.metabase;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metabase")
public class MetabaseProxyConotrller {

    @Autowired
    private MetabaseService metabaseService;

    @Autowired // <-- NUEVA INYECCIÓN
    private EncryptionUtil encryptionUtil;

    @GetMapping("/embed")
    public ResponseEntity<?> obtenerUrlEmbed(
        @RequestParam String resource, // "dashboard" o "question"
        @RequestParam int id
    ) {
        try {
            String url = metabaseService.generarUrlEmbed(resource, id);
            // 1. Cifrar la URL generada
            String urlCifrada = encryptionUtil.encrypt(url); // <-- NUEVA LÍNEA

            // 2. Devolver la URL cifrada con un nuevo nombre de clave
            return ResponseEntity.ok(Map.of("iframeUrlCifrada", urlCifrada)); // <-- CLAVE CAMBIADA
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
        }
    }
}
