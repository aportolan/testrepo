package hr.aportolan.dto;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jsondoc.core.annotation.ApiObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hr.aportolan.domain.Message;
import hr.aportolan.domain.User;
import hr.aportolan.exceptions.StandardException;

@ApiObject
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestObject<T> extends RequestResponseObject<T> {

	public RequestObject() {
	}

	public RequestObject(T payload, int offset, int limit) {
		super.payload = payload;
		this.offset = offset;
		this.limit = limit;
	}

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

		case DELETE_MESSAGES: {
			checkIfListIsValid();
			if (!(((List<?>) payload).get(0) instanceof Message))
				throw new StandardException("Payload type is invalid!");
			break;
		}
		case SAVE_MESSAGES: {
			if (!(payload instanceof Message))
				throw new StandardException("Payload type is invalid!");
			User user = ((Message) payload).getUser();
			if (user == null || user.getUid() == 0 && (user.getTag() == null || user.getTag().equals("")))
				throw new StandardException("User in payload must have uid or tag defined!");
			boolean body = (((Message) payload).getBody() == null || ((Message) payload).getBody().length() == 0);
			boolean titl = (((Message) payload).getTitle() == null || ((Message) payload).getTitle().length() == 0);
			boolean val = ((Message) payload).getValidity() == null;

			if (body || titl || val)
				throw new StandardException("Some fields are empty!");
			break;
		}
		case GET_BY_USER_MESSAGES: {
			if (!(payload instanceof Message))
				throw new StandardException("Payload type is invalid!");
			// User user = ((Message) payload).getUser();
			// boolean uidIn = user.getUid() != 0;
			// boolean nameIn = user.getName() != null &&
			// !user.getName().equals("");
			// boolean tagIn = user.getTag() != null &&
			// !user.getTag().equals("");
			// boolean invalid = (uidIn && nameIn) || (uidIn && tagIn) ||
			// (nameIn && tagIn);
			// if (invalid)
			// throw new StandardException("Only one search term can be
			// applicable (uid or name or tag)!");

			break;
		}
		case SAVE_ALL_MESSAGES:
		case GET_USERS:

			break;
		case DELETE_USERS:
		case SAVE_ALL_USERS:
			checkIfListIsValid();

			break;
		case SAVE_USERS: {
			if (!(payload instanceof User))
				throw new StandardException("Payload type is invalid!");
			boolean name = (((User) payload).getName() == null || ((User) payload).getName().length() == 0);
			boolean tag = (((User) payload).getTag() == null || ((User) payload).getTag().length() == 0);

			if (name || tag)
				throw new StandardException("Some fields are empty!");
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
			return;
		}
		throw new StandardException("Invalid payload in request!");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}
}
