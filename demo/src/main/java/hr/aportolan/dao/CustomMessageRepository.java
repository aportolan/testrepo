package hr.aportolan.dao;

import java.util.List;

import hr.aportolan.domain.Message;

public interface CustomMessageRepository {
	/**
	 * Deletes expired messages (where 'validity' is before current date)
	 */
	void deleteExpired();

	/**
	 * Saves message for all users (or by tag) by calling merge
	 * 
	 * @param payload
	 *            {@link Message}
	 */
	void saveByUserData(Message payload);

	/**
	 * Deletes all messages by by mid, uid or tag. Values of fields can be
	 * combined or independently set
	 * 
	 * @param message
	 *            {@link Message}
	 */
	void deleteByUser(Message message);

	/**
	 * Gets messages by mid, uid or tag. Values of fields can be combined or
	 * independently set
	 * 
	 * @param payload
	 *            {@link Message}
	 * @param offset
	 *            start row
	 * @param limit
	 *            number of possible rows returned
	 * @return {@link List} of {@link Message}
	 */
	List<Message> getByUser(Message payload, int offset, int limit);
}
