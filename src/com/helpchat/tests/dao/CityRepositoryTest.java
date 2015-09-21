package com.helpchat.tests.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.helpchat.tests.entities.CityMaster;



public interface CityRepositoryTest extends JpaRepository<CityMaster, Integer> {

  public CityMaster findByCityName(String cityName);

}

