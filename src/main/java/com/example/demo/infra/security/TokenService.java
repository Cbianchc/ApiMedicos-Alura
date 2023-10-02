package com.example.demo.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String apiSecret;

    public String generarToken(Usuario usuario){

        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("voil med")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(fechaExpiracion())
                    .sign(algorithm);
        } catch (JWTVerificationException exception){
            throw  new RuntimeException("error al generar el  token jwt", exception);
        }
    }

    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("voil med")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {

        }
        if (verifier.getSubject() == null) {
            throw new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();
    }

    private Instant fechaExpiracion() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));}


}
