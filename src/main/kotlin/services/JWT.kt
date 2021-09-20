package services

import com.google.common.io.BaseEncoding.base64
import jakarta.inject.Singleton
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Singleton
class JWT {
    fun getJWT(): String {
        var JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        var secret = "shh"
        var payload = JSONObject()
        var base: Base64
        payload.put("name", "oss")
        payload.put("id", 1)
        var signature = hmacSha256(
            base64().encode(JWT_HEADER.toByteArray()) + "." + base64().encode(
                payload.toString().toByteArray()
            ), secret
        )
        var token = base64().encode(JWT_HEADER.toByteArray()) + "." + base64().encode(
            payload.toString().toByteArray()
        ) + "."+ signature
        return token
    }

    private fun hmacSha256(data: String, secret: String): String? {

        //MessageDigest digest = MessageDigest.getInstance("SHA-256");
        val hash: ByteArray =
            secret.toByteArray(StandardCharsets.UTF_8) //digest.digest(secret.getBytes(StandardCharsets.UTF_8));
        val sha256Hmac = Mac.getInstance("HmacSHA256")
        val secretKey = SecretKeySpec(hash, "HmacSHA256")
        sha256Hmac.init(secretKey)
        val signedBytes = sha256Hmac.doFinal(data.toByteArray(StandardCharsets.UTF_8))
        return encode(signedBytes)
    }

    private fun encode(bytes: ByteArray): String {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }
}