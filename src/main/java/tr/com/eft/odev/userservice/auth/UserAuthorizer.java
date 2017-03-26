package tr.com.eft.odev.userservice.auth;

import io.dropwizard.auth.Authorizer;
import tr.com.eft.odev.userservice.api.AuthUser;

public class UserAuthorizer implements Authorizer<AuthUser> {

	@Override
	public boolean authorize(AuthUser user, String role) {
		return user.getRoles() != null && user.getRoles().contains(role);
	}
}
