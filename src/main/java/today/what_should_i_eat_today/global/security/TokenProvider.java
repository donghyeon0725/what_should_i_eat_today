package today.what_should_i_eat_today.global.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import today.what_should_i_eat_today.global.config.AppProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {

    private final AppProperties appProperties;

    public String createToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        // 권한을 ROLE_MEMBER 로 설정
        Map<String, Object> map = new HashMap<>();
        map.put("role", userPrincipal.getAuthorities().toArray()[0]);


        return Jwts.builder()
                .setClaims(map)
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public String createToken(UserPrincipal userPrincipal) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

        Map<String, Object> map = new HashMap<>();
        map.put("sub", userPrincipal.getId().toString()); // Claims 의 sub
        map.put("role", userPrincipal.getAuthorities().toArray()[0]);


        return Jwts.builder()
                .setClaims(map)
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public String createTestToken(UserPrincipal userPrincipal, long expirationMsec) {

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMsec);

        Map<String, Object> map = new HashMap<>();
        map.put("sub", userPrincipal.getId().toString()); // Claims 의 sub
        map.put("role", userPrincipal.getAuthorities().toArray()[0]);


        return Jwts.builder()
                .setClaims(map)
                .setSubject(userPrincipal.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public Claims getClaims(String token) {

        return Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
