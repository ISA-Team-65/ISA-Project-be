package com.team65.isaproject.model;

import javax.persistence.*;

@Entity
@Table(name = "Userr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "company_id") // Dodato
    private Company company;

    public User() {
    }

    public User(Integer id, Company company) {
        this.id = id;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
