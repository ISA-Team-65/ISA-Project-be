package com.team65.isaproject.model.equipment;

import com.team65.isaproject.model.appointment.Appointment;
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @Override
    public String toString() {
        return "Equipment{" + "id=" + id + ", name='" + name + '\'' + ", type='" + type + '\'' + '}';
    }
}
