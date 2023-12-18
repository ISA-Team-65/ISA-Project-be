package com.team65.isaproject.controller;

import com.team65.isaproject.dto.EquipmentDTO;
import com.team65.isaproject.mapper.Mapper;
import com.team65.isaproject.model.equipment.Equipment;
import com.team65.isaproject.service.EquipmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping(path = "api/equipment")
@Tag(name = "Equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final Mapper<Equipment, EquipmentDTO> mapper;

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<EquipmentDTO> createEquipment(@RequestBody EquipmentDTO equipmentDTO){
        //ovde bi isla validacija
        if(equipmentDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

//        Equipment equipment = mapper.MapToModel(equipmentDTO, Equipment.class);
        equipmentDTO = equipmentService.save(equipmentDTO);
        return new ResponseEntity<>(equipmentDTO, HttpStatus.CREATED);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<EquipmentDTO> getEquipment(@PathVariable Integer id)
    {
        EquipmentDTO equipmentDTO = equipmentService.findById(id);

        if (equipmentDTO == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(equipmentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/byCompanyId/{id}")
    @PreAuthorize("hasAnyRole( 'USER', 'COMPANY_ADMIN')")
    public ResponseEntity<List<EquipmentDTO>> getAllByCompanyId(@PathVariable Integer id){
        List<Equipment> equipment = equipmentService.getAllEquipmentByCompanyId(id);

        List<EquipmentDTO> equipmentDTOS = new ArrayList<>();

        for(Equipment e : equipment){
            equipmentDTOS.add(mapper.mapToDto(e, EquipmentDTO.class));
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
            equipmentDTOS.add(mapper.mapToDto(e, EquipmentDTO.class));
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
            equipmentDTOS.add(mapper.mapToDto(e, EquipmentDTO.class));
        }

        if(equipmentDTOS.isEmpty()){
            return new ResponseEntity<>(equipmentDTOS, HttpStatus.OK);
        }
        return new ResponseEntity<>(equipmentDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        equipmentService.delete(id);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<EquipmentDTO> updateEquipment(@RequestBody EquipmentDTO equipmentDTO){
        EquipmentDTO updatedEquipmentDto = equipmentService.findById(equipmentDTO.getId());

        if(updatedEquipmentDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        updatedEquipmentDto.setName(equipmentDTO.getName());
        updatedEquipmentDto.setType(equipmentDTO.getType());
        updatedEquipmentDto.setDescription(equipmentDTO.getDescription());
        updatedEquipmentDto.setRating(equipmentDTO.getRating());
        updatedEquipmentDto.setPrice(equipmentDTO.getPrice());


        updatedEquipmentDto = equipmentService.save(updatedEquipmentDto);
        return new ResponseEntity<>( updatedEquipmentDto, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", value = "/addToAppointment")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<EquipmentDTO> addToAppointment(@RequestBody EquipmentDTO equipmentDTO){
        EquipmentDTO updatedEquipmentDto = equipmentService.findById(equipmentDTO.getId());

        updatedEquipmentDto.setAppointmentId(equipmentDTO.getAppointmentId());

        updatedEquipmentDto = equipmentService.save(updatedEquipmentDto);
        return new ResponseEntity<>( updatedEquipmentDto, HttpStatus.OK);
    }
}
