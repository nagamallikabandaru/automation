package com.helpchat.tests.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the user_addresses database table.
 * 
 */
@Entity
@Table(name="user_addresses")
//@NamedQuery(name="UserAddress.findAll", query="SELECT u FROM UserAddress u")
public class UserAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String address;

	@Column(name = "city_id")
	private Integer cityId;
	
	@Column(name = "state_id",columnDefinition="tinyint")
	private Integer stateId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "is_default", columnDefinition = "tinyint(4)")
	private boolean isDefault;

	@Column(name = "last_name")
	private String lastName;

	private String locality;

	private int pincode;

	private String tag;

	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "mobile",columnDefinition="bigint(20) unsigned")
	private String mobile;

	@Column(name="latitude",columnDefinition="decimal(9,6)")
	private Double latitude;
	
	@Column(name="longitude",columnDefinition="decimal(9,6)")
	private Double longitude;
	
	@Column(name="city_alias")
	private String cityAlias;
	
	@Column(name = "deleted", columnDefinition = "tinyint(4)")
    private boolean deleted;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public boolean getIsDefault() {
		return this.isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLocality() {
		return this.locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public int getPincode() {
		return this.pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	/**
     * @param deleted the deleted to set
     */
    public void setDeleted(boolean deleted) {
      this.deleted = deleted;
    }
    
    /**
     * @return the deleted
     */
    public boolean isDeleted() {
      return deleted;
    }

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getCityAlias() {
		return cityAlias;
	}

	public void setCityAlias(String cityAlias) {
		this.cityAlias = cityAlias;
	}
}