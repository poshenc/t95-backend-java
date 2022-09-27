package com.t95.t95backend.utils.encryption;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.message.AuthException;

import org.springframework.stereotype.Component;

import com.t95.t95backend.returnBean.ReturnUserInfo;

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
	public String generateToken(ReturnUserInfo userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", userDetails.getName());
        claims.put("id", userDetails.getId());
	
		Instant now = Instant.now();	
		String jwtToken = Jwts.builder()
	        .setClaims(claims)
	        .setExpiration(Date.from(now.plus(100*24*70, ChronoUnit.MINUTES)))
	        .signWith(SignatureAlgorithm.HS256, SECRET)
	        .compact();	
	
		return jwtToken;
	};
	
	
	//Verify JWT and then parse user information
	public ReturnUserInfo getJwtInfo(String token) throws AuthException {
		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
			String name = claims.get("name").toString();
			Long id = ((Number) claims.get("id")).longValue();
			ReturnUserInfo expectedMap = new ReturnUserInfo(name, id);
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
