package uz.pdp.app6contactcompany.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.app6contactcompany.entity.Role;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {

    private final long expireTime = 7200_000; //twenty hours

    private final String secretWord = "ThisIsMySecretWordAndNoBodyKnowsIt";

    public String generateToken(String username, Set<Role> roles){

        Date expireDate = new Date(System.currentTimeMillis() + expireTime);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("roles", roles)
                .signWith(SignatureAlgorithm.HS512, secretWord)
                .compact();

        return token;
    }

    public String getUsernameFromToken(String token){
        String username;
        try {
            username = Jwts
                    .parser()
                    .setSigningKey(secretWord)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return username;
        }catch (Exception e){
            return null;
        }
    }

}
