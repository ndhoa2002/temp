package com.javaweb.repository.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;
import com.mysql.cj.log.Log;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
//	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
//	static final String USER = "root";
//	static final String PASS = "callofduty12345";

	
	public static void joinTable(Map<String, Object> params, List<String> typeCode, StringBuilder sql) {
//		Long staffId =(Long) params.get("staffid");
		String staffId = (String)params.get("staffid");
		if(StringUtil.checkString(staffId)) {
			 sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}
		if(typeCode!=null && typeCode.size()!=0) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}
//		String rentAreaTo = (String)params.get("rentareato");
//		String rentAreaFrom = (String)params.get("rentareafrom");
//		if(StringUtil.checkString(rentAreaFrom)==true || StringUtil.checkString(rentAreaTo)==true) {
//			sql.append(" INNER JOIN rentarea ON rentarea.buildingid = b.id");
//		}
	}
	public static void queryNomal(Map<String, Object> params, StringBuilder where) {
		for(Map.Entry<String, Object> it : params.entrySet()) {
			if(!it.getKey().equals("staffid") && !it.getKey().equals("typeCode") &&
					!it.getKey().startsWith("rentarea") && !it.getKey().startsWith("rentprice")) {
				String value = it.getValue().toString();
				if(StringUtil.checkString(value)) {
					if(NumberUtil.isNumber(value)) {
						where.append(" AND b." + it.getKey() + " = " + value);
					}else {
						where.append(" AND b." + it.getKey() + " LIKE '%" + value + "%' ");
					}
				}
			}
		}
	}
	public static void querySpecial(Map<String, Object> params, List<String> typeCode, StringBuilder where) {
		String staffId = (String)params.get("staffid");
		if(StringUtil.checkString(staffId)) {
			where.append(" AND assignmentbuilding.staffid = " + staffId);
		}
		String rentAreaTo = (String)params.get("rentareato");
		String rentAreaFrom = (String)params.get("rentareafrom");
		if(StringUtil.checkString(rentAreaFrom)==true || StringUtil.checkString(rentAreaTo)==true) {
			where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid ");
			if(StringUtil.checkString(rentAreaFrom)) {
				where.append(" AND r.value >= " + rentAreaFrom);
			}
			if(StringUtil.checkString(rentAreaTo)) {
				where.append(" AND r.value <= " + rentAreaTo);
			}
			where.append(") ");
		}
		String rentPriceTo = (String)params.get("rentpriceto");
		String rentPriceFrom = (String)params.get("rentpricefrom");
		if(StringUtil.checkString(rentPriceFrom)==true && StringUtil.checkString(rentPriceTo)==true) {
			if(StringUtil.checkString(rentPriceFrom)) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if(StringUtil.checkString(rentPriceTo)) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
//		if(typeCode!=null && typeCode.size()!=0) {
//			List<String> code = new ArrayList<>();
//			for(String item : typeCode) {
//				code.add("'" + item + "'");
//			}
//			where.append(" AND renttype.code IN(" + String.join(",", code) + ") ");
//		}
		//java 8
		if(typeCode != null && typeCode.size()!=0) {
			where.append(" AND(");
			String sql = typeCode.stream().map(it-> "renttype.code like " + "'%" + it + "%' ").collect(Collectors.joining(" OR "));
			where.append(sql + ") ");
		}
	}
	@Override
	public List<BuildingEntity> findAll(Map<String, Object> params, List<String> typeCode) {
		// TODO Auto-generated method stub
//		StringBuilder sql = new StringBuilder("select b.* from building b");
//		StringBuilder sql = new StringBuilder("select b.id, b.districtid, b.name, b.street, "
//				+ "b.ward, b.numberofbasement, b.managername, b.managerphonenumber, "
//				+ "b.floorarea, b.servicefee, b.brokeragefee, b.rentprice from building b ");
////				+ "from building b inner join district d on b.districtid = d.id "
////				+ "inner join rentarea r on b.id=r.buildingid ");
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, "
				+ "b.floorarea, b.rentprice, b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ");
		joinTable(params, typeCode, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNomal(params, where);
		querySpecial(params, typeCode, where);
		where.append(" GROUP BY b.id");
		sql.append(where);
//		checkJoin(sql, params, typeCode);
//		sql.append("where 1 = 1 ");
//		checkWhere(sql, params, typeCode);
//		sql.append(" group by b.id");
		
		List<BuildingEntity> result = new ArrayList<>();
		try (Connection conn = ConnectionJDBCUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql.toString())) {
			while (rs.next()) {
				BuildingEntity buildingEntity = new BuildingEntity();
				buildingEntity.setId(rs.getInt("id"));
				buildingEntity.setName(rs.getString("name"));
				buildingEntity.setDistrictid(rs.getInt("districtid"));
				buildingEntity.setWard(rs.getString("ward"));
				buildingEntity.setStreet(rs.getString("street"));
				buildingEntity.setNumberOfBasement(rs.getInt("numberofbasement"));
				buildingEntity.setManagername(rs.getString("managername"));
				buildingEntity.setManagerphonenumber(rs.getString("managerphonenumber"));
				buildingEntity.setFloorarea(rs.getInt("floorarea"));
				buildingEntity.setRentprice(rs.getInt("rentprice"));
				buildingEntity.setServiceFee(rs.getInt("servicefee"));
				buildingEntity.setBrokerageFee(rs.getInt("brokeragefee"));
				result.add(buildingEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		System.out.println(sql.toString());
		return result;
	}

	public void checkJoin(StringBuilder sql, Map<String, Object> params, List<String> typeCode) {
//		String userid = (String) params.get("userid");
//
//		if (userid != null && !userid.equals("")) {
//			sql.append(" inner join assignmentbuilding a on b.id=a.buildingid ");
//		}
//		if (typeCode.size() != 0 && typeCode != null) {
//			sql.append(" INNER JOIN buildingrenttype brt ON brt.buildingid = b.id ");
//			sql.append(" INNER JOIN renttype rt ON rt.id = brt.renttypeid ");
//		}
		
		if(params!=null && !params.isEmpty()) {
			if(params.containsKey("userid")) {
				if(params.get("userid")!=null) {
					sql.append(" inner join assignmentbuilding a on b.id=a.buildingid ");
				}
			}
		}
	
		if(typeCode!=null && !typeCode.isEmpty()) {
			sql.append(" inner join buildingrenttype bu on b.id=bu.buildingid "
					+ " inner join renttype re on re.id=bu.renttypeid ");
		}
	}

	public void checkWhere(StringBuilder sql, Map<String, Object> params, List<String> typeCode) {
//		String staffId = (String) params.get("userid");
//		if (staffId != null && !staffId.equals("")) {
//			sql.append(" AND a.staffid = " + staffId);
//		}
//		
//		if (params.get("name") != null && !params.get("name").equals("")) {
//			sql.append(" AND b.name like '%" + params.get("name") + "%' ");
//		}
//		if (params.get("floorarea") != null) {
//			sql.append(" AND b.floorarea = " + params.get("floorarea") + " ");
//		}
//		if (params.get("districtid") != null) {
//			sql.append(" AND b.districtid = " + params.get("districtid") + " ");
//		}
//		if (params.get("ward") != null && !params.get("ward").equals("")) {
//			sql.append(" AND b.ward like '%" + params.get("ward") + "%' ");
//		}
//		if (params.get("street") != null && !params.get("street").equals("")) {
//			sql.append(" AND b.street like '%" + params.get("street") + "%' ");
//		}
//		if (params.get("numberofbasement") != null) {
//			sql.append(" AND b.numberofbasement = " + params.get("numberofbasement") + " ");
//		}
//		if (params.get("direction") != null && !params.get("direction").equals("")) {
//			sql.append(" AND b.direction like '%" + params.get("direction") + "%' ");
//		}
//		if (params.get("level") != null) {
//			sql.append(" AND b.level = " + params.get("level") + " ");
//		}
//		if (params.get("rentareafrom") != null || params.get("rentareato") != null) {
//			sql.append(" AND EXISTS (SELECT * FROM rentarea ra WHERE b.id = ra.buildingid ");
//			if(params.get("rentareafrom") != null) {
//				sql.append(" AND ra.value = " + params.get("rentareafrom") + " ");
//			}
//			if(params.get("rentareato") != null) {
//				sql.append(" AND ra.value = " + params.get("rentareato") + " ");
//			}
//		}
//		
//		if (params.get("rentpricefrom") != null) {
//			sql.append(" AND b.rentprice >= " + params.get("rentpricefrom") + " ");
//		}
//		if (params.get("rentpriceto") != null) {
//			sql.append(" AND b.rentprice <= " + params.get("rentpriceto") + " ");
//		}
//		if (params.get("managername") != null && !params.get("managername").equals("")) {
//			sql.append(" AND b.managername like '%" + params.get("managername") + "%' ");
//		}
//		if (params.get("managerphonenumber") != null && !params.get("managerphonenumber").equals("")) {
//			sql.append(" AND b.managerphonenumber like '%" + params.get("managerphonenumber") + "%' ");
//		}
		
		
		if(params!=null && !params.isEmpty()) {
			if(params.containsKey("name")) {
				if (params.get("name") != null && !params.get("name").equals("")) {
					sql.append(" AND b.name like '%" + params.get("name") + "%' ");
				}
			}
			if(params.containsKey("floorarea")) {
				if (params.get("floorarea") != null) {
					sql.append(" AND b.floorarea = " + params.get("floorarea") + " ");
				}
			}
			if(params.containsKey("districtid")) {
				if (params.get("districtid") != null) {
					sql.append(" AND b.districtid = " + params.get("districtid") + " ");
				}
			}
			if(params.containsKey("ward")) {
				if (params.get("ward") != null && !params.get("ward").equals("")) {
					sql.append(" AND b.ward like '%" + params.get("ward") + "%' ");
				}
			}
			if(params.containsKey("street")) {
				if (params.get("street") != null && !params.get("street").equals("")) {
					sql.append(" AND b.street like '%" + params.get("street") + "%' ");
				}
			}
			if(params.containsKey("numberofbasement")) {
				if (params.get("numberofbasement") != null) {
					sql.append(" AND b.numberofbasement = " + params.get("numberofbasement") + " ");
				}
			}
			if(params.containsKey("direction")) {
				if (params.get("direction") != null && !params.get("direction").equals("")) {
					sql.append(" AND b.direction like '%" + params.get("direction") + "%' ");
				}
			}
			if(params.containsKey("level")) {
				if (params.get("level") != null) {
					sql.append(" AND b.level = " + params.get("level") + " ");
				}
			}
//			if(params.containsKey("rentareafrom")) {
//				if(params.get("rentareafrom") != null) {
//					sql.append(" AND r.value >= " + params.get("rentareafrom") + " ");
//				}
//			}
//			if(params.containsKey("rentareato")) {
//				if(params.get("rentareato") != null) {
//					sql.append(" AND r.value <= " + params.get("rentareato") + " ");
//				}
//			}
			
			if (params.get("rentareafrom") != null || params.get("rentareato") != null) {
				sql.append(" AND EXISTS (SELECT * FROM rentarea ra WHERE b.id = ra.buildingid ");
				if(params.get("rentareafrom") != null) {
					sql.append(" AND ra.value >= " + params.get("rentareafrom") + " ");
				}
				if(params.get("rentareato")==null) {
					sql.append(") ");
				}
				if(params.get("rentareato") != null) {
					sql.append(" AND ra.value <= " + params.get("rentareato") + ") ");
				}
			}
			if(params.containsKey("rentpricefrom")) {
				if (params.get("rentpricefrom") != null) {
					sql.append(" AND b.rentprice >= " + params.get("rentpricefrom") + " ");
				}
			}
			if(params.containsKey("rentpriceto")) {
				if (params.get("rentpriceto") != null) {
					sql.append(" AND b.rentprice <= " + params.get("rentpriceto") + " ");
				}
			}
			if(params.containsKey("managername")) {
				if (params.get("managername") != null && !params.get("managername").equals("")) {
					sql.append(" AND b.managername like '%" + params.get("managername") + "%' ");
				}
			}
			if(params.containsKey("managerphonenumber")) {
				if (params.get("managerphonenumber") != null && !params.get("managerphonenumber").equals("")) {
					sql.append(" AND b.managerphonenumber like '%" + params.get("managerphonenumber") + "%' ");
				}
			}
			if(params.containsKey("userid")) {
				if (params.get("userid") != null) {
					sql.append(" AND a.staffid = " + params.get("userid") + " ");
				}
			}
			
		}
		if (typeCode != null) {
			sql.append("AND (");
			int temp = 0;
			for (String code : typeCode) {
				sql.append("re.code like '%" + code + "%' ");
				temp++;
				if (temp == typeCode.size()) {
					break;
				}
				sql.append("or ");
			}
			sql.append(")");
//			sql.append(" AND (");
//			String query = typeCode.stream().map(it -> "re.code LIKE " + "'%" + it + "%' ")
//					.collect(Collectors.joining(" OR "));
//			sql.append(query + " ) ");
		}
	}

}
