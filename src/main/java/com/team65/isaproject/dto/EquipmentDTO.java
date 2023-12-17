package com.team65.isaproject.dto;

import com.team65.isaproject.model.equipment.EquipmentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDTO {

    private Integer id;
    private String name;
    private EquipmentType type;
    private String description;
    private double rating;
    private double price;
    private Integer companyId;
    private Integer appointmentId;
}
