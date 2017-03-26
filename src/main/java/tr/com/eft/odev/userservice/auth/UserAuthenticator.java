package tr.com.eft.odev.userservice.auth;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import tr.com.eft.odev.userservice.api.AuthUser;

public class UserAuthenticator implements Authenticator<BasicCredentials, AuthUser> {

	private static final Set<String> questRoles = ImmutableSet.of("SELECT");
	private static final Set<String> memberRoles = ImmutableSet.of("SELECT", "INSERT", "UPDATE", "DELETE");
	private static final Set<String> adminRoles = ImmutableSet.of("ADMIN");

	private static final Map<String, Set<String>> VALID_USERS = ImmutableMap.of("guest", questRoles, "mustafa",
			memberRoles, "admin", adminRoles);

	@Override
	public Optional<AuthUser> authenticate(BasicCredentials credentials) throws AuthenticationException {
		if (VALID_USERS.containsKey(credentials.getUsername()) && "secret".equals(credentials.getPassword())) {
			return Optional.of(new AuthUser(credentials.getUsername(), VALID_USERS.get(credentials.getUsername())));
		}
		return Optional.empty();
	}

}
