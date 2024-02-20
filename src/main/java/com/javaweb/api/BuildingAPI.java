package com.javaweb.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.model.BuildingDTO;
import com.javaweb.model.ErrorResponseDTO;
import com.javaweb.service.BuildingService;

import customexception.FieldRequiredException;

@RestController 
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
	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam(value = "name", required = false) String name,
										@RequestParam(value = "districtid", required = false) Long district,
										@RequestParam(value = "typeCode", required = false) List<String> typecode){
		List<BuildingDTO> result = buildingService.findAll(name, district);
		return result;
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
	
	@DeleteMapping(value="/api/building/{id}/{name}")
	public void deleteBuilding(@PathVariable("id") Integer idbuilding,
							   @PathVariable("name") String namebuilding,
							   @RequestParam(value="ward", required = false) String ward) {
		System.out.println("da xoa toa nha co id la " + idbuilding + " va name la " + namebuilding);
	}
}