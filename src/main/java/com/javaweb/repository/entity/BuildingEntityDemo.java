//package com.javaweb.repository.entity;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "building")
//public class BuildingEntityDemo {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "managername")
//    private String managerName;
//
//    @Column(name = "managerphonenumber")
//    private String managerPhoneNumber;
//
//    @Column(name = "rentprice")
//    private String rentPrice;
//
//    @Column(name = "street")
//    private String street;
//
//    @Column(name = "ward")
//    private String ward;
//
//    @ManyToOne
//    @JoinColumn(name = "districtid")
//    private DistrictEntity district;
//
//    @OneToMany(mappedBy = "building", fetch = FetchType.LAZY)
//    private List<RentareaEntity> items = new ArrayList<>();
//
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getManagerName() {
//        return managerName;
//    }
//
//    public void setManagerName(String managerName) {
//        this.managerName = managerName;
//    }
//
//    public String getManagerPhoneNumber() {
//        return managerPhoneNumber;
//    }
//
//    public void setManagerPhoneNumber(String managerPhoneNumber) {
//        this.managerPhoneNumber = managerPhoneNumber;
//    }
//
//    public String getRentPrice() {
//        return rentPrice;
//    }
//
//    public void setRentPrice(String rentPrice) {
//        this.rentPrice = rentPrice;
//    }
//
//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public String getWard() {
//        return ward;
//    }
//
//    public void setWard(String ward) {
//        this.ward = ward;
//    }
//
//    public DistrictEntity getDistrict() {
//        return district;
//    }
//
//    public void setDistrict(DistrictEntity district) {
//        this.district = district;
//    }
//
//    public List<RentareaEntity> getItems() {
//        return items;
//    }
//
//    public void setItems(List<RentareaEntity> items) {
//        this.items = items;
//    }
//}
