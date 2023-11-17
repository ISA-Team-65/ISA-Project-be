//package com.team65.isaproject.dto;
//
//import com.team65.isaproject.model.Address;
//import com.team65.isaproject.model.Company;
//
//import javax.persistence.Column;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//
//public class AddressDTO {
//
//    private Integer id;
//    private String country;
//    private String city;
//    private String street;
//    private Integer streetNumber;
//    private double longitude;
//    private double latitude;
//    private Company company;
//
//    public AddressDTO() {
//    }
//
//    public AddressDTO(Address address) {
//        id = address.getId();
//        country = address.getCountry();
//        city = address.getCity();
//        street = address.getStreet();
//        streetNumber = address.getStreetNumber();
//        longitude = address.getLongitude();
//        latitude = address.getLatitude();
//        company = address.getCompany();
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
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
//    public Integer getStreetNumber() {
//        return streetNumber;
//    }
//
//    public void setStreetNumber(Integer streetNumber) {
//        this.streetNumber = streetNumber;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
//    public Company getCompany() {
//        return company;
//    }
//
//    public void setCompany(Company company) {
//        this.company = company;
//    }
//}
