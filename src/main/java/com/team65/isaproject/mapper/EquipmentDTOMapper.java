package com.team65.isaproject.mapper;

import com.team65.isaproject.dto.AppointmentDTO;
import com.team65.isaproject.dto.EquipmentDTO;
import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.model.equipment.Equipment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EquipmentDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public EquipmentDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Equipment fromDTOtoEquipment(EquipmentDTO equipmentDTO){
        return modelMapper.map(equipmentDTO, Equipment.class);
    }

    public static EquipmentDTO fromEquipmenttoDTO(Equipment equipment){
        return modelMapper.map(equipment, EquipmentDTO.class);
    }
}
