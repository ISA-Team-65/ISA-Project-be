package com.team65.isaproject.controller;

import com.team65.isaproject.dto.AppointmentDTO;
import com.team65.isaproject.mapper.Mapper;
import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.service.AppointmentService;
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
@RequestMapping(value = "api/appointments")
@Tag(name = "Appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final Mapper<Appointment, AppointmentDTO> mapper;

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'COMPANY_ADMIN')")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO){
        //ovde bi isla validacija
        if(appointmentDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Appointment appointment = mapper.MapToModel(appointmentDTO, Appointment.class);
        appointment = appointmentService.save(appointment);
        return new ResponseEntity<>(mapper.MapToDto(appointment, AppointmentDTO.class), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Integer id)
    {
        Appointment appointment = appointmentService.findById(id);

        if (appointment == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.MapToDto(appointment, AppointmentDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/byCompanyId/{id}")
    //@PreAuthorize("hasAnyRole('USER', 'COMPANY_ADMIN')")
    public ResponseEntity<List<AppointmentDTO>> getAllByCompanyId(@PathVariable Integer id){
        List<Appointment> appointments = appointmentService.getAllAppointmentsByCompanyId(id);

        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();

        for(Appointment a : appointments){
            appointmentDTOS.add(mapper.MapToDto(a, AppointmentDTO.class));
        }

        if(appointmentDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
//    @PreAuthorize("hasAnyRole('USER', 'COMPANY_ADMIN')")
    public ResponseEntity<AppointmentDTO> updateAppointment(@RequestBody AppointmentDTO appointmentDTO){
        Appointment appointment = appointmentService.findById(appointmentDTO.getId());

        if(appointment == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        appointment.setAdminLastname(appointmentDTO.getAdminLastname());
        appointment.setAdminName(appointmentDTO.getAdminName());
        appointment.setDuration(appointmentDTO.getDuration());
        appointment.setReserved(appointmentDTO.isReserved());
        appointment.setDateTime(appointmentDTO.getDateTime());
        appointment.setStatus(appointmentDTO.getStatus());
        appointment.setUserId(appointmentDTO.getUserId());
        appointment.setCompanyId(appointmentDTO.getCompanyId());

        appointment = appointmentService.save(appointment);
        return new ResponseEntity<>(mapper.MapToDto(appointment, AppointmentDTO.class), HttpStatus.OK);
    }
}
