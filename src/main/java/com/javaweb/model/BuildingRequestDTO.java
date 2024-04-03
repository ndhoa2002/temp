package com.javaweb.model;

public class BuildingRequestDTO {
	private Integer id;
	private String name;
	private String ward;
	private String street;
	private Integer districtid;
	private Long rentprice;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public Integer getDistrictId() {
		return districtid;
	}
	public void setDistrictId(Integer districtId) {
		this.districtid = districtId;
	}
	public Long getRentprice() {
		return rentprice;
	}
	public void setRentprice(Long rentprice) {
		this.rentprice = rentprice;
	}
	
	
}
