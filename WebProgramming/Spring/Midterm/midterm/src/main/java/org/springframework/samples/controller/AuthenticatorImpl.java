package org.springframework.samples.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.model.PerformerInfo;
import org.springframework.samples.service.PerformerService;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatorImpl implements Authenticator {

	@Autowired
	private PerformerService performerService;
	
	@Override
	public void authenticate(LoginCommand loginCommand) {
		String email = loginCommand.getEmail();
		
		PerformerInfo realPerformer = performerService.getPerformerInfoByEmail(email);
		if (!realPerformer.matchPassword(loginCommand.getPassword())) {
			throw new AuthenticationException("not match password" + email);
		}
	}
	
}
