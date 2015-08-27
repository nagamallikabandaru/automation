package com.helpchat.tests.dao;

import com.helpchat.tests.entities.Merchants;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MerchantRegistrationDao extends JpaRepository<Merchants, Integer> {
	
	 /**
	 * 
	 */
	@Query("from Merchants m where m.mobile = :mobile and m.userType = :userType and m.status = 3")
	Merchants findByMobileAndUserType(@Param("mobile") final String mobile,
			@Param("userType") final Byte userType);
}
