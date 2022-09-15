package com.t95.t95backend.utils.encryption;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.message.AuthException;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtils implements Serializable {
	
	private static final String SECRET = "T95, all in one wealth managment app.";

	//generate JWT token
	public String generateToken(HashMap<String, String> userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("name", userDetails.get("name"));
		claims.put("id", userDetails.get("id"));
	
		Instant now = Instant.now();	
	//	System.out.println(now);
		String jwtToken = Jwts.builder()
	        .setClaims(claims)
	        .setExpiration(Date.from(now.plus(100*24*70, ChronoUnit.MINUTES)))
	        .signWith(SignatureAlgorithm.HS256, SECRET)
	        .compact();	
	
		return jwtToken;
	};
	
	
	//Verify JWT and then parse user information
	public HashMap<String, Object> getJwtInfo(String token) throws AuthException {
		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
			HashMap<String, Object> expectedMap = new HashMap<>(claims);
			return expectedMap;
		} catch (SignatureException e) {
			throw new AuthException("Invalid JWT signature.");
		} catch (MalformedJwtException e) {
			throw new AuthException("Invalid JWT token.");
		} catch (ExpiredJwtException e) {
			throw new AuthException("Expired JWT token");
		} catch (UnsupportedJwtException e) {
			throw new AuthException("Unsupported JWT token");
		} catch (IllegalArgumentException e) {
			throw new AuthException("JWT token compact of handler are invalid");
		}
	}
	
	
}
