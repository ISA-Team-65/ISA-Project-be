package com.team65.isaproject.dto;

import com.team65.isaproject.model.Company;

public class CompanyDTO {
    private Integer id;
    private String companyName;
    private String address;
    private String description;
    private Double rating;

    public CompanyDTO() {
    }

    public CompanyDTO(Company company) {
        this(company.getId(), company.getCompanyName(), company.getAddress(), company.getDescription(), company.getRating());
    }

    public CompanyDTO(Integer id, String companyName, String address, String description, Double rating) {
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.description = description;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
