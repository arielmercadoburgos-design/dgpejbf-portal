package dgpejbf.portal.web.rest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetabaseController {

    private static final String METABASE_SECRET_KEY = "20c9b2a410e0c08e87a8fd1ecf61edf9811ecd38e76734e5764e753505c82ee3";
    private static final String METABASE_SITE_URL = "http://10.1.90.240:3000";

    @GetMapping("/api/metabase-url")
    public String getMetabaseEmbedUrl() throws Exception {
        long exp = (System.currentTimeMillis() / 1000) + (10 * 60); // 10 min

        String payload = String.format("{\"resource\":{\"dashboard\":5},\"params\":{},\"exp\":%d}", exp);

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(METABASE_SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        String token = Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(sha256_HMAC.doFinal(payload.getBytes(StandardCharsets.UTF_8)));

        return METABASE_SITE_URL + "/embed/dashboard/" + token + "#bordered=true&titled=true";
    }
}
