package com.bookcomet.config;

import com.bookcomet.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String signaturekey;

    @Value("${jwt.experation}")
    private Long expiration;

    public String gerarToken(User user) {
        long expiracaoValue = Long.valueOf(expiration);

        // takes the current time and adds the minutes.
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expiracaoValue);

        // get timeExpiration with system zone convert and instant
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, signaturekey)
                .compact();
    }

}