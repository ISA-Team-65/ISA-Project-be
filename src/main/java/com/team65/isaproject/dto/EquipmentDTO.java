package com.team65.isaproject.dto;

import com.team65.isaproject.model.equipment.Equipment;
import com.team65.isaproject.model.equipment.EquipmentType;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class EquipmentDTO {

    private Integer id;
    private String name;
    private EquipmentType type;
    private String description;
    private double rating;
    private double price;
    private Integer company_id;
    private Integer appointmentId;

    public EquipmentDTO() {
    }

    public EquipmentDTO(Equipment equipment){
        id = equipment.getId();
        name = equipment.getName();
        type = equipment.getType();
        description = equipment.getDescription();
        rating = equipment.getRating();
        price = equipment.getPrice();
        company_id = equipment.getCompany_id();
        appointmentId = equipment.getAppointmentId();
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

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }
}
