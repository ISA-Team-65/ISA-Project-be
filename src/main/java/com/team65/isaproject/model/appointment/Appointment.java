package com.team65.isaproject.model.appointment;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "admin_name")
    private String adminName;
    @Column(name = "admin_lastname")
    private String adminLastname;
    @Column(name = "appointment_date_time")
    private LocalDateTime dateTime;
    @Column(name = "duration")
    private double duration;
    @Column(name = "status")
    private AppointmentStatus status;
    @Column(name = "company_id") // Dodato
    private Integer company_id;

    @Column(name = "is_reserved")
    private boolean isReserved;

    @Column(name = "user_id")
    private Integer user_id;

    public Appointment() {
    }

    public Appointment(Integer id, String adminName, String adminLastname, LocalDateTime dateTime, double duration, AppointmentStatus status, Integer company_id, boolean isReserved, Integer user_id) {
        this.id = id;
        this.adminName = adminName;
        this.adminLastname = adminLastname;
        this.dateTime = dateTime;
        this.duration = duration;
        this.status = status;
        this.company_id = company_id;
        this.isReserved = isReserved;
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminLastname() {
        return adminLastname;
    }

    public void setAdminLastname(String adminLastname) {
        this.adminLastname = adminLastname;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Integer getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Integer company_id) {
        this.company_id = company_id;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}