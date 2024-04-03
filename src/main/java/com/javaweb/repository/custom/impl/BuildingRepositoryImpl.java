package com.javaweb.repository.custom.impl;

import java.lang.reflect.Field;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.model.BuildingDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.custom.BuildingRepositoryCustom;
import com.javaweb.repository.entity.BuildingEntity;
import com.javaweb.utils.ConnectionJDBCUtil;
import com.javaweb.utils.NumberUtil;
import com.javaweb.utils.StringUtil;
import com.mysql.cj.log.Log;

@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepositoryCustom{
//	static final String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
//	static final String USER = "root";
//	static final String PASS = "callofduty12345";
	@PersistenceContext
	private EntityManager entityManager;

	
	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
//		Long staffId =(Long) params.get("staffid");
		Long staffId = buildingSearchBuilder.getStaffId();
		if(staffId!=null) {
			 sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
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
	public static void queryNomal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for(Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if(!fieldName.equals("staffId") && !fieldName.equals("typeCode") &&
						!fieldName.startsWith("rentarea") && !fieldName.startsWith("rentPrice")) {
					Object value = item.get(buildingSearchBuilder);
					if(value != null) {
						if(item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Integer")) {
							where.append(" AND b." + fieldName + " = " + value);
						}else {
							where.append(" AND b." + fieldName + " LIKE '%" + value + "%' ");
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		Long staffId = buildingSearchBuilder.getStaffId();
		if(staffId!=null) {
			where.append(" AND assignmentbuilding.staffid = " + staffId);
		}
		Long rentAreaTo = buildingSearchBuilder.getRentPriceTo();
		Long rentAreaFrom = buildingSearchBuilder.getRentPriceFrom();
		if(rentAreaTo!=null || rentAreaFrom!=null) {
			where.append(" AND EXISTS (SELECT * FROM rentarea r WHERE b.id = r.buildingid ");
			if(rentAreaFrom!=null) {
				where.append(" AND r.value >= " + rentAreaFrom);
			}
			if(rentAreaTo!=null) {
				where.append(" AND r.value <= " + rentAreaTo);
			}
			where.append(") ");
		}
		Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
		Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		if(rentPriceFrom!=null || rentPriceTo!=null) {
			if(rentPriceFrom!=null) {
				where.append(" AND b.rentprice >= " + rentPriceFrom);
			}
			if(rentPriceTo!=null) {
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
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if(typeCode != null && typeCode.size()!=0) {
			where.append(" AND(");
			String sql = typeCode.stream().map(it-> "renttype.code like " + "'%" + it + "%' ").collect(Collectors.joining(" OR "));
			where.append(sql + ") ");
		}
	}
//	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {

//		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, "
//				+ "b.floorarea, b.rentprice, b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ");
		StringBuilder sql = new StringBuilder("SELECT b.* FROM building b ");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNomal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
		where.append(" GROUP BY b.id");
		sql.append(where);
//		checkJoin(sql, params, typeCode);
//		sql.append("where 1 = 1 ");
//		checkWhere(sql, params, typeCode);
//		sql.append(" group by b.id");
		
//		List<BuildingEntity> result = new ArrayList<>();
//		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//				Statement stmt = conn.createStatement();
//				ResultSet rs = stmt.executeQuery(sql.toString());) {
//			while (rs.next()) {
//				BuildingEntity buildingEntity = new BuildingEntity();
//				buildingEntity.setId(rs.getInt("id"));
//				buildingEntity.setName(rs.getString("name"));
////				buildingEntity.setDistrictid(rs.getInt("districtid"));
//				buildingEntity.setWard(rs.getString("ward"));
//				buildingEntity.setStreet(rs.getString("street"));
////				buildingEntity.setNumberOfBasement(rs.getInt("numberofbasement"));
//				buildingEntity.setManagername(rs.getString("managername"));
//				buildingEntity.setManagerphonenumber(rs.getString("managerphonenumber"));
////				buildingEntity.setFloorarea(rs.getInt("floorarea"));
//				buildingEntity.setRentprice(rs.getInt("rentprice"));
////				buildingEntity.setServiceFee(rs.getInt("servicefee"));
////				buildingEntity.setBrokerageFee(rs.getInt("brokeragefee"));
//				result.add(buildingEntity);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			
//		}
//		System.out.println(sql.toString());
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class); 
		return query.getResultList();
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
