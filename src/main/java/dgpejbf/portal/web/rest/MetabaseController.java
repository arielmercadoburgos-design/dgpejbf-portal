package dgpejbf.portal.web.rest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetabaseController {

    private static final String METABASE_SECRET_KEY = "20c9b2a410e0c08e87a8fd1ecf61edf9811ecd38e76734e5764e753505c82ee3";
    private static final String METABASE_SITE_URL = "http://10.1.90.240:3000";

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/api/metabase-url")
    public ResponseEntity<Map<String, String>> getMetabaseEmbedUrl() throws Exception {
        long exp = (System.currentTimeMillis() / 1000) + (10 * 60); // 10 min

        Algorithm algorithm = Algorithm.HMAC256(METABASE_SECRET_KEY);
        String token = JWT.create()
            .withClaim(
                "resource",
                new java.util.HashMap<String, Object>() {
                    {
                        put("dashboard", 5);
                    }
                }
            )
            .withClaim("params", new java.util.HashMap<String, Object>())
            .withExpiresAt(new java.util.Date(exp * 1000))
            .sign(algorithm);

        String url = METABASE_SITE_URL + "/embed/dashboard/" + token + "#bordered=true&titled=true";

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(Map.of("url", url));
    }
}
