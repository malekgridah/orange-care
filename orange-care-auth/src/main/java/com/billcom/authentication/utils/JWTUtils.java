package com.billcom.authentication.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Deprecated
@Component
public class JWTUtils {
    private static final String SECRET = "secret";

    @Deprecated
    public String generateToken(Authentication user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());

        log.info("Creating the JWT");
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getName())
                .setIssuer("cts.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    @Deprecated
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    @Deprecated
    public String extractUsername(String token) {
        log.info("Extracting the subject");
        return getClaims(token).getSubject();
    }

    @Deprecated
    public Boolean validateToken(String token) {
        log.info("Checking token validity");
        return !getClaims(token).getExpiration()
                .before(new Date());
    }
}
