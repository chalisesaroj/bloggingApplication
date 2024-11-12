package com.udemy.learn.blogging.configuration;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.udemy.learn.blogging.exception.BlogAPIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	private String jwtSecret = "daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb";

	private long jwtExpirationDate = 60480000;

	public String generateToken(Authentication auth) {
		String username = auth.getName();
		Date currentdate = new Date();
		Date exiprydate = new Date(currentdate.getTime() + jwtExpirationDate);

		String token = Jwts.builder().setSubject(username).setIssuedAt(currentdate).setExpiration(exiprydate)
				.signWith(getkey()).compact();
		return token;

	}

	public Key getkey() {
		String key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)).toString();
		System.out.println("key is  " + key);
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	public String getUsername(String token) {
		Claims claims = Jwts.parser().setSigningKey(getkey()).build().parseClaimsJws(token).getBody();
		String username = claims.getSubject();
		return username;
	}

	public boolean validatetoken(String token) {
		try {
			System.out.println("the token is" + token);
			Jwts.parser().setSigningKey(getkey()).build().parse(token);
			return true;
		} catch (MalformedJwtException e) {
			
			throw new BlogAPIException("Invalid JWT token ::" + e.getMessage());
			
		} catch (UnsupportedJwtException e) {
			throw new BlogAPIException("Unsupported JWT token");
			
		} catch (IllegalArgumentException e) {
			throw new BlogAPIException(" JWT token is empty");
			
		}
	}
}
