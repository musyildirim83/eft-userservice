package tr.com.eft.odev.userservice.api;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "users")
@NamedQueries({ @NamedQuery(name = "tr.com.eft.odev.userservice.api.User.findAll", query = "SELECT p FROM User p") })
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5895095793173022631L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "fullName")
	private String fullName;

	@Column(name = "jobTitle")
	private String jobTitle;

	@Column(name = "luc")
	@Version
	private int version;

	public User() {
	}

	public User(String fullName, String jobTitle) {
		this.fullName = fullName;
		this.jobTitle = jobTitle;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}