package com.javaweb.converter;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentareaEntity;

@Component
public class BuildingDTOConverter {
//	@Autowired
//	private DistrictRepository districtRepository;
//	@Autowired
//	private RentAreaRepository rentAreaRepository;
	@Autowired
	private ModelMapper modelMapper;
	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		BuildingDTO buildingDTO = modelMapper.map(item, BuildingDTO.class);
//		buildingDTO.setName(item.getName());
//		buildingDTO.setRentPrice(item.getRentprice());
//		buildingDTO.setServiceFee(item.getServiceFee());
//		buildingDTO.setBrokerageFee(item.getBrokerageFee());
//		buildingDTO.setNumberOfBasement(item.getNumberOfBasement());
//		buildingDTO.setManagerName(item.getManagername());
//		buildingDTO.setManagerPhoneNumber(item.getManagerphonenumber());
//		buildingDTO.setFloorArea(item.getFloorarea());
//		DistrictEntity districtEntity = item.getDistrict();
		buildingDTO.setAddress(item.getStreet() + ", " + item.getWard() + ", " + item.getDistrict().getName());
		
		
		List<RentareaEntity> rentareaEntities = item.getItems();
		StringJoiner rentArea = new StringJoiner(",");
		for(RentareaEntity re : rentareaEntities) {
			rentArea.add(re.getValue().toString());
		}
		String areaResult = rentareaEntities.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(","));
		
		buildingDTO.setRentArea(areaResult);
		
		return buildingDTO;
	}
}
