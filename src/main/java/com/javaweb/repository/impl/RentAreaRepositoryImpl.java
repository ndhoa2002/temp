package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.repository.entity.RentareaEntity;
import com.javaweb.utils.ConnectionJDBCUtil;

public class RentAreaRepositoryImpl{
//	
//	@Override
//	public List<RentareaEntity> getRentAreaByValue(Integer buildingid) {
//		// TODO Auto-generated method stub
//		String sql = "SELECT * FROM rentarea where buildingid = " + buildingid;
//		List<RentareaEntity> result = new ArrayList<>();
//		try(Connection conn = ConnectionJDBCUtil.getConnection();
//				Statement stmt = conn.createStatement();
//				ResultSet rs = stmt.executeQuery(sql);) {
//			while(rs.next()) {
//				RentareaEntity rentareaEntity = new RentareaEntity();
//				rentareaEntity.setId(rs.getInt("id"));
//				rentareaEntity.setValue(rs.getInt("value"));
////				rentareaEntity.setBuildingid(rs.getInt("buildingid"));
//				result.add(rentareaEntity);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			System.out.println("Connected database failed ...!");
//		}
//		return result;
//	}

}
