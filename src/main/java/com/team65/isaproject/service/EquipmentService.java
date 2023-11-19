package com.team65.isaproject.service;

import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.model.equipment.Equipment;
import com.team65.isaproject.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> findAll(){
        return equipmentRepository.findAll();
    }

    public Equipment findById(Integer id){
        return equipmentRepository.findById(id).orElse(null);
    }

    public Equipment update(Equipment equipment){
        Equipment temp = findById(equipment.getId());
        if(temp != null){
            return equipmentRepository.save(temp);
        }
        return null;
    }

    public Equipment save(Equipment equipment){
        return equipmentRepository.save(equipment);
    }

    public List<Equipment> getAllEquipmentByCompanyId(Integer id){

        ArrayList<Equipment> equipment = new ArrayList<>();

        for(Equipment e : findAll()){
            if(e.getCompany_id().equals(id)){
                equipment.add(e);
            }
        }

        return equipment;
    }
}
