package com.sourav.banking.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

//    private static final Logger log = LoggerFactory.getLogger(JWTUtil.class);
    private final String secret_key="abcd123456";
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
    public String genrateUserToken(String username) throws Exception{
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512,secret_key)
                .compact();
    }

    public String extractUserName(String token){
        return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean checkVaid(String username, String token){
        return (username.equals(extractUserName(token)) && extractExpirationTime(token).before(new Date()));
    }

    public Date extractExpirationTime(String token){
        return Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody().getExpiration();
    }


}
