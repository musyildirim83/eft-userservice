package tr.com.eft.odev.userservice.repository;

import java.util.List;
import java.util.Optional;

import org.hibernate.SessionFactory;

import io.dropwizard.hibernate.AbstractDAO;
import tr.com.eft.odev.userservice.api.User;

public class UserDAO extends AbstractDAO<User> {
	public UserDAO(SessionFactory factory) {
		super(factory);
	}

	public Optional<User> findById(Long id) {
		return Optional.ofNullable(get(id));
	}

	public User create(User user) {
		return persist(user);
	}

	public User update(User user) {
		System.out.println("asedf");
		currentSession().merge(user);
		currentSession().evict(user);
		return new User();
	}

	public void delete(User user) {
		currentSession().delete(user);
	}

	public List<User> findAll() {
		return list(namedQuery("tr.com.eft.odev.userservice.api.User.findAll"));
	}
}
