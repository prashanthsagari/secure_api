package com.custom.security;

import java.util.List;

import org.springframework.stereotype.Service;

import com.custom.security.exception.SecurityExceptionWithStatusCode;
import com.custom.security.util.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class SecurityService {

	private final HttpServletRequest request;

	private final HttpServletResponse response;

	public SecurityService(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public boolean checkRole(String requiredRole) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new SecurityExceptionWithStatusCode("Missing or invalid Authorization header" + requiredRole, 401);
		}

		String token = authHeader.substring(7); // Strip "Bearer "
		Claims claims = JwtUtil.getClaimsBody(token);

		@SuppressWarnings("unchecked")
		List<String> roles = claims.get("roles", List.class);

		System.out.println("hey access");
		boolean hasRole = roles.contains(requiredRole);
		if (!hasRole) {
			throw new SecurityExceptionWithStatusCode("Access Denied: Insufficient permissions for " + requiredRole, 403);
		}
		return true;
	}
}
