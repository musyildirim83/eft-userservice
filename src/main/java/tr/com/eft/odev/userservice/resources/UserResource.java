package tr.com.eft.odev.userservice.resources;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import tr.com.eft.odev.userservice.api.User;
import tr.com.eft.odev.userservice.repository.UserDAO;

@Path("/userservice/users")
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
	public Response getUser(@PathParam("userId") LongParam userId) {
		User user = userDAO.findById(userId.get());
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(user).build();
	}

	@GET
	@RolesAllowed("SELECT")
	@UnitOfWork
	public Response listUser() {
		return Response.ok(userDAO.findAll()).build();
	}

	@POST
	@RolesAllowed("INSERT")
	@UnitOfWork
	public Response createUser(@Valid User user) {
		return Response.status(Status.CREATED).entity(userDAO.create(user)).build();
	}

	@PUT
	@RolesAllowed("UPDATE")
	@Path("/{userId}")
	@UnitOfWork
	public Response updateUser(@PathParam("userId") LongParam userId, @Valid User user) {
		User updatedUser = userDAO.findById(userId.get());
		if (updatedUser == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		updatedUser.setFullName(user.getFullName());
		updatedUser.setJobTitle(user.getJobTitle());
		return Response.ok(userDAO.update(updatedUser)).build();
	}

	@DELETE
	@RolesAllowed("DELETE")
	@Path("/{userId}")
	@UnitOfWork
	public Response deleteUser(@PathParam("userId") LongParam userId) {
		User deletedUser = userDAO.findById(userId.get());
		if (deletedUser == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		userDAO.delete(deletedUser);
		return Response.noContent().build();
	}

}
