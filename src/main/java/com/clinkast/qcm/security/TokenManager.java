package com.clinkast.qcm.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Strings;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.clinkast.qcm.entities.User;


public class TokenManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenManager.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.token.validity:120}")
    private Integer tokenValidity;
    

    public Integer getTokenValidity() {
        return tokenValidity;
    }

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     * 
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */
    public AppUserDetails parseToken(String token) {
	try {
	    Claims body = Jwts.parser()
		    .setSigningKey(secret)
		    .parseClaimsJws(token)
		    .getBody();

	    User user = new User();
	    user.setLogin(body.getSubject());
	    user.setId((Integer)body.get("userId"));
	    user.setFirstname((String) body.get("identity"));

	    user.setRolesString(Strings.commaDelimitedListToSet((String)body.get("roles")));
	    //user.setRoles((ArrayList<Role>)body.get("roles"));           
	    AppUserDetails userDetails = new AppUserDetails(user);

	    return userDetails;

	} catch (JwtException | ClassCastException e) {
	    if(e instanceof ExpiredJwtException)
		LOGGER.debug("TokenManager error :  token has expired ", e);
	    else
	    LOGGER.debug("TokenManager error :  token is invalid ", e);
	}
	return null;
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     * 
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(AppUserDetails userDetails) {
	return generateTokenForExpiration(userDetails, tokenValidity);
    }



    public AppUserDetails removeToken(String token) {
	AppUserDetails userDetails = parseToken(token);
	//	if (userDetails != null) {
	//		tokens.remove(userDetails);
	//	}
	return userDetails;
    }
    
    /**
     * generate token for expiration time in hour
     * @param userDetails
     * @param expirationTime
     * @return the token
     */
    public String generateTokenForExpiration(AppUserDetails userDetails, int expirationTime) {
	Claims claims = Jwts.claims().setSubject(userDetails.getUser().getLogin());
	claims.put("identity", userDetails.getUser().getFirstname());
	claims.put("userId", userDetails.getUser().getId());
	String roles = 
		Strings.arrayToCommaDelimitedString(userDetails.getUser().getRolesString().toArray());
	claims.put("roles", roles);
	claims.setExpiration(new Date(System.currentTimeMillis() + (expirationTime * 60 * 60 * 1000)));
	claims.setId(UUID.randomUUID().toString());

	return Jwts.builder()
		.setClaims(claims)
		.signWith(SignatureAlgorithm.HS512, secret)
		.compact();
    }
}
