package com.team65.isaproject.model;

import javax.persistence.*;

@Entity
@Table(name = "Userr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "company_id") // Dodato
    private Integer company_id;

    public User() {
    }

    public User(Integer id, Integer company_id) {
        this.id = id;
        this.company_id = company_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }
}
