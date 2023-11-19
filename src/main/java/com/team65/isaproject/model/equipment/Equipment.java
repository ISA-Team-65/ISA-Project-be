package com.team65.isaproject.model.equipment;

import javax.persistence.*;

@Entity
@Table(name = "Equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "equipment_type")
    private EquipmentType type;
    @Column(name = "description")
    private String description;
    @Column(name = "rating")
    private double rating;
    @Column(name = "price")
    private double price;

    @Column(name = "company_id")
    private Integer company_id;

    public Equipment() {
    }

    public Equipment(Integer id, String name, EquipmentType type, String description, double rating, double price, Integer company_id) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.company_id = company_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }
}

