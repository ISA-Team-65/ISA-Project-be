package com.team65.isaproject.dto;

import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.model.appointment.AppointmentStatus;

import java.time.LocalDateTime;

public class AppointmentDTO {

    private Integer id;
    private String adminName;
    private String adminLastname;
    private LocalDateTime dateTime;
    private double duration;
    private AppointmentStatus status;
    private Integer company_id;
    private boolean isReserved;

    public AppointmentDTO() {
    }

    public AppointmentDTO(Appointment appointment){
        id = appointment.getId();
        adminName = appointment.getAdminName();
        adminLastname = appointment.getAdminLastname();
        dateTime = appointment.getDateTime();
        duration = appointment.getDuration();
        status = appointment.getStatus();
        company_id = appointment.getCompany_id();
        isReserved = appointment.isReserved();
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
}
