package com.helpchat.tests.chat.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.helpchat.tests.entities.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
	@Query(value = "from UserAddress ua where ua.userId=:userId and ua.id=:addressId and ua.deleted = false")
	public UserAddress getUserAddress(@Param("userId") Long userId,@Param("addressId") Long addressId);

}
