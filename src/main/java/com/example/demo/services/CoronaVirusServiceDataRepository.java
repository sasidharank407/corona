package com.example.demo.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.LocationStates;


@Repository

public interface CoronaVirusServiceDataRepository extends JpaRepository<LocationStates, Integer> 
{

	LocationStates findByCountry(String countryName);

}