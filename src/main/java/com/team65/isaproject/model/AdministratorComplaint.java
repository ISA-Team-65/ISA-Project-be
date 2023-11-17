//package com.team65.isaproject.model;
//
//import javax.persistence.*;
//
//@Entity
//public class AdministratorComplaint {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ad_complaint_id")
//    private Integer id;
//
//    private Integer administrator_id;
//    private String description;
//
//    public AdministratorComplaint() {
//    }
//
//    public AdministratorComplaint(Integer id, Integer administrator_id, String description) {
//        this.id = id;
//        this.administrator_id = administrator_id;
//        this.description = description;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    @Column(name = "admin_id")
//    public Integer getAdministrator_id() {
//        return administrator_id;
//    }
//
//    public void setAdministrator_id(Integer administrator_id) {
//        this.administrator_id = administrator_id;
//    }
//
//    @Column(name = "description")
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//}
