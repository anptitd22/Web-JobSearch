package com.project.webIT.helper;

import com.project.webIT.models.Admin;
import com.project.webIT.models.Company;
import com.project.webIT.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenHelper {

    @Value("${jwt.expiration}") //no lombok
    private long expiration;//save to an environment variable

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateTokenFromUser(User user){
        //properties -> claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("userId", user.getId());
        claims.put("role", user.getRole().getName());
        return createToken(claims, user.getEmail());
    }
    public String generateTokenFromCompany(Company company){
        Map<String, Object> claims = new HashMap<>();
        claims.put("account", company.getAccount());
        claims.put("companyId", company.getId());
        claims.put("role", company.getRole().getName());
        return createToken(claims, company.getAccount());
    }
    public String generateTokenFromAdmin(Admin admin){
        Map<String, Object> claims = new HashMap<>();
        claims.put("account", admin.getAccount());
        claims.put("adminId", admin.getId());
        claims.put("role", admin.getRole().getName());
        return createToken(claims, admin.getAccount());
    }
    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey); //FfyEJyyYieuIdNN99gm8ibEik38pjPBk7elv2qz4utE=
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractAllClaims (String token){
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim (String token, Function<Claims, T> claimsResolver){
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    //check expiration
    public boolean isTokenExpired (String token){
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    public String extractSubject(String token){
        return extractClaim(token, Claims::getSubject); //subject: email or account
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public boolean validateToken (String token, UserDetails userDetails){
        String subject = extractSubject(token);
        return (subject.equals(userDetails.getUsername()) &&
                !isTokenExpired(token)); //kiem tra subject va han su dung cua token
    }
    //ham tao key
//    private String generateSecretKey(){
//        SecureRandom random = new SecureRandom();
//        byte[] keyBytes = new byte[32];
//        random.nextBytes(keyBytes);
//        String secretKey = Encoders.BASE64.encode(keyBytes);
//        return secretKey;
//    }

}
