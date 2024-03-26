package com.javaweb.repository.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="building")
public class BuildingEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "floorarea")
	private Integer floorArea;
	
//	@Column(name = "districtid")
//	private Integer districtid;
	
	@Column(name = "ward")
	private String ward;
	
	@Column(name = "structure")
	private String structure;
	
	@Column(name = "servicefee")
	private String serviceFee;
	
	@Column(name = "carfee")
	private String carFee;
	
	@Column(name = "motorbikefee")
	private String motorbikeFee;
	
	@Column(name = "overtimefee")
	private String overTimeFee;
	
	@Column(name = "waterfee")
	private String waterFee;
	
	@Column(name = "electricityfee")
	private String electricityFee;
	
	@Column(name = "deposit")
	private String deposit;
	
	@Column(name = "payment")
	private String payment;
	
	@Column(name = "renttime")
	private String rentTime;
	
	@Column(name = "decorationtime")
	private String decorationTime;
	
	@Column(name = "brokeragefee")
	private Float brokerageFee;
	
	@Column(name = "note")
	private String note;
	
	@Column(name = "linkofbuilding")
	private String linkOfBuilding;
	
	@Column(name = "map")
	private String map;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "createddate")
	private Date createdDate;
	
	@Column(name = "modifieddate")
	private Date modifiedDate;
	
	@Column(name = "createdby")
	private String createdBy;
	
	@Column(name = "modifiedby")
	private String modifiedBy;
	
	@Column(name = "street")
	private String street;
	
	@Column(name = "numberofbasement")
	private Integer numberOfBasement;
	
	@Column(name = "direction")
	private String direction;
	
	@Column(name = "level")
	private Integer level;
	
	@Column(name = "rentprice")
	private Integer rentPrice;
	
	@Column(name = "managername")
	private String managerName;
	
	@Column(name = "managerphonenumber")
	private String managerPhoneNumber;
	
	
//	private Integer empyArea;
	

//	private Integer serviceFee;
//	private Integer brokerageFee;
	
	@ManyToOne
	@JoinColumn(name = "districtid")
	private DistrictEntity district;
	
	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
	private List<RentareaEntity> items = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "buildingrenttype",
			joinColumns = @JoinColumn(name = "buildingid", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "renttypeid", nullable = false))
	private List<RenttypeEntity> renttype = new ArrayList<>();
	
	@OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
	private List<AssignmentBuildingEntity> assignmentBuildingEntities = new ArrayList<>();
	
	
	
	public List<AssignmentBuildingEntity> getAssignmentBuildingEntities() {
		return assignmentBuildingEntities;
	}
	public void setAssignmentBuildingEntities(List<AssignmentBuildingEntity> assignmentBuildingEntities) {
		this.assignmentBuildingEntities = assignmentBuildingEntities;
	}
	public List<RenttypeEntity> getRenttype() {
		return renttype;
	}
	public void setRenttype(List<RenttypeEntity> renttype) {
		this.renttype = renttype;
	}
	public List<RentareaEntity> getItems() {
		return items;
	}
	public void setItems(List<RentareaEntity> items) {
		this.items = items;
	}
	public DistrictEntity getDistrict() {
		return district;
	}
	public void setDistrict(DistrictEntity district) {
		this.district = district;
	}
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
//	public Integer getNumberOfBasement() {
//		return numberOfBasement;
//	}
//	public void setNumberOfBasement(Integer numberOfBasement) {
//		this.numberOfBasement = numberOfBasement;
//	}
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
//	public Integer getFloorarea() {
//		return floorarea;
//	}
//	public void setFloorarea(Integer floorarea) {
//		this.floorarea = floorarea;
//	}
//	public Integer getDistrictid() {
//		return districtid;
//	}
//	public void setDistrictid(Integer districtid) {
//		this.districtid = districtid;
//	}
//	public String getDirection() {
//		return direction;
//	}
//	public void setDirection(String direction) {
//		this.direction = direction;
//	}
//	public Integer getLevel() {
//		return level;
//	}
//	public void setLevel(Integer level) {
//		this.level = level;
//	}	
	public String getManagername() {
		return managerName;
	}
	public void setManagername(String managerName) {
		this.managerName = managerName;
	}
	public String getManagerphonenumber() {
		return managerPhoneNumber;
	}
	public void setManagerphonenumber(String managerPhoneNumber) {
		this.managerPhoneNumber = managerPhoneNumber;
	}
	public Integer getRentprice() {
		return rentPrice;
	}
	public void setRentprice(Integer rentPrice) {
		this.rentPrice = rentPrice;
	}
//	public Integer getEmpyArea() {
//		return empyArea;
//	}
//	public void setEmpyArea(Integer empyArea) {
//		this.empyArea = empyArea;
//	}
//	public Integer getServiceFee() {
//		return serviceFee;
//	}
//	public void setServiceFee(Integer serviceFee) {
//		this.serviceFee = serviceFee;
//	}
//	public Integer getBrokerageFee() {
//		return brokerageFee;
//	}
//	public void setBrokerageFee(Integer brokerageFee) {
//		this.brokerageFee = brokerageFee;
//	}
	public Integer getFloorarea() {
		return floorArea;
	}
	public void setFloorarea(Integer floorArea) {
		this.floorArea = floorArea;
	}
	public String getStructure() {
		return structure;
	}
	public void setStructure(String structure) {
		this.structure = structure;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getCarFee() {
		return carFee;
	}
	public void setCarFee(String carFee) {
		this.carFee = carFee;
	}
	public String getMotorbikeFee() {
		return motorbikeFee;
	}
	public void setMotorbikeFee(String motorbikeFee) {
		this.motorbikeFee = motorbikeFee;
	}
	public String getOverTimeFee() {
		return overTimeFee;
	}
	public void setOverTimeFee(String overTimeFee) {
		this.overTimeFee = overTimeFee;
	}
	public String getWaterFee() {
		return waterFee;
	}
	public void setWaterFee(String waterFee) {
		this.waterFee = waterFee;
	}
	public String getElectricityFee() {
		return electricityFee;
	}
	public void setElectricityFee(String electricityFee) {
		this.electricityFee = electricityFee;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getRentTime() {
		return rentTime;
	}
	public void setRentTime(String rentTime) {
		this.rentTime = rentTime;
	}
	public String getDecorationTime() {
		return decorationTime;
	}
	public void setDecorationTime(String decorationTime) {
		this.decorationTime = decorationTime;
	}
	public Float getBrokerageFee() {
		return brokerageFee;
	}
	public void setBrokerageFee(Float brokerageFee) {
		this.brokerageFee = brokerageFee;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getLinkOfBuilding() {
		return linkOfBuilding;
	}
	public void setLinkOfBuilding(String linkOfBuilding) {
		this.linkOfBuilding = linkOfBuilding;
	}
	public String getMap() {
		return map;
	}
	public void setMap(String map) {
		this.map = map;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getNumberOfBasement() {
		return numberOfBasement;
	}
	public void setNumberOfBasement(Integer numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
}
