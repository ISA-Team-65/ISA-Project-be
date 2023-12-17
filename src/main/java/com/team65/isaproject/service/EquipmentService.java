package com.team65.isaproject.service;

import com.team65.isaproject.dto.EquipmentDTO;
import com.team65.isaproject.mapper.Mapper;
import com.team65.isaproject.model.equipment.Equipment;
import com.team65.isaproject.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final Mapper<Equipment, EquipmentDTO> mapper;

    public List<Equipment> findAll(){
        return equipmentRepository.findAll();
    }

    public EquipmentDTO findById(Integer id){
        return mapper.MapToDto(equipmentRepository.findById(id).orElse(null), EquipmentDTO.class);
    }

//    public Equipment update(Equipment equipment){
//        Equipment temp = findById(equipment.getId());
//        if(temp != null){
//            return equipmentRepository.save(temp);
//        }
//        return null;
//    }

    public EquipmentDTO save(EquipmentDTO equipmentDto){

        return mapper.MapToDto(equipmentRepository.save(mapper.MapToModel(equipmentDto, Equipment.class)), EquipmentDTO.class);
    }

    public List<Equipment> getAllEquipmentByCompanyId(Integer id){

        ArrayList<Equipment> equipment = new ArrayList<>();

        for(Equipment e : findAll()){
            if(e.getCompanyId().equals(id)){
                equipment.add(e);
            }
        }

        return equipment;
    }

    public List<Equipment> findAllByName(String name) {

        return equipmentRepository.findAllByNameContainingIgnoreCase(name);
    }

    public void delete(Integer id){
        equipmentRepository.deleteById(id);
    }
}
