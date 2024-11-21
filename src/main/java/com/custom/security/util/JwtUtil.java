package com.custom.security.util;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

public class JwtUtil {

	private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	private static final String SECRET = "WZT7di2p46QeOL3rgkUzA4B9o9Oc0eZpZMVybqFIguneOoT6fJa82Jzttdn1LTpPmtjcNZFlTvw52YoBiQ";

	private static SecretKey key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
	}

	public static String extractUsernameFromToken(String token) {
		if (!validateJwtToken(token)) {
			return null;
		}
		return getClaims(token, Claims::getSubject);
	}

	public static <T> T getClaims(String token, Function<Claims, T> resolver) {
		return resolver.apply(Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload());
	}

	public static Claims getClaimsBody(String token) {
		return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload();
	}

	public static boolean validateJwtToken(String authToken) {
		try {
			Date expiration = getClaims(authToken, Claims::getExpiration);
			return !expiration.before(new Date()); // Returns true if invalid
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		} catch (SignatureException se) {
			logger.error("Modified JWT token: {}", se.getMessage());
		}
		return false;
	}

}