package com.team65.isaproject.repository;

import com.team65.isaproject.model.equipment.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    public List<Equipment> findAllByName(String name);
}
