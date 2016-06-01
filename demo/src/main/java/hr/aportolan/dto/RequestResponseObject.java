package hr.aportolan.dto;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jsondoc.core.annotation.ApiObject;

@ApiObject
public abstract class RequestResponseObject<T> {
	protected T payload;

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);

	}
}
