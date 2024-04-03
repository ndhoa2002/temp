package com.javaweb.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
public interface DistrictRepository extends JpaRepository<DistrictEntity, Integer>{
	DistrictEntity findNameById(Integer districtid);
}
