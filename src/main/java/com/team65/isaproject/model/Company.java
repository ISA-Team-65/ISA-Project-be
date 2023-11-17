package com.team65.isaproject.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Integer id;

    private String companyName;

    private String address;

    private String description;

    private Double rating;

    //@Column(name = "availableDates", nullable =
    @OneToMany(mappedBy = "company")
    private List<Pickup> availableDates;

    //@Column(name = "administrators", nullable = true)
    @OneToMany(mappedBy = "company")
    private List<User> companyAdministrators;

    @OneToMany(mappedBy = "company")
    private List<Equipment> equipments;

    public Company() {super();
        this.companyAdministrators = new ArrayList<User>();
        this.availableDates = new ArrayList<Pickup>();
        this.equipments = new ArrayList<Equipment>();}

    public Company(Integer id, String companyName, String address, String description, Double rating, List<Pickup> availableDates, List<User> companyAdministrators, List<Equipment> equipments) {
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.description = description;
        this.rating = rating;
        this.availableDates = availableDates;
        this.companyAdministrators = companyAdministrators;
        this.equipments = equipments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "company name", nullable = false)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "rating", nullable = false)
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @OneToMany(mappedBy = "company")
    public List<Pickup> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(List<Pickup> availableDates) {
        this.availableDates = availableDates;
    }

    @OneToMany(mappedBy = "company")
    public List<User> getCompanyAdministrators() {
        return companyAdministrators;
    }

    public void setCompanyAdministrators(List<User> companyAdministrators) {
        this.companyAdministrators = companyAdministrators;
    }

    @OneToMany(mappedBy = "company")
    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }
}
