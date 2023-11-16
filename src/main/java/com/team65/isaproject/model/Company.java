package com.team65.isaproject.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String CompanyName;

    private String Address;

    private String Description;

    private String Rating;

    //@Column(name = "availableDates", nullable =
    @OneToMany
    private ArrayList<Date> AvailableDates; //ovde ce da budu termini objekti ne dates

    //@Column(name = "administrators", nullable = true)
    @OneToMany
    private ArrayList<Integer> CompanyAdministrators; //ovo treba lista usera da bude

    public Company() {super();
        CompanyAdministrators = new ArrayList<>();
        AvailableDates = new ArrayList<>();}

    public Company(Integer id, String companyName, String address, String description, String rating, ArrayList<Date> availableDates, ArrayList<Integer> companyAdministrators) {
        this.id = id;
        CompanyName = companyName;
        Address = address;
        Description = description;
        Rating = rating;
        AvailableDates = availableDates;
        CompanyAdministrators = companyAdministrators;
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

    public ArrayList<Date> getAvailableDates() {
        return AvailableDates;
    }

    public void setAvailableDates(ArrayList<Date> availableDates) {
        this.AvailableDates = availableDates;
    }

    public ArrayList<Integer> getCompanyAdministrators() {
        return CompanyAdministrators;
    }

    public void setCompanyAdministrators(ArrayList<Integer> companyAdministrators) {
        CompanyAdministrators = companyAdministrators;
    }
}
