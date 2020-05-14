package org.springframework.samples.controller;

public interface Authenticator {

	void authenticate(LoginCommand loginCommand);

}
