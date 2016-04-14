package bg.jwd.webbank.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bg.jwd.webbank.entities.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public User findByUsername(String username) {

		Criteria criteria = sessionFactory.openSession().createCriteria(User.class);
		criteria.add(Restrictions.like("username", username));

		List<User> users = criteria.list();

		if (!users.isEmpty()) {
			return users.get(0);
		} else {
			return null;
		}
	}
}
