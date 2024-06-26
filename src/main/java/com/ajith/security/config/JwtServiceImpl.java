package com.ajith.security.config;

import com.ajith.security.exceptions.AuthHeaderNotContainExpectedTokenException;
import com.ajith.security.exceptions.UserNotFoundException;
import com.ajith.security.user.model.User;
import com.ajith.security.user.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtServiceImpl {

    @Value ( "${application.security.jwt.secret-key}" )
    private String secretKey;
    @Value ( "${application.security.jwt.expiration}" )
    private long jwtExpiration ;

    private final UserRepository userRepository;


    private  Key getSigningKey ( ) {
        byte[] keyBytes = Decoders.BASE64.decode (secretKey);
        return Keys.hmacShaKeyFor (keyBytes);
    }
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token has expired", e);
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid token signature", e);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing token", e);
        }
    }

    public String getUsernameFromToken(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String getRoleFromToken(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public String getUserIdFromToken(String token) {
        return extractAllClaims(token).get("userId", String.class);
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired (String token) {
        return extractExpiration(token).before(new Date ());
    }
    public  boolean isTokenValid (String token){
        try {
            Jwts.parser()
                    .setSigningKey(getSigningKey ())
                    .parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException e){
            throw new ExpiredJwtException(e.getHeader(),e.getClaims(),"Jwt token is expired");
        }
        catch (InvalidClaimException e){
            throw new JwtException("Jwt token is invalid");
        }
        catch (Exception e){
            throw new RuntimeException("Something is wrong with the jwt token validation");
        }

    }

    public  String generateToken(UserDetails userDetails){
        return generateToken ( new HashMap <> () ,userDetails );
    }
    public String generateToken(
            Map <String ,Object> extraClaims,
            UserDetails userDetails) {
        return buildToken ( extraClaims,userDetails,jwtExpiration );

    }
    private String buildToken(Map <String ,Object> extraClaims,
                              UserDetails userDetails, long expiration ) {
        Map<String, Object> claims = new HashMap <> (extraClaims);

        User currentUser = userRepository.findByEmail ( userDetails.getUsername () )
                .orElseThrow (()->new UserNotFoundException ("User email is not exist in the database while building the token"));

        claims.put ("role",currentUser.getRole ());
        claims.put ("userId", currentUser.getUserId () );
        return Jwts
                .builder ()
                .setClaims ( claims )
                .setSubject ( userDetails.getUsername () )
                .setIssuedAt ( new Date (System.currentTimeMillis ()) )
                .setExpiration ( new Date (System.currentTimeMillis () + expiration) )
                .signWith ( getSigningKey (), SignatureAlgorithm.HS256 )
                .compact ();
    }
    private String extractTokenFromAuthenticationHeader(String authHeader) {
        try{
            if(authHeader == null && !authHeader.startsWith ( "Bearer " )) {
                throw new AuthHeaderNotContainExpectedTokenException ("auth header null or not start with Bearer");
            }else
                return authHeader.substring ( 7 );
        }catch (AuthHeaderNotContainExpectedTokenException e){
            log.error ( e.getMessage () );
            throw new AuthHeaderNotContainExpectedTokenException(e.getMessage ());
        }
    }

    public  User extractUserFromAuthHeader (String authHeader){
        try{
            String token = extractTokenFromAuthenticationHeader ( authHeader );
            String userEmail = getUsernameFromToken ( token );
            return userRepository.findByEmail ( userEmail )
                    .orElseThrow (()->  new UserNotFoundException ( "User " + userEmail + " not found" ));
        }catch (UserNotFoundException e){
            log.error ( "fetching user with auth header failed" );
            throw new UserNotFoundException (e.getMessage ());
        }
    }

}
