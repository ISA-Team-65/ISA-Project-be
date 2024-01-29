package com.team65.isaproject.repository;

import com.team65.isaproject.model.equipment.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

    @Query("SELECT e FROM Equipment e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    public List<Equipment> findAllByNameContainingIgnoreCase(@Param("name") String name);

    public Optional<List<Equipment>> findAllByAppointmentId(int appointmentId);
    public void deleteAllByAppointmentId(int appointmentId);
}
