package hello.lunchback.login.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtProvider {

    @Value("${jwt.secret_key}")
    private String secretKey;


    public String create(UserDetails user) {

        List<String> roles = getRoles(user);

        //클레임 생성
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("Roles",roles);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(1,ChronoUnit.HOURS)))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

    }




    private List<String> getRoles(UserDetails user) {
        return user.getAuthorities()
                .stream()
                .map(grant -> grant.getAuthority())
                .toList();
    }

    public Map<String,Object> validate(String token) {
        Map<String, Object> list = new HashMap<>();
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            String email = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);
            list.put("email",email);
            list.put("roles",roles);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
