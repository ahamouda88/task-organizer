package mywork.task.organizer.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Version
	private long version;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	private int numOfVisits;
	@NotNull
	private String comment;
	@NotNull
	private String location;
	@NotNull
	private String name;
	@NotNull
	private Interval type;
	@NotNull
	private Date createdAt;
	@NotNull
	@JsonProperty("from")
	private Date start;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "task", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonIgnoreProperties(value = "task", allowSetters = true)
	private List<Visit> visits;

	public Task() {
	}

	public Task(int numOfVisits, String comment, String location, String name, Interval type, Date start) {
		this.numOfVisits = numOfVisits;
		this.comment = comment;
		this.location = location;
		this.name = name;
		this.type = type;
		this.createdAt = Calendar.getInstance().getTime();
		this.start = start;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumOfVisits() {
		return numOfVisits;
	}

	public void setNumOfVisits(int numOfVisits) {
		this.numOfVisits = numOfVisits;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Interval getType() {
		return type;
	}

	public void setType(Interval type) {
		this.type = type;
	}

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

}
