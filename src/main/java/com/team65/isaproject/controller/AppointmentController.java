package com.team65.isaproject.controller;

import com.team65.isaproject.dto.AppointmentDTO;
import com.team65.isaproject.mapper.AppointmentDTOMapper;
import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentDTOMapper appointmentDTOMapper;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO){
        //ovde bi isla validacija
        if(appointmentDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Appointment appointment = AppointmentDTOMapper.fromDTOtoAppointment(appointmentDTO);
        appointment = appointmentService.save(appointment);
        return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Integer id)
    {
        Appointment appointment = appointmentService.findById(id);

        if (appointment == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.OK);
    }

}
