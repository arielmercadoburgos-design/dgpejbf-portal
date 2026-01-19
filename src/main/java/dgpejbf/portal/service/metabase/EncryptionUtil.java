package dgpejbf.portal.service.metabase;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtil {

    private final SecretKeySpec secretKey;

    public EncryptionUtil(@Value("${CIFRADO_CLAVE}") String secret) {
        // 1. DECÓDIFICAR la cadena Base64 a bytes binarios
        byte[] keyBytes = Base64.getDecoder().decode(secret);

        // 2. Ahora sí, verificar la longitud de los bytes binarios decodificados
        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("La clave Base64 decodificada no tiene 32 bytes. Tiene: " + keyBytes.length);
        }

        // 3. Usar los bytes decodificados para crear la clave secreta
        this.secretKey = new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(String strToEncrypt) throws Exception {
        // La lógica de cifrado se mantiene igual, ya que usa la clave binaria correcta
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8));

        // Devolvemos el resultado cifrado en formato Base64
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
