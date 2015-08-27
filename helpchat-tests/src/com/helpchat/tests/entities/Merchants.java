package com.helpchat.tests.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.helpchat.tests.enums.MerchantStatus;
import com.helpchat.tests.enums.MerchantType;

/**
 * @author <a href="mailto:sumved.shami@akosha.in">Sumved Shami</a>
 *
 */
@Entity
@Table(name = "agents")
public class Merchants extends Domain<Integer> implements Serializable {

  /**
     * 
     */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  private String name;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "mobile")
  private String mobile;

  @Column(name = "password")
  private String password;

  // TODO Change it to enum
  @Column(name = "user_type")
  private Byte userType;

  @Column(name = "email")
  private String email;

  @Column(name = "updated_by")
  private Integer updatedBy;

  // TODO Change it to enum
  @Column(name = "status")
  private Byte status;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "created_at")
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "updated_at")
  private Date updatedAt;

  @Column(name = "is_brand_user")
  private Byte isBrandUser;

  public Merchants() {

  }

  public Merchants(Merchants oldMerchant) {
    super();
    this.id = oldMerchant.id;
    this.name = oldMerchant.name;
    this.firstName = oldMerchant.firstName;
    this.lastName = oldMerchant.lastName;
    this.mobile = oldMerchant.mobile;
    this.password = oldMerchant.password;
    this.userType = oldMerchant.userType;
    this.email = oldMerchant.email;
    this.updatedBy = oldMerchant.updatedBy;
    this.status = oldMerchant.status;
    this.createdAt = oldMerchant.createdAt;
    this.updatedAt = oldMerchant.updatedAt;
    this.isBrandUser = oldMerchant.isBrandUser;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName the firstName to set
   */
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * @param lastName the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the mobile
   */
  public String getMobile() {
    return mobile;
  }

  /**
   * @param mobile the mobile to set
   */
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  /**
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * @param password the password to set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * @return the userType
   */
  public MerchantType getUserType() {

    return MerchantType.fromType(this.userType.intValue());
  }

  /**
   * @param userType the userType to set
   */
  public void setUserType(MerchantType merchantType) {
    this.userType = merchantType.getType().byteValue();
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the updatedBy
   */
  public Integer getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @param updatedBy the updatedBy to set
   */
  public void setUpdatedBy(Integer updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @return the status
   */
  public MerchantStatus getStatus() {
    return MerchantStatus.fromStatus(this.status.intValue());
  }

  /**
   * @param status the status to set
   */
  public void setStatus(MerchantStatus merchantStatus) {
    this.status = merchantStatus.getStatus().byteValue();
  }

  /**
   * @return the createdAt
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * @param createdAt the createdAt to set
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * @return the updatedAt
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * @param updatedAt the updatedAt to set
   */
  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Byte getIsBrandUser() {
    return isBrandUser;
  }

  public void setIsBrandUser(Byte isBrandUser) {
    this.isBrandUser = isBrandUser;
  }

}
