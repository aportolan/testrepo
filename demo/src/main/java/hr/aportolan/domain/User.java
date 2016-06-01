package hr.aportolan.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.jsondoc.core.annotation.ApiObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@ApiObject
@Entity
// TODO : tag je unique ili klasifikacija ??
// @Table(indexes = { @Index(columnList = "tag") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "messages" })
public class User {

	public User() {

	}

	public User(String name, String tag) {
		super();
		this.name = name;
		this.tag = tag;
	}

	public User(String name, String tag, List<Message> messages) {
		super();
		this.name = name;
		this.tag = tag;
		this.messages = messages;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	private long uid;
	@Column(nullable = false, unique = false)
	private String name;
	@Column(nullable = false, unique = false)
	private String tag;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "user")
	private List<Message> messages;

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (uid ^ (uid >>> 32));
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
		User other = (User) obj;
		if (uid != other.uid)
			return false;
		return true;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("uid", uid);
		builder.append("name", name);
		builder.append("tag", tag);
		return builder.toString();
	}
}
