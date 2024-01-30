package com.team65.isaproject.model.appointment;

import com.team65.isaproject.model.equipment.Equipment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime dateTime;
    private double duration;
    private AppointmentStatus status;
    private Integer companyId;
    private boolean isReserved;
    private Integer userId;
    private Integer adminId;
    private LocalDateTime pickUpDateTime;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "appointment")
    private List<Equipment> equipmentList;

    @Override
    public String toString() {
        return "Appointment{" + "id=" + id + ", adminId='" + adminId + '\'' + '}';
    }
}
