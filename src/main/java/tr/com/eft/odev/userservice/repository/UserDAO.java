package tr.com.eft.odev.userservice.repository;

import java.util.List;

import org.hibernate.SessionFactory;

import io.dropwizard.hibernate.AbstractDAO;
import tr.com.eft.odev.userservice.api.User;

public class UserDAO extends AbstractDAO<User> {
	public UserDAO(SessionFactory factory) {
		super(factory);
	}

	public User findById(Long id) {
		return get(id);
	}

	public User create(User user) {
		return persist(user);
	}

	public User update(User user) {
		return persist(user);
	}

	public void delete(User user) {
		currentSession().delete(user);
	}

	public List<User> findAll() {
		return list(namedQuery("tr.com.eft.odev.userservice.api.User.findAll"));
	}
}
