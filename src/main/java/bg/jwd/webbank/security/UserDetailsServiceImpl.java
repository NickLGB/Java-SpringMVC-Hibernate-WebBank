package bg.jwd.webbank.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import bg.jwd.webbank.dao.UserDao;
import bg.jwd.webbank.entities.User;

public final class UserDetailsServiceImpl implements UserDetailsService {

	public static final GrantedAuthority BANK_EMPLOYEE_AUTHORITY = new SimpleGrantedAuthority("ROLE_BANK_EMPLOYEE");
	private static final GrantedAuthority USER_AUTHORITY = new SimpleGrantedAuthority("ROLE_USER");

	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(final String username) {

		User user = userDao.findByUsername(username);

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(USER_AUTHORITY);

		if ("ROLE_BANK_EMPLOYEE".equals(user.getRole())) {
			authorities.add(BANK_EMPLOYEE_AUTHORITY);
		}

		return new UserDetailsImpl(user.getUsername(), user.getPassword(), authorities);
	}
}
