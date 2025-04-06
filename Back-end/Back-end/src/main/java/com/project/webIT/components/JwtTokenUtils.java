package com.project.webIT.components;

import com.project.webIT.exception.InvalidParamException;
import com.project.webIT.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {

    @Value("${jwt.expiration}") //no lombok
    private long expiration;//save to an environment variable

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(User user) throws Exception {
        //properties -> claims
        Map<String, Object> claims = new HashMap<>();
//        this.generateSecretKey();
        claims.put("email", user.getEmail());
        claims.put("userId", user.getId());
        return createToken(claims, user.getEmail());
    }
//    public String generateTokenFromOAuth2(OAuth2User oAuth2User)throws Exception {
//        Map<String, Object> claims = new HashMap<>();
//        String email = oAuth2User.getAttribute("email"); // Lấy email từ OAuth2 response
//        claims.put("email", email);
//        claims.put("name", oAuth2User.getAttribute("name")); // Thêm tên user
//        claims.put("provider", "oauth2"); // Đánh dấu user từ OAuth2
//        return createToken(claims, email);
//    }
    private String createToken(Map<String, Object> claims, String subject) throws Exception{
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            throw new InvalidParamException("Cannot create JWT token, error: " + e.getMessage());
        }
    }

    private Key getSignInKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey); //FfyEJyyYieuIdNN99gm8ibEik38pjPBk7elv2qz4utE=
        return Keys.hmacShaKeyFor(bytes);
    }
    //ham tao key
//    private String generateSecretKey(){
//        SecureRandom random = new SecureRandom();
//        byte[] keyBytes = new byte[32];
//        random.nextBytes(keyBytes);
//        String secretKey = Encoders.BASE64.encode(keyBytes);
//        return secretKey;
//    }

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

    public String extractEmail(String token){
        return extractClaim(token, Claims::getSubject); //subject: email
    }

    public boolean validateToken (String token, UserDetails userDetails){
        String email = extractEmail(token);
        return (email.equals(userDetails.getUsername()) &&
                !isTokenExpired(token)); //kiem tra email va han su dung cua token
    }
}
