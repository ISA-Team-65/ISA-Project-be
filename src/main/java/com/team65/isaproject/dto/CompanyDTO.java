package com.team65.isaproject.dto;

import com.team65.isaproject.model.*;
import com.team65.isaproject.model.user.User;

import java.util.List;

public class CompanyDTO {
    private Integer id;
    private String companyName;
    private Address address;
    private String description;
    private double rating;
    private List<Appointment> availableAppointments;
    private List<User> companyAdministrators;
    private List<Equipment> equipments;

    public CompanyDTO() {
    }

    public CompanyDTO(Company company) {
        id = company.getId();
        companyName = company.getCompanyName();
        address = company.getAddress();
        description = company.getDescription();
        rating = company.getRating();
        availableAppointments = company.getAvailableAppointments();
        companyAdministrators = company.getCompanyAdministrators();
        equipments = company.getEquipments();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Appointment> getAvailableAppointments() {
        return availableAppointments;
    }

    public void setAvailableAppointments(List<Appointment> availableAppointments) {
        this.availableAppointments = availableAppointments;
    }

    public List<User> getCompanyAdministrators() {
        return companyAdministrators;
    }

    public void setCompanyAdministrators(List<User> companyAdministrators) {
        this.companyAdministrators = companyAdministrators;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }
}
