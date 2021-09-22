package services

import com.google.common.io.BaseEncoding.base64
import jakarta.inject.Singleton
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Singleton
class JWT {
    var secret = System.getenv("SECRET")

    fun getJWT(username: String): String {
        var JWT_HEADER = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";
        var payload = JSONObject()
        val expAt = System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(30)
        val issuedAt = System.currentTimeMillis()

        payload.put("username", username)
        payload.put("exp", expAt)
        payload.put("issuedAt",issuedAt)

        var signature = hash(
            base64().encode(JWT_HEADER.toByteArray()) + "." + base64().encode(
                payload.toString().toByteArray()
            ), secret
        )
        var token = base64().encode(JWT_HEADER.toByteArray()) + "." + base64().encode(
            payload.toString().toByteArray()
        ) + "."+ signature
        println("generating token")
        return token
    }

    private fun hash(data: String, secret: String): String? {
        val hash: ByteArray =
            secret.toByteArray(StandardCharsets.UTF_8)
        val sha256Hmac = Mac.getInstance("HmacSHA256")
        val secretKey = SecretKeySpec(hash, "HmacSHA256")
        sha256Hmac.init(secretKey)
        val signedBytes = sha256Hmac.doFinal(data.toByteArray(StandardCharsets.UTF_8))
        return encode(signedBytes)
    }

    private fun encode(bytes: ByteArray): String {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes)
    }

    fun verifyToken(token :String):String
     {
         var token = token.split(" ")
         var tokenParts = token[1].split(".")
         var jwtHeader =tokenParts[0]
         var payload =tokenParts[1]
         var signature=tokenParts[2]

         if(signature.equals(hash(jwtHeader+ "." + payload, secret)))
         {
             payload=String(Base64.getUrlDecoder().decode(tokenParts[1]))
             val jsonPayload = JSONObject(payload)
             if(jsonPayload.getLong("exp") > System.currentTimeMillis())
             {
                 return "Verified"+jsonPayload
             }
         }
         return "Verificatio Error"
    }
}