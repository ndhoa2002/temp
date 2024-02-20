package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.mysql.cj.log.Log;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository{
	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
	static final String USER = "root";
	static final String PASS = "callofduty12345";
	
	@Override
	public List<BuildingEntity> findAll(String name, Long districtId) {
		// TODO Auto-generated method stub		
		StringBuilder sql = new StringBuilder("SELECT * FROM building b where 1 = 1 ");
		if(name != null && !name.equals("")) {
			sql.append("AND b.name like '%" + name + "%' ");
		}
		if(districtId != null) {
			sql.append("AND b.districtid = " + districtId + " ");
		}
		List<BuildingEntity> result = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString());) {
			while(rs.next()) {
				BuildingEntity buildingDTO = new BuildingEntity();
				buildingDTO.setName(rs.getString("name"));
				buildingDTO.setStreet(rs.getString("street"));
				buildingDTO.setWard(rs.getString("ward"));
				buildingDTO.setNumberOfBasement(rs.getInt("numberofbasement"));
				result.add(buildingDTO);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("Connected database failed...");
		}
		return result;
	}
	
}
