package com.papa.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

@Configuration
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire}")
    private Long expireTime;

    private static String CLAIM_SUB_PRE="sub";
    private static String CLAIM_CREATE_PRE="created";
    public String generateToken(Claims claims){
        return Jwts.builder().setClaims(claims).
                setExpiration(getDateByExpireTime(expireTime))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();

    }
    public String generateToken(UserDetails userDetails){
        Claims claims=new DefaultClaims();
        claims.put(CLAIM_SUB_PRE,userDetails.getUsername());
        claims.put(CLAIM_CREATE_PRE,new Date());
        return generateToken(claims);
    }

    private Date getDateByExpireTime(Long expireTime){
        return new Date(System.currentTimeMillis()+expireTime*1000);
    }


    public Claims getClaimsByToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    public String getUserNameByToken(String token){
        Claims claims=getClaimsByToken(token);
        return claims.getSubject();
    }
    public Date getExpireByToken(String token){
        Claims claims=getClaimsByToken(token);
        return claims.getExpiration();
    }
    private boolean isExpired(String token){
        Date date=getExpireByToken(token);
        return date.before(new Date());
    }
    public boolean validateToken(String token,UserDetails userDetails){

        return getUserNameByToken(token).equals(userDetails.getUsername())&&!isExpired(token);
    }

}
