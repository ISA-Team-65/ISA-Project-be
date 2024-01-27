package com.team65.isaproject.dto;

import com.team65.isaproject.model.appointment.AppointmentStatus;
import com.team65.isaproject.model.equipment.Equipment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDTO {

    private Integer id;
    private LocalDateTime dateTime;
    private double duration;
    private AppointmentStatus status;
    private Integer companyId;
    private boolean isReserved;
    private Integer userId;
    private Integer adminId;
    private List<EquipmentDTO> equipmentList;
}
