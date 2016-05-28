package hr.aportolan.dao;

import java.util.List;

import hr.aportolan.domain.User;

public interface CustomUserRepository {

	/**
	 * Selects users by any of the {@link User} properties
	 * 
	 * @param payload
	 *            {@link User}
	 * @param offset
	 *            starting row
	 * @param limit
	 *            max number of possible selected rows
	 * @return {@link List} of {@link User}
	 */
	List<User> selectUsersByCriteria(User payload, int offset, int limit);
}
