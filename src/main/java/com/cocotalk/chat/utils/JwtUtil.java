package com.cocotalk.chat.utils;

import com.cocotalk.chat.dto.TokenPayload;
import com.cocotalk.chat.exception.CustomError;
import com.cocotalk.chat.exception.CustomException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;

@Slf4j
@Component
public class JwtUtil {
    private static String jwtSecret;

    @Value("${jwt.secret}")
    public void setJwtSecret(String secret) {
        jwtSecret = secret;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static TokenPayload getPayload(String accessToken) { // JWT Parsing
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
        try {
            TokenPayload payload = objectMapper.readValue(claims.getSubject(), TokenPayload.class);
            return payload;
        } catch (JacksonException e) {
            e.printStackTrace();
            log.error("[JwtUtil/getPayload] : Jwt Payload를 파싱하는 도중 문제가 발생했습니다.");
            throw new CustomException(CustomError.JSON_PARSE, e);
        }
    }

    public static boolean validateToken(String accessToken) {
        return getClaims(accessToken) != null;
    }

    private static Claims getClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (Throwable ex) {
            String message = "";
            if (ex.getClass() == NullPointerException.class) {
                message = "X-ACCESS-TOKEN 헤더가 설정되지 않았습니다.";
            }
            else if (ex.getClass() == ExpiredJwtException.class) {
                message = "만료된 토큰입니다.";
            } else if (ex.getClass() == MalformedJwtException.class ||
                    ex.getClass() == SignatureException.class ||
                    ex.getClass() == UnsupportedJwtException.class) {
                message = "올바르지 않은 형식의 토큰입니다.";
            } else if (ex.getClass() == IllegalArgumentException.class) {
                message = "헤더에 토큰이 포함되지 않았습니다.";
            } else {
                message = "알 수 없는 에러입니다.";
            }
            log.error("[JwtUtil/getClaims] : Jwt Payload를 파싱하는 도중 문제가 발생했습니다.");
            throw new CustomException(CustomError.JWT_AUTHENTICATION, message);
        }
    }
}