package dgpejbf.portal.service.metabase;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.Instant;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MetabaseService {

    @Value("${metabase.site-url}")
    private String metabaseSiteUrl;

    @Value("${METABASE_JWT_SECRET}")
    private String metabaseSecretKey;

    // 1. INYECTAR LA UTILIDAD DE CIFRADO
    @Autowired
    private EncryptionUtil encryptionUtil;

    public String generarUrlEmbed(String tipo, int id) throws Exception {
        JSONObject payload = new JSONObject();
        // 1. Calcular tiempo de expiración (ej. 5 minutos a partir de ahora)
        long expirationTimeSeconds = Instant.now().plusSeconds(60).getEpochSecond(); // 1 minuto
        JSONObject resource = new JSONObject();
        resource.put(tipo, id);
        payload.put("resource", resource);
        payload.put("params", new JSONObject());
        // 2. Añadir el claim 'exp' al payload
        payload.put("exp", expirationTimeSeconds);
        String token = createSignedToken(payload.toString());
        return metabaseSiteUrl + "/embed/" + tipo + "/" + token + "#bordered=false&titled=false";
    }

    private String createSignedToken(String payloadJson) throws Exception {
        String header = new JSONObject().put("alg", "HS256").put("typ", "JWT").toString();

        String headerEncoded = Base64.getUrlEncoder().withoutPadding().encodeToString(header.getBytes(StandardCharsets.UTF_8));
        String payloadEncoded = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
        String unsignedToken = headerEncoded + "." + payloadEncoded;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(metabaseSecretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        String signature = Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(mac.doFinal(unsignedToken.getBytes(StandardCharsets.UTF_8)));
        return unsignedToken + "." + signature;
    }
}
