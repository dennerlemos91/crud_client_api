package br.com.crudclient.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JWTUtil {

    static final long EXPIRATION_TIME = 86400000;
    static final String SECRET = "JWtlincegpsbrasil";

    public String generateToken(String username, Set<String> permissoes) {
        Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("username", username);
        additionalInfo.put("permissoes", permissoes);

        return Jwts.builder()
                .setSubject(username)
                .setClaims(additionalInfo)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
    }

    public boolean tokenValido(String token) {
        Claims claims = getClaim(token);
        if(Objects.nonNull(claims)) {
            final var username = claims.get("username");
            final var expritaionDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if(Objects.nonNull(username) && Objects.nonNull(expritaionDate) && now.before(expritaionDate)) {
                return true;
            }
        }
        return false;
    }

    private Claims getClaim(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
            return null;
        }

    }

    public String getUsername(String token) {
        Claims claims = getClaim(token);
        if(Objects.nonNull(claims)) {
            return claims.get("username").toString();
        }
        return null;
    }
}
