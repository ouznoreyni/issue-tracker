package sn.noreyni.issuetrackerbackend.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import sn.noreyni.issuetrackerbackend.shared.Constants;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenService {
    @Value("${application.jwt.secret-key}")
    private static  String JWT_SECRET_KEY;

    @Value("${application.jwt.ttl-in-seconds}")
    private static  long JWT_TTL_KEY ;
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims;
        try {
            claims = extractAllClaims(token);
        } catch (Exception e) {
            log.debug("An error occur when extracting AllClaims: {}", e.getMessage());
            return null;
        }
        return claimsResolver.apply(claims);

    }

    public String generateToken(String login){
        try {
            Map<String,Object> claims=new HashMap<>();
            return createToken(claims,login);
        } catch (Exception e) {
            log.debug("An error occur when generation a token: {}", e.getMessage());
            return null;
        }

    }
    public String getToken(HttpServletRequest request){
        String tokenHeader = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if(tokenHeader != null){
            return tokenHeader.substring(Constants.JWT_AUTHORIZATION_TYPE.length());
        }
        return null;
    }

    public Date getTokenExpiration(String token){
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    public String purgedToken(HttpServletRequest request) {
        return resolveToken(request);
    }


    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(Constants.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constants.JWT_AUTHORIZATION_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String createToken(Map<String, Object> claims, String login) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(login)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TTL_KEY))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(JWT_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
