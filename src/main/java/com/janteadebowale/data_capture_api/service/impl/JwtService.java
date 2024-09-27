package com.janteadebowale.data_capture_api.service.impl;

import com.janteadebowale.data_capture_api.model.User;
import com.janteadebowale.data_capture_api.util.DataCaptureUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**********************************************************
 2024 Copyright (C),  JTA                                         
 https://www.janteadebowale.com | jante.adebowale@gmail.com                                     
 **********************************************************
 * Author    : Jante Adebowale
 * Project   : data-capture-service
 * Package   : com.janteadebowale.data_capture_api.service
 **********************************************************/
@Service
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private int accessTokenExpiration;
    @Value("${application.security.jwt.refresh.expiration}")
    private int refreshTokenExpiration;


    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public String generateToken(User userDetails) {
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("name",userDetails.getFirstname() + " " + userDetails.getLastname());
        objectObjectHashMap.put("role",userDetails.getRole().toString());
        return generateToken(objectObjectHashMap, userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }



    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims,userDetails,accessTokenExpiration);
    }

    public String generateRefreshToken( UserDetails userDetails) {
        return buildToken(new HashMap<>(),userDetails,refreshTokenExpiration);
    }

    public String buildToken(Map<String, Object> extraClaims, UserDetails userDetails,int expiration){
        Date expirationTime = DataCaptureUtil.createLifeSpanFromNowInSeconds(expiration);
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(DataCaptureUtil.getCurrentDate("Africa/Lagos"))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .setExpiration(expirationTime)
                .setIssuer("JTA")
                .compact();
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
