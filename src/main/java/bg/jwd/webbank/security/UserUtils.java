package bg.jwd.webbank.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public final class UserUtils {

	private static final Logger logger = LoggerFactory.getLogger(UserUtils.class);

	private UserUtils() {
	}

	public static UserDetailsImpl getUser() {
		Object principal;

		try {
			principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (NullPointerException e) {
			logger.error("No logged user", e);
			return null;
		}

		if (principal == null || !(principal instanceof UserDetailsImpl)) {
			return null;
		}

		return (UserDetailsImpl) principal;
	}
}