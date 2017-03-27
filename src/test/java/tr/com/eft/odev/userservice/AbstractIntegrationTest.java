package tr.com.eft.odev.userservice;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.testing.junit.DAOTestRule;
import io.dropwizard.testing.junit.ResourceTestRule;
import tr.com.eft.odev.userservice.api.User;
import tr.com.eft.odev.userservice.repository.UserDAO;
import tr.com.eft.odev.userservice.resources.UserResource;

public abstract class AbstractIntegrationTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractIntegrationTest.class);
	public static final String USERS_URL = "/userservice/users";

	@BeforeClass
	public static void setup() throws Exception {
		LOGGER.info("setup");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		LOGGER.info("tearDown");
	}

	@ClassRule
	public static DAOTestRule daoTestRule = DAOTestRule.newBuilder().addEntityClass(User.class).build();

	@ClassRule
	public static ResourceTestRule resource = ResourceTestRule.builder().addResource(new UserResource(getUserDao()))
			.build();

	private static UserDAO getUserDao() {
		return new UserDAO(daoTestRule.getSessionFactory());
	}

	public static User createUser(String fullName, String jobTitle) {
		User user = new User(fullName, jobTitle);
		Response response = resource.client().target(USERS_URL).request().post(Entity.json(user));
		assertThat(response.getStatusInfo()).isEqualTo(Status.CREATED);
		return response.readEntity(User.class);
	}
}
