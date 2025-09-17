package cn.cdut.ai.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JwtUtils {
    /**
     * 加密算法
     */
    private static final SecureDigestAlgorithm<SecretKey, SecretKey> ALGORITHM = Jwts.SIG.HS256;
    /**
     * 生成jwt
     * 使用Hs256算法, 私匙使用固定秘钥
     *
     * @param secretKey jwt秘钥串
     * @param seconds jwt过期时间(秒)
     * @param claims    设置的自定义信息负载
     * @return 生成的jwt令牌
     */
    public static String createJWT(String secretKey, int seconds, Map<String, Object> claims) {
        // 过期时间
        Date expireTime = Date.from(Instant.now().plusSeconds(seconds));
        // 秘钥
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .claims(claims)
                .expiration(expireTime)
                .issuedAt(new Date())
                .signWith(key, ALGORITHM)
                .compact();
    }

    /**
     * 解析token
     * @param secretKey jwt秘钥串
     * @param token jwt令牌
     * @return 负载信息
     */
    public static Claims parseJWT(String secretKey, String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
