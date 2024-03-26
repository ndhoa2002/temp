package com.javaweb.repository.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.entity.BuildingEntity;
@Repository
@Primary
public class BuildingRepositoryImpl implements BuildingRepository{

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
		Long rentAreaTo = buildingSearchBuilder.getAreaTo();
		Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
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
	
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		// TODO Auto-generated method stub
		//JPQL
//		String sql = "FROM BuildingEntity b where b.name like '%building%' ";
//		Query query = entityManager.createQuery(sql, BuildingEntity.class);
		StringBuilder sql = new StringBuilder("SELECT * FROM building b ");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNomal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
		where.append(" GROUP BY b.id");
		sql.append(where);
		
//		Query query1 = 
		//sql native
//		String sql = "SELECT * FROM building b WHERE b.name like '%building%' ";
//		Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);
		System.out.println(sql.toString());
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		return query.getResultList();
	}

}
