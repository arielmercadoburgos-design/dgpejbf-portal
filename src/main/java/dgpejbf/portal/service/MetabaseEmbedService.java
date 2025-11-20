package dgpejbf.portal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MetabaseEmbedService {

    @Value("${metabase.secret-key}")
    private String secretKey;

    @Value("${metabase.site-url}")
    private String metabaseUrl;

    private static final ObjectMapper mapper = new ObjectMapper();

    public String generateSignedDashboardUrl(Long dashboardId, Map<String, Object> params) {
        try {
            long expiration = (System.currentTimeMillis() / 1000L) + 60 * 5; // 5 minutos

            Map<String, Object> payload = new HashMap<>();
            payload.put("resource", Map.of("dashboard", dashboardId));
            payload.put("params", params);
            payload.put("exp", expiration);

            String token = signJwt(payload);

            return metabaseUrl + "/embed/dashboard/" + token + "#bordered=true&titled=true";
        } catch (Exception e) {
            throw new RuntimeException("Error generating Metabase signed URL", e);
        }
    }

    private String signJwt(Map<String, Object> payload) throws Exception {
        String headerJson = mapper.writeValueAsString(Map.of("typ", "JWT", "alg", "HS256"));
        String payloadJson = mapper.writeValueAsString(payload);

        String header = Base64.getUrlEncoder().withoutPadding().encodeToString(headerJson.getBytes());
        String body = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJson.getBytes());
        String unsignedToken = header + "." + body;

        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(new SecretKeySpec(secretKey.getBytes(), "HmacSHA256"));
        byte[] signatureBytes = hmac.doFinal(unsignedToken.getBytes());
        String signature = Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);

        return header + "." + body + "." + signature;
    }
}
