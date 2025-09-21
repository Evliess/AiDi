package evl.io.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import evl.io.constant.ServiceConstants;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.time.Duration;
import java.util.Base64;

public class RestUtils {
    public static String getUid(String code) {
        String appId = System.getenv(ServiceConstants.APP_ID);
        String appSecret = System.getenv(ServiceConstants.APP_SECRET);
        String url = ServiceConstants.UID_ENDPOINT.replace("${appid}", appId)
                .replace("${secret}", appSecret).replace("${code}", code);
        RestTemplate restTemplate = buildRestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JSONObject body = JSON.parseObject(response.getBody());
            assert body != null;
            return body.getString("openid");
        } catch (Exception e) {
            return "";
        }
    }

    private static RestTemplate buildRestTemplate() {
        return new RestTemplateBuilder().setConnectTimeout(Duration.ofMinutes(2L))
                .setReadTimeout(Duration.ofMinutes(2L)).build();
    }

    public static String encryptWithSalt(String strToEncrypt, String password) {
        try {
            byte[] salt = new byte[16];
            new java.security.SecureRandom().nextBytes(salt);

            // 生成密钥
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));

            // 组合salt和加密数据: salt + encryptedData
            byte[] combined = new byte[salt.length + encryptedBytes.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(encryptedBytes, 0, combined, salt.length, encryptedBytes.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            System.out.println("加密错误: " + e.toString());
        }
        return null;
    }

    public static String decryptWithSalt(String strToDecrypt, String password) {
        try {
            // 解码Base64
            byte[] combined = Base64.getDecoder().decode(strToDecrypt);

            // 分离salt和加密数据
            byte[] salt = new byte[16];
            byte[] encryptedBytes = new byte[combined.length - 16];
            System.arraycopy(combined, 0, salt, 0, 16);
            System.arraycopy(combined, 16, encryptedBytes, 0, encryptedBytes.length);

            // 生成密钥
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            // 解密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes, "UTF-8");
        } catch (Exception e) {
            System.out.println("解密错误: " + e.toString());
        }
        return null;
    }
}
