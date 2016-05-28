package hr.aportolan.dto;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import hr.aportolan.domain.Message;
import hr.aportolan.domain.User;
import hr.aportolan.exceptions.StandardException;

public class RequestObject<T> extends RequestResponseObject<T> {

	private int offset;
	private int limit;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void amIValid(ValidationType validationType) {
		switch (validationType) {
		case SAVE_MESSAGES:
		case DELETE_MESSAGES: {
			checkIfListIsValid();
			if (!(((List<?>) payload).get(0) instanceof Message))
				throw new StandardException("Payload type is invalid!");
			break;
		}
		case SAVE_ALL_MESSAGES: {
			if (!(payload instanceof Message))
				throw new StandardException("Payload type is invalid!");
			User user = ((Message) payload).getUser();
			if (user.getUid() == 0 && user.getTag() == null)
				throw new StandardException("User in payload must have uid or tag defined!");
			break;
		}
		case GET_BY_USER_MESSAGES: {
			if (!(payload instanceof Message))
				throw new StandardException("Payload type is invalid!");
			User user = ((Message) payload).getUser();
			if (user.getUid() == 0)
				throw new StandardException("User in payload must have uid defined!");
			break;
		}

		case GET_USERS:

			break;
		case DELETE_USERS:
		case SAVE_ALL_USERS:
			checkIfListIsValid();

			break;
		case SAVE_USERS: {
			if (!(payload instanceof User))
				throw new StandardException("Payload type is invalid!");
			if (((User) payload).getUid() == 0)
				throw new StandardException("User in payload must have uid defined!");
			break;
		}

		default:
			break;
		}
	}

	private void checkIfListIsValid() {
		if (payload instanceof List<?>) {
			if (((List<?>) payload) == null || ((List<?>) payload).isEmpty())
				throw new StandardException("Payload is null or empty!");

		}
		throw new StandardException("Invalid payload in request!");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}
}
