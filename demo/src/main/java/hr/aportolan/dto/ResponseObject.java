package hr.aportolan.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jsondoc.core.annotation.ApiObject;

@ApiObject
public class ResponseObject<T> extends RequestResponseObject<T> {

	public ResponseObject(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public ResponseObject(boolean success) {
		super();
		this.success = success;
		this.errorMessage = null;
	}

	public ResponseObject(boolean success, T payload) {
		super();
		this.payload = payload;
		this.success = success;
		this.errorMessage = null;
	}

	private boolean success;
	private final String errorMessage;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
