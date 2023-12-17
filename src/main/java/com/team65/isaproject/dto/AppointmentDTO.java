package com.team65.isaproject.dto;

import com.team65.isaproject.model.appointment.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Integer id;
    private String adminName;
    private String adminLastname;
    private LocalDateTime dateTime;
    private double duration;
    private AppointmentStatus status;
    private Integer companyId;
    private boolean isReserved;
    private Integer userId;
}
