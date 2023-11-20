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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "api/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentDTOMapper equipmentDTOMapper;

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
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

    @GetMapping(value = "/byCompanyId/{id}")
    @PreAuthorize("hasAnyRole( 'USER', 'COMPANY_ADMIN')")
    public ResponseEntity<List<EquipmentDTO>> getAllByCompanyId(@PathVariable Integer id){
        List<Equipment> equipment = equipmentService.getAllEquipmentByCompanyId(id);

        List<EquipmentDTO> equipmentDTOS = new ArrayList<>();

        for(Equipment e : equipment){
            equipmentDTOS.add(new EquipmentDTO(e));
        }

        if(equipmentDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(equipmentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/allEquipments")
    public ResponseEntity<List<EquipmentDTO>> getAll()
    {
        List<Equipment> equipments = equipmentService.findAll();

        List<EquipmentDTO> equipmentDTOS = new ArrayList<>();

        for(Equipment e : equipments){
            equipmentDTOS.add(new EquipmentDTO(e));
        }

        if(equipmentDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(equipmentDTOS, HttpStatus.OK);
    }

    @GetMapping("/test/{name}")
    public ResponseEntity<List<EquipmentDTO>> getAllByName(@PathVariable String name)
    {
        List<Equipment> equipments = equipmentService.findAllByName(name);

        List<EquipmentDTO> equipmentDTOS = new ArrayList<>();

        for(Equipment e : equipments)
        {
            equipmentDTOS.add(new EquipmentDTO(e));
        }

        if(equipmentDTOS.isEmpty()){
            return new ResponseEntity<>(equipmentDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(equipmentDTOS, HttpStatus.OK);
    }
}
