package com.team65.isaproject.model.equipment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Enumerated(EnumType.ORDINAL)
    private EquipmentType type;
    private String description;
    private double rating;
    private double price;
    private Integer companyId;
    private Integer appointmentId;
}
