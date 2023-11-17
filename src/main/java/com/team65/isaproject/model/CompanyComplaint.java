//package com.team65.isaproject.model;
//
//import javax.persistence.*;
//
//@Entity
//public class CompanyComplaint {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "comp_complaint_id")
//    private Integer id;
//
//    //mozda ce ici koji je user ulozio zalbu a mozda ce da bude anonimno
//    //to kasnije cemo videti kako nam bude potrebno
//    //za sad anonimno
//
//    private String description;
//    private Integer companyId;
//
//    public CompanyComplaint() {
//    }
//
//    public CompanyComplaint(Integer id, String description, Integer companyId) {
//        this.id = id;
//        this.description = description;
//        this.companyId = companyId;
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
//    @Column(name = "description")
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    @Column(name = "company_id")
//    public Integer getCompanyId() {
//        return companyId;
//    }
//
//    public void setCompanyId(Integer companyId) {
//        this.companyId = companyId;
//    }
//}
