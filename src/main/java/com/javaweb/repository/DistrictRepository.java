package com.javaweb.repository;

import java.util.List;
import java.util.Map;

import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
public interface DistrictRepository {
	DistrictEntity findNameById(Integer districtid);
}
