package com.TaskManagement.TaskManagementServer.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {
    private static final SecretKey secretKey = Jwts.SIG.HS256.key().build();
    private static final String ISSUER = "arnabShi_auth_server";
    public boolean validateToken(String jwtToken){
        return parseToken(jwtToken).isPresent() && !isTokenExpired(jwtToken);
    }
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public Date getExpirationDateFromToken(String jwtToken){
        return getClaimFromToken(jwtToken, (Optional<Claims> t) -> t.get().getExpiration());
    }
    private Optional<Claims> parseToken(String jwtToken){
        try {
            return Optional.of(Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload());
        }
        catch (JwtException | IllegalArgumentException e){
            log.error("JWT exception occurred");
        }
        return Optional.empty();
    }
    public <T> T getClaimFromToken(String jwtToken, Function<Optional<Claims>, T> claimsResolver) {
        final Optional<Claims> claims = parseToken(jwtToken);
        return claimsResolver.apply(claims);
    }

    public String getUsernameFromToken(String jwtToken){
        System.out.println(jwtToken);
        return getClaimFromToken(jwtToken,(Optional<Claims> t)-> t.get().getSubject());
    }
    public String generateToken(String username) {
        var jwtExpirationInMinutes = 10;
        var expiration = 10*60*60;

        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuer(ISSUER)
                .setSubject(username)
                .signWith(secretKey)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }
}
