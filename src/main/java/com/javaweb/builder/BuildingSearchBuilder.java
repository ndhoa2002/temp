package com.javaweb.builder;

import java.util.ArrayList;
import java.util.List;

public class BuildingSearchBuilder {
	private String name;
	private Long floorArea;
	private String ward;
	private String street;
	private Long districtid;
	private Integer numberOfBasement;
	private List<String> typeCode = new ArrayList<>();
	private String managerName;
	private String managerPhoneNumber;
	private Long rentPriceFrom;
	private Long rentPriceTo;
	private Long rentareaFrom;
	private Long rentareaTo;
	private Long staffId;
	
	public BuildingSearchBuilder(Builder builder) {
		this.name = builder.name;
		this.floorArea = builder.floorArea;
		this.ward = builder.ward;
		this.street = builder.street;
		this.districtid = builder.districtid;
		this.numberOfBasement = builder.numberOfBasement;
		this.typeCode = builder.typeCode;
		this.managerName = builder.managerName;
		this.managerPhoneNumber = builder.managerPhoneNumber;
		this.rentPriceFrom = builder.rentPriceFrom;
		this.rentPriceTo = builder.rentPriceTo;
		this.rentareaFrom = builder.areaFrom;
		this.rentareaTo = builder.areaTo;
		this.staffId = builder.staffId;
	}
	public String getName() {
		return name;
	}
	public Long getFloorArea() {
		return floorArea;
	}
	public String getWard() {
		return ward;
	}
	public String getStreet() {
		return street;
	}
	public Long getDistrictid() {
		return districtid;
	}
	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}
	public List<String> getTypeCode() {
		return typeCode;
	}
	public String getManagerName() {
		return managerName;
	}
	public String getManagerPhoneNumber() {
		return managerPhoneNumber;
	}
	public Long getRentPriceFrom() {
		return rentPriceFrom;
	}
	public Long getRentPriceTo() {
		return rentPriceTo;
	}
	public Long getAreaFrom() {
		return rentareaFrom;
	}
	public Long getAreaTo() {
		return rentareaTo;
	}
	public Long getStaffId() {
		return staffId;
	}
	
	public static class Builder{
		private String name;
		private Long floorArea;
		private String ward;
		private String street;
		private Long districtid;
		private Integer numberOfBasement;
		private List<String> typeCode = new ArrayList<>();
		private String managerName;
		private String managerPhoneNumber;
		private Long rentPriceFrom;
		private Long rentPriceTo;
		private Long areaFrom;
		private Long areaTo;
		private Long staffId;
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		public Builder setFloorArea(Long floorArea) {
			this.floorArea = floorArea;
			return this;
		}
		public Builder setWard(String ward) {
			this.ward = ward;
			return this;
		}
		public Builder setStreet(String street) {
			this.street = street;
			return this;
		}
		public Builder setDistrictid(Long districtid) {
			this.districtid = districtid;
			return this;
		}
		public Builder setNumberOfBasement(Integer numberOfBasement) {
			this.numberOfBasement = numberOfBasement;
			return this;
		}
		public Builder setTypeCode(List<String> typeCode) {
			this.typeCode = typeCode;
			return this;
		}
		public Builder setManagerName(String managerName) {
			this.managerName = managerName;
			return this;
		}
		public Builder setManagerPhoneNumber(String managerPhoneNumber) {
			this.managerPhoneNumber = managerPhoneNumber;
			return this;
		}
		public Builder setRentPriceFrom(Long rentPriceFrom) {
			this.rentPriceFrom = rentPriceFrom;
			return this;
		}
		public Builder setRentPriceTo(Long rentPriceTo) {
			this.rentPriceTo = rentPriceTo;
			return this;
		}
		public Builder setAreaFrom(Long areaFrom) {
			this.areaFrom = areaFrom;
			return this;
		}
		public Builder setAreaTo(Long areaTo) {
			this.areaTo = areaTo;
			return this;
		}
		public Builder setStaffId(Long staffId) {
			this.staffId = staffId;
			return this;
		}
		
		public BuildingSearchBuilder build() {
			return new BuildingSearchBuilder(this);
		}
	}
}
