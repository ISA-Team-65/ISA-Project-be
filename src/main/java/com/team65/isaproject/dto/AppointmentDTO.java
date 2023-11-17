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
    }
}
