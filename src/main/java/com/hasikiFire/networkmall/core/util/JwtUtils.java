package com.hasikiFire.networkmall.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * JWT 工具类
 *
 * @author hasikiFire
 * @date 2024/6/4
 */
@ConditionalOnProperty("network.jwt.secret")
@Component
@Slf4j
public class JwtUtils {

    /**
     * 注入JWT加密密钥
     */
    @Value("${network.jwt.secret}")
    private String secret;

    /**
     * 定义系统标识头常量
     */
    private static final String HEADER_SYSTEM_KEY = "systemKeyHeader";
    // 7 天
    public static final long EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000;

    /**
     * 根据用户ID生成JWT
     *
     * @param uid       用户ID
     * @param systemKey 系统标识
     * @return JWT
     */
    public String generateToken(Integer uid) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setSubject(uid.toString())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    /**
     * 解析JWT返回用户ID
     *
     * @param token     JWT
     * @param systemKey 系统标识
     * @return 用户ID
     */
    public Long parseToken(String token, String systemKey) {
        Jws<Claims> claimsJws;
        try {
            claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            // OK, we can trust this JWT
            // 判断该 JWT 是否属于指定系统
            if (Objects.equals(claimsJws.getHeader().get(HEADER_SYSTEM_KEY), systemKey)) {
                return Long.parseLong(claimsJws.getBody().getSubject());
            }
        } catch (JwtException e) {
            log.warn("JWT解析失败:{}", token);
            // don't trust the JWT!
        }
        return null;
    }

}
