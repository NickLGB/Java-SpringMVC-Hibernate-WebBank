package bg.jwd.webbank.dao;

import bg.jwd.webbank.entities.User;

public interface UserDao {

	User findByUsername(String username);
}
