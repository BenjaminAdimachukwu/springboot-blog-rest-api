package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwTokenProvider {

    @Value("${app.jwt-secret}")
    private  String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    public String generateToken(Authentication authentication){
       String username = authentication.getName();
       Date currentdate = new Date();
       Date expiredate = new Date(currentdate.getTime() + jwtExpirationDate);
       String token = Jwts.builder().subject(username).issuedAt(new Date())
               .expiration(expiredate).signWith(key()).compact();
              return token;
    }
    private Key key(){
      return   Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    };
    // get username from  JWT token
    public String getUsername( String token){
       return Jwts.parser()
               .verifyWith((SecretKey) key())
               .build()
               .parseSignedClaims(token)
               .getPayload().getSubject();

    }

    // validate Jwt token
    public boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
            return true;
        }catch (MalformedJwtException malformedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "invalid JWT token");
        }catch (ExpiredJwtException expiredJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        }catch (UnsupportedJwtException  unsupportedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException illegalArgumentException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Jwt claims string is null or empty");
        }
    }
}
