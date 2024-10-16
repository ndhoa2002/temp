package com.javaweb.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.model.BuildingRequestDTO;
import com.javaweb.model.ErrorResponseDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.service.BuildingService;

import customexception.FieldRequiredException;

@RestController 
@PropertySource("classpath:application.properties")
@Transactional
public class BuildingAPI {
	
//	@PostMapping(value="/api/building/")
//	public Object getBuilding(@RequestBody BuildingDTO buildingDTO) {
//		//xu ly duoi DB xong roi
////		try {
////			System.out.println(5/0);
////		} catch (Exception e) {
////			ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
////			errorResponseDTO.setError(e.getMessage());
////			List<String> details = new ArrayList<>();
////			details.add("Số nguyên làm sao chia hết cho số 0 được?");
////			errorResponseDTO.setDetaiList(details);
////			return errorResponseDTO;
////		}
////		List<BuildingDTO> listBuilding = new ArrayList<>();
////		BuildingDTO dto1 = new BuildingDTO();
////		dto1.setName("ABC building");
////		dto1.setNumberOfBasement(3);
////		dto1.setWard("Son Duong");
////		BuildingDTO dto2 = new BuildingDTO();
////		dto2.setName("Venus Tower");
////		dto2.setNumberOfBasement(5);
////		dto2.setWard("Mo Lao");
////		listBuilding.add(dto1);
////		listBuilding.add(dto2);
////		return listBuilding;
////		//System.out.println(name + " " + numberOfBasement);
////		try {
////			System.out.println(5/0);
////			valiDate(buildingDTO);
////		} catch (Exception e) {
////			// TODO: handle exception
////			ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
////			errorResponseDTO.setError(e.getMessage());
////			List<String> details = new ArrayList<>();
////			details.add("Check lại name hoặc numberofbasement đi bởi vì đang bị null đó!");
////			errorResponseDTO.setDetaiList(details);
////			return errorResponseDTO;
////		}
////		valiDate(buildingDTO);
////		return null;
//		
//		
//	}
	@Autowired
	private BuildingService buildingService;
	
	@Value("${name}")
	private String data;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	BuildingRepository buildingRepository;
	
	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam(required = false) Map<String, Object> params,
										@RequestParam(name="typeCode", required = false) List<String> typeCode){
		List<BuildingDTO> result = buildingService.findAll(params, typeCode);
		return result;
	}
	
	@GetMapping(value = "/api/building/{name}/{street}")
	public BuildingDTO getBuildingById(@PathVariable String name,
			@PathVariable String street){
		BuildingDTO result = new BuildingDTO();
		List<BuildingEntity> building = buildingRepository.findByNameContainingAndStreet(name, street);
		return result;
	}


	@GetMapping(value = "/api/building/{id}")
	public BuildingDTO getBuildingById(@PathVariable Integer id){
		BuildingDTO result = new BuildingDTO();
		BuildingEntity building = buildingRepository.findById(id).get();
		return result;
	}
	
	@PostMapping(value = "/api/building/")
	public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		DistrictEntity districtEntity = new DistrictEntity();
		districtEntity.setId(buildingRequestDTO.getDistrictId());
		buildingEntity.setDistrict(districtEntity);
		entityManager.persist(buildingEntity);
		System.out.println("OK");
	}
	
	@PutMapping(value = "/api/building/")
	public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
//		BuildingEntity buildingEntity = buildingRepository.findById(buildingRequestDTO.getId()).get();
//		buildingEntity.setId(1);
		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setStreet(buildingRequestDTO.getStreet());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		DistrictEntity districtEntity = new DistrictEntity();
		districtEntity.setId(buildingRequestDTO.getDistrictId());
		buildingEntity.setDistrict(districtEntity);
//		entityManager.merge(buildingEntity);
		buildingRepository.save(buildingEntity);
		System.out.println("OK");
	}
	
	
	
	
	public void valiDate(BuildingDTO buildingDTO){
		if(buildingDTO.getName() == null || buildingDTO.getName().equals("") || buildingDTO.getNumberOfBasement() == null) {
			throw new FieldRequiredException("name or numberofbasement is null");
		}
	}
	
//	@PostMapping(value="/api/building/")
//	public void getBuilding2(@RequestBody BuildingDTO buildingDTO) {
//		System.out.println("ok");
//	}
	
	@DeleteMapping(value="/api/building/{ids}")
	public void deleteBuilding(@PathVariable Integer[] ids) {
//		System.out.println("da xoa toa nha co id la " + idbuilding);
//		System.out.println(data);
//		BuildingEntity buildingEntity = entityManager.find(BuildingEntity.class, id);
//		entityManager.remove(buildingEntity);
		buildingRepository.deleteByIdIn(ids);
		
	}
}