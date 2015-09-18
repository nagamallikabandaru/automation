package com.helpchat.tests.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="state_master")
public class StateMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private byte id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="state_name")
	private String stateName;

	//bi-directional many-to-many association to CityMaster
	@OneToMany(mappedBy="state")
	private List<CityMaster> cityMasters;

	public StateMaster() {
	}

	public byte getId() {
		return this.id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public List<CityMaster> getCityMasters() {
		return this.cityMasters;
	}

	public void setCityMasters(List<CityMaster> cityMasters) {
		this.cityMasters = cityMasters;
	}
}