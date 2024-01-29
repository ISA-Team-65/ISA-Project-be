package com.team65.isaproject.service;

import com.team65.isaproject.dto.EquipmentDTO;
import com.team65.isaproject.mapper.Mapper;
import com.team65.isaproject.model.equipment.Equipment;
import com.team65.isaproject.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final Mapper<Equipment, EquipmentDTO> mapper;

    public List<Equipment> findAll(){
        return equipmentRepository.findAll();
    }

    public EquipmentDTO findById(Integer id){
        return mapper.mapToDto(equipmentRepository.findById(id).orElse(null), EquipmentDTO.class);
    }

//    public Equipment update(Equipment equipment){
//        Equipment temp = findById(equipment.getId());
//        if(temp != null){
//            return equipmentRepository.save(temp);
//        }
//        return null;
//    }

    public EquipmentDTO save(EquipmentDTO equipmentDto){

        return mapper.mapToDto(equipmentRepository.save(mapper.mapToModel(equipmentDto, Equipment.class)), EquipmentDTO.class);
    }

    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
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

    public void removeAppointment(Integer appointmentId) {
        var equipment = equipmentRepository.findAllByAppointmentId(appointmentId);
        if (equipment.isPresent()) {
            for (Equipment item :
                    equipment.get()) {
                item.setAppointment(null);
                equipmentRepository.save(item);
            }
        }
    }

    public Optional<List<Equipment>> findAllByAppointmentId(Integer id) {
        return equipmentRepository.findAllByAppointmentId(id);
    }

    public void removeAllByAppointmentId(Integer id) {
        equipmentRepository.removeAllByAppointmentId(id);
    }
}
