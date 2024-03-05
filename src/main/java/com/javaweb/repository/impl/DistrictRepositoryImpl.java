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

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.javaweb.repository.DistrictRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.repository.entity.DistrictEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
@Repository
public class DistrictRepositoryImpl implements DistrictRepository{
//	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
//	static final String USER = "root";
//	static final String PASS = "callofduty12345";
	@Override
	public DistrictEntity findNameById(Integer districtid) {
		// TODO Auto-generated method stub
//		StringBuilder sql = new StringBuilder("SELECT * FROM district where id = " + districtid);
//		DistrictEntity districtEntity = new DistrictEntity();
//		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//				Statement stmt = conn.createStatement();
//				ResultSet rs = stmt.executeQuery(sql.toString());) {
//			while(rs.next()) {
//				districtEntity.setId(rs.getInt("id"));
//				districtEntity.setCode(rs.getString("code"));
//				districtEntity.setName(rs.getString("name"));
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.setStackTrace(null);
//			System.out.println("Connected database failed ...!");
//		}
//		return districtEntity;
		String sql = "SELECT d.name FROM district d WHERE d.id = " + districtid;
		DistrictEntity districtEntity = new DistrictEntity();
		try(Connection conn = ConnectionJDBCUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while(rs.next()) {
				districtEntity.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return districtEntity;
	}

}
