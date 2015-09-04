package com.helpchat.tests.masterdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.helpchat.tests.entities.CityMaster;

@Repository
public interface CityRepositoryTest extends JpaRepository<CityMaster, Integer> {

//@Query("from CityMaster cm where cm.cityName = :cityName")
//	@Param("cityName")
  public CityMaster findByCityName(String cityName) ;
	// TODO Auto-generated method stub

  
//public static void main(String args[]){
//
//	findByCityName("Bangalore");
//System.out.println("*****");
//
//}

}

