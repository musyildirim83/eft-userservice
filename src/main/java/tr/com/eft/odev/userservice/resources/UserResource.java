package tr.com.eft.odev.userservice.resources;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import tr.com.eft.odev.userservice.api.User;
import tr.com.eft.odev.userservice.repository.UserDAO;

@Path("/userservice/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	private final UserDAO userDAO;

	public UserResource(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@GET
	@Path("/{userId}")
	@RolesAllowed("SELECT")
	@UnitOfWork
	public User getUser(@PathParam("userId") LongParam userId) {
		return findSafely(userId.get());
	}

	@GET
	@RolesAllowed("SELECT")
	@UnitOfWork
	public List<User> listUser() {
		return userDAO.findAll();
	}

	@POST
	@RolesAllowed("INSERT")
	@UnitOfWork
	public User createUser(@Valid User user) {
		return userDAO.create(user);
	}

	@PUT
	@RolesAllowed("UPDATE")
	@Path("/{userId}")
	@UnitOfWork
	public User updateUser(@PathParam("userId") LongParam userId, @Valid User user) {
		// User user2 = findSafely(userId.get());
		// user2.setFullName(user.getFullName());
		// user2.setJobTitle(user.getJobTitle());
		user.setId(userId.get());
		return userDAO.update(user);
	}

	@DELETE
	@RolesAllowed("DELETE")
	@Path("/{userId}")
	@UnitOfWork
	public void deleteUser(@PathParam("userId") LongParam userId) {
		userDAO.delete(findSafely(userId.get()));
	}

	private User findSafely(long userId) {
		return userDAO.findById(userId).orElseThrow(() -> new NotFoundException("No such user."));
	}

}
