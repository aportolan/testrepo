package hr.aportolan.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import hr.aportolan.enums.CustomQueries;

@Entity
@NamedNativeQueries(value = {
		@NamedNativeQuery(name = CustomQueries.UPDATE_ALL_USER_MESSAGES_NAME, query = CustomQueries.UPDATE_ALL_USER_MESSAGES),
		@NamedNativeQuery(name = CustomQueries.UPDATE_ALL_USER_MESSAGES_BY_TAG_NAME, query = CustomQueries.UPDATE_ALL_USER_MESSAGES_BY_TAG),
		@NamedNativeQuery(name = CustomQueries.UPDATE_ALL_USER_MESSAGES_BY_UID_NAME, query = CustomQueries.UPDATE_ALL_USER_MESSAGES_BY_UID),
		@NamedNativeQuery(name = CustomQueries.UPDATE_ALL_USER_MESSAGES_BY_NAME_NAME, query = CustomQueries.UPDATE_ALL_USER_MESSAGES_BY_NAME) })
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@jsonMid")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Message {

	public Message() {
	}

	public Message(String title, String body, Date validity) {
		super();
		this.title = title;
		this.body = body;
		this.validity = validity;
	}

	public Message(String title, String body, Date validity, User user) {
		super();
		this.title = title;
		this.body = body;
		this.validity = validity;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	private long mid;

	@Column(nullable = false, unique = false)
	private String title;

	@Column(nullable = false, unique = false)
	private String body;

	@Column(nullable = false, unique = false)
	private Date validity;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "uid")
	private User user;

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (mid ^ (mid >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (mid != other.mid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("mid", mid);
		builder.append("title", title);
		builder.append("body", body);
		builder.append("validity", validity);
		if (user != null) {
			builder.append("user.uid", user.getUid());
			builder.append("user.name", user.getName());
			builder.append("user.tag", user.getTag());
		}
		return builder.toString();
	}

}
