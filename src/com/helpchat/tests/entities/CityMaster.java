package com.helpchat.tests.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "city_master")
public class CityMaster implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private Integer id;

  @Column(name = "city_name")
  private String cityName;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  private Date createdAt;

  // bi-directional many-to-many association to StateMaster
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinTable(name = "state_city", joinColumns = @JoinColumn(name = "city_id"),
      inverseJoinColumns = @JoinColumn(name = "state_id"))
  private StateMaster state;

  @Column(name = "priority_display")
  private Byte priorityDisplay;

  public CityMaster() {}

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCityName() {
    return this.cityName;
  }

  public void setCityName(String cityName) {
    this.cityName = cityName;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Byte getPriorityDisplay() {
    return priorityDisplay;
  }

  public void setPriorityDisplay(Byte priorityDisplay) {
    this.priorityDisplay = priorityDisplay;
  }

  public StateMaster getState() {
    return state;
  }

  public void setState(StateMaster state) {
    this.state = state;
  }

}