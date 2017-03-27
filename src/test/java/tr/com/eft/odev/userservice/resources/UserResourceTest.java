package tr.com.eft.odev.userservice.resources;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import tr.com.eft.odev.userservice.AbstractIntegrationTest;
import tr.com.eft.odev.userservice.api.User;

public class UserResourceTest extends AbstractIntegrationTest {

	@Test
	public void createUserTest() {
		createUser("ali", "uzman");
	}

	@Test
	public void listUserTest() {
		User user = createUser("mehmet", "uzman");
		assertThat(listUsers()).contains(user);
	}

	@Test
	public void getUserTest() {
		User user = createUser("mehmet", "uzman");
		assertThat(resource.client().target(USERS_URL + "/" + user.getId()).request().get(User.class)).isEqualTo(user);
	}

	@Test
	public void updateUserTest() {
		User user = createUser("kemal", "uzman");
		user.setJobTitle("kidemli");
		assertThat(resource.client().target(USERS_URL + "/" + user.getId()).request().put(Entity.json(user))
				.getStatusInfo()).isEqualTo(Status.OK);
		assertThat(listUsers()).contains(user);
	}

	@Test
	public void deleteUserTest() {
		User user = createUser("kaan", "kidemli");
		assertThat(resource.client().target(USERS_URL + "/" + user.getId()).request().delete().getStatusInfo())
				.isEqualTo(Response.Status.NO_CONTENT);
		assertThat(resource.client().target(USERS_URL + "/" + user.getId()).request().get().getStatusInfo())
				.isEqualTo(Response.Status.NOT_FOUND);
	}

	private Collection<User> listUsers() {
		return Arrays.asList(resource.client().target(USERS_URL).request().get(User[].class));
	}

}
