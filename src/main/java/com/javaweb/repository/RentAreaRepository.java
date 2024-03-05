package com.javaweb.repository;

import java.util.List;
import java.util.Map;

import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.RentareaEntity;

public interface RentAreaRepository {
	List<RentareaEntity> getRentAreaByValue(Integer buildingid);
}
