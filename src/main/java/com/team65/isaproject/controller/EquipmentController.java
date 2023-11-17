package com.team65.isaproject.controller;

import com.team65.isaproject.dto.AppointmentDTO;
import com.team65.isaproject.dto.EquipmentDTO;
import com.team65.isaproject.mapper.AppointmentDTOMapper;
import com.team65.isaproject.mapper.EquipmentDTOMapper;
import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.model.equipment.Equipment;
import com.team65.isaproject.service.AppointmentService;
import com.team65.isaproject.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentDTOMapper equipmentDTOMapper;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<EquipmentDTO> createEquipment(@RequestBody EquipmentDTO equipmentDTO){
        //ovde bi isla validacija
        if(equipmentDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Equipment equipment = EquipmentDTOMapper.fromDTOtoEquipment(equipmentDTO);
        equipment = equipmentService.save(equipment);
        return new ResponseEntity<>(new EquipmentDTO(equipment), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EquipmentDTO> getEquipment(@PathVariable Integer id)
    {
        Equipment equipment = equipmentService.findById(id);

        if (equipment == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new EquipmentDTO(equipment), HttpStatus.OK);
    }
}
