package com.io.libraryproject.security.jwt;

import com.io.libraryproject.exception.message.ErrorMessage;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${library.app.jwtSecret}")
    private String jwtSecret ;
    @Value("${library.app.jwtExpirationMs}")
    private Long jwtExpirationMs ;


    public String generateJwtToken(UserDetails userDetails){
        return Jwts.builder().
                setSubject(userDetails.getUsername()).
                setIssuedAt(new Date()).
                setExpiration(new Date(new Date().getTime() + jwtExpirationMs)).
                signWith(SignatureAlgorithm.HS512, jwtSecret).
                compact();
    }


    public String getEmailFromToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).
                parseClaimsJws(token).
                getBody().
                getSubject();
    }



    public boolean validateJwtToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            logger.error(String.format(
                    ErrorMessage.JWTTOKEN_ERROR_MESSAGE, e.getMessage()));
        }
        return false;
    }

}
