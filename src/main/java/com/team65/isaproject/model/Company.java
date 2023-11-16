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

    private String CompanyName;

    private String Address;

    private String Description;

    private String Rating;

    //@Column(name = "availableDates", nullable =
    @OneToMany(mappedBy = "company")
    private List<Pickup> AvailableDates;

    //@Column(name = "administrators", nullable = true)
    @OneToMany(mappedBy = "company")
    private List<User> CompanyAdministrators;

    @OneToMany(mappedBy = "company")
    private List<Equipment> Equipments;

    public Company() {super();
        CompanyAdministrators = new ArrayList<User>();
        AvailableDates = new ArrayList<Pickup>();
        Equipments = new ArrayList<Equipment>();}

    public Company(Integer id, String companyName, String address, String description, String rating, List<Pickup> availableDates, List<User> companyAdministrators, List<Equipment> equipments) {
        this.id = id;
        CompanyName = companyName;
        Address = address;
        Description = description;
        Rating = rating;
        AvailableDates = availableDates;
        CompanyAdministrators = companyAdministrators;
        Equipments = equipments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "company name", nullable = false)
    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Column(name = "rating", nullable = false)
    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    @OneToMany(mappedBy = "company")
    public List<Pickup> getAvailableDates() {
        return AvailableDates;
    }

    public void setAvailableDates(List<Pickup> availableDates) {
        this.AvailableDates = availableDates;
    }

    @OneToMany(mappedBy = "company")
    public List<User> getCompanyAdministrators() {
        return CompanyAdministrators;
    }

    public void setCompanyAdministrators(List<User> companyAdministrators) {
        CompanyAdministrators = companyAdministrators;
    }

    @OneToMany(mappedBy = "company")
    public List<Equipment> getEquipments() {
        return Equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        Equipments = equipments;
    }
}
