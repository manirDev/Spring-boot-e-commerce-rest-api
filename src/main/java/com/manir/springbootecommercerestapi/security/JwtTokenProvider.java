package com.manir.springbootecommercerestapi.security;

import com.manir.springbootecommercerestapi.exception.EcommerceApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationTime;

    //generate token method

    public String generateToken(Authentication authentication){
        String userName = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationTime);

        String token = Jwts.builder()
                                    .setSubject(userName)
                                    .setIssuedAt(new Date())
                                    .setExpiration(expireDate)
                                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                                    .compact();

        return token;
    }

    //get username from the token, retrieve username from generated token
    public String getUserNameFromToken(String token){
        Claims claims = Jwts.parser()
                                    .setSigningKey(jwtSecret)
                                    .parseClaimsJws(token)
                                    .getBody();

        return claims.getSubject();
    }

    //validate JWT token
    public boolean validateToken(String token){
            try {
                Jwts.parser()
                            .setSigningKey(jwtSecret)
                            .parseClaimsJws(token);
                return true;
            }catch (SignatureException e){
                throw new EcommerceApiException("Invalid JWT signature", HttpStatus.BAD_REQUEST);
            }catch (MalformedJwtException e){
                throw new EcommerceApiException("Invalid JWT token", HttpStatus.BAD_REQUEST);
            }catch (ExpiredJwtException e){
                throw new EcommerceApiException("Expired JWT token", HttpStatus.BAD_REQUEST);
            }catch (UnsupportedJwtException e){
                throw new EcommerceApiException("Unsupported JWT token", HttpStatus.BAD_REQUEST);
            }catch (IllegalArgumentException e){
                throw new EcommerceApiException("JWT claims string is empty", HttpStatus.BAD_REQUEST);
            }
    }
}
