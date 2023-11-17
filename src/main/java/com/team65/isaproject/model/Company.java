package com.team65.isaproject.model;

import com.team65.isaproject.model.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Integer id;
    @Column(name = "company_name", nullable = false)
    private String companyName;
    @Column(name = "address")
    private String address;
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "rating", nullable = false)
    private double rating;

//    @Column(name = "available_appointments")
//    private List<Integer> availableAppointments;

//    @OneToMany(mappedBy = "company")
//    private List<User> companyAdministrators;

//    @OneToMany(mappedBy = "company")
//    private List<Equipment> equipment;

    public Company() {
        super();
//        companyAdministrators = new ArrayList<User>();
//        availableAppointments = new ArrayList<Appointment>();
//        equipment = new ArrayList<Equipment>();
    }

    public Company(Integer id, String companyName, String address, String description, double rating, List<Appointment> availableAppointments, List<User> companyAdministrators, List<Equipment> equipment) {
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.description = description;
        this.rating = rating;
//        this.availableAppointments = availableAppointments;
//        this.companyAdministrators = companyAdministrators;
//        this.equipment = equipment;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

//    public List<Appointment> getAvailableAppointments() {
//        return availableAppointments;
//    }
//
//    public void setAvailableAppointments(List<Appointment> availableAppointments) {
//        this.availableAppointments = availableAppointments;
//    }
//
//    public List<User> getCompanyAdministrators() {
//        return companyAdministrators;
//    }
//
//    public void setCompanyAdministrators(List<User> companyAdministrators) {
//        this.companyAdministrators = companyAdministrators;
//    }
//
//    public List<Equipment> getEquipment() {
//        return equipment;
//    }
//
//    public void setEquipments(List<Equipment> equipment) {
//        this.equipment = equipment;
//    }
}
