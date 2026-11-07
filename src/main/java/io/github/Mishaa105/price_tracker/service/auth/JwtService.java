package io.github.Mishaa105.price_tracker.service.auth;

import io.github.Mishaa105.price_tracker.config.Config;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class JwtService
{
    private final SecretKey secretKey;

    public JwtService(Config config)
    {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(config.jwt().secretKey()));
    }

    public String generateJwt(String userId, String userRole)
    {
        Instant creationTime = Instant.now();
        Instant expirationTime = creationTime.plusSeconds(900);

        return Jwts.builder().subject(userId).claim("role", userRole)
                .issuedAt(Date.from(creationTime)).expiration(Date.from(expirationTime))
                .signWith(secretKey).compact();
    }
}