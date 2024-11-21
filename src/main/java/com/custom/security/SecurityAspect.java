package com.custom.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {

	private final SecurityService securityService;

	public SecurityAspect(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Before("@annotation(com.custom.security.annotations.AdminAccess)")
	public void enforceAdminAccess() {
		securityService.checkRole("ADMIN");
	}

	@Before("@annotation(com.custom.security.annotations.WriteAccess)")
	public void enforceWriteAccess() {
		securityService.checkRole("WRITE");
	}

	@Before("@annotation(com.custom.security.annotations.ReadAccess)")
	public void enforceReadAccess() {
		securityService.checkRole("USER");
	}
}
