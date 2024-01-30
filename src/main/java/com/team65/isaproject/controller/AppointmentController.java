package com.team65.isaproject.controller;

import com.google.zxing.NotFoundException;
import com.team65.isaproject.dto.AppointmentDTO;
import com.team65.isaproject.mapper.Mapper;
import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.service.AppointmentService;
import com.team65.isaproject.service.EquipmentService;
import com.team65.isaproject.service.QRCodeService;
import com.team65.isaproject.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/appointments")
@Tag(name = "Appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final Mapper<Appointment, AppointmentDTO> mapper;
    private final QRCodeService qrCodeService;
    private final EquipmentService equipmentService;
    private final UserService userService;

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'COMPANY_ADMIN')")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {

        return appointmentService.create(appointmentDTO).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping(consumes = "application/json", value = "/createWithoutMail")
    @PreAuthorize("hasAnyRole('USER', 'COMPANY_ADMIN')")
    public ResponseEntity<AppointmentDTO> createAppointmentWithoutMail(@RequestBody AppointmentDTO appointmentDTO) {

        return appointmentService.createAppointmentWithoutMail(appointmentDTO).map(ResponseEntity::ok).orElse(ResponseEntity.badRequest().build());
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('USER', 'COMPANY_ADMIN')")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Integer id)
    {
        Appointment appointment = appointmentService.findById(id);

        if (appointment == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.mapToDto(appointment, AppointmentDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/byCompanyId/{id}")
    @PreAuthorize("hasAnyRole('USER', 'COMPANY_ADMIN')")
    public ResponseEntity<List<AppointmentDTO>> getAllByCompanyId(@PathVariable Integer id){
        List<Appointment> appointments = appointmentService.getAllAppointmentsByCompanyId(id);

        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();

        for(Appointment a : appointments){
            appointmentDTOS.add(mapper.mapToDto(a, AppointmentDTO.class));
        }

//        if(appointmentDTOS.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('USER', 'COMPANY_ADMIN')")
    public ResponseEntity<AppointmentDTO> updateAppointment(@RequestBody AppointmentDTO appointmentDTO){
        var appointment = appointmentService.update(appointmentDTO);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PostMapping(path = "/decodeQR", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<String> decodeQRCode(@RequestBody MultipartFile file) throws NotFoundException, IOException {
        var decodedQR = qrCodeService.decodeQRCode(file);
        var checkIfPickUpDatePassed = appointmentService.checkIfPickUpDatePassed(decodedQR);
        if (!checkIfPickUpDatePassed) {
            var userId = appointmentService.penaliseAppointment(decodedQR);
            userService.penalize(userId, 2);
            return ResponseEntity.ok("penalized");
        }
        return ResponseEntity.ok(decodedQR);
    }

    @PostMapping(path = "/doPickUpEquipment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<String> doPickUpEquipment(@RequestBody MultipartFile file) throws NotFoundException, IOException {
        var decodedQR = qrCodeService.decodeQRCode(file);
        var appointmentId = appointmentService.update(decodedQR);
        equipmentService.removeAllByAppointmentId(appointmentId);
        return ResponseEntity.ok("done");
    }
    
    @GetMapping(value = "/byUserId/{id}")
    @PreAuthorize("hasAnyRole('USER', 'COMPANY_ADMIN')")
    public ResponseEntity<List<AppointmentDTO>> getAllByUserId(@PathVariable Integer id) {
        List<Appointment> appointments = appointmentService.getAllAppointmentsByUserId(id);

        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();

        for (Appointment a : appointments) {
            appointmentDTOS.add(mapper.mapToDto(a, AppointmentDTO.class));
        }

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> cancelAppointment(@PathVariable Integer id, @PathVariable Integer userId) {
        var response = appointmentService.cancel(id, userId);
        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/byAdminId/{id}")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<List<AppointmentDTO>> getAllByAdminId(@PathVariable Integer id) {
        List<Appointment> appointments = appointmentService.getAllAppointmentsByAdminId(id);

        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();

        for (Appointment a : appointments) {
            appointmentDTOS.add(mapper.mapToDto(a, AppointmentDTO.class));
        }

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/getAvailable/{companyId}")
    public ResponseEntity<List<AppointmentDTO>> getAvailableAppointments(@PathVariable Integer companyId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) String date) {
        LocalDateTime localDateTime = LocalDateTime.parse(date);

        List<AppointmentDTO> appointmentDTOS = appointmentService.findAvailableAppointments(localDateTime, companyId);

        return new ResponseEntity<>(appointmentDTOS, HttpStatus.OK);
    }

    @PutMapping(value = "/penaliseAfterReservation/{userId}/{appointmentId}")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<AppointmentDTO> penaliseAfterReservation(@PathVariable Integer userId, @PathVariable Integer appointmentId){
        var appointment = appointmentService.penaliseAfterReservation(userId, appointmentId);
//        return new ResponseEntity<>(appointment, HttpStatus.OK);

        return new ResponseEntity<>(mapper.mapToDto(appointment, AppointmentDTO.class), HttpStatus.OK);
    }

    @PutMapping(value = "/pickup/{appointmentId}")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<AppointmentDTO> pickUpEquipment(@PathVariable Integer appointmentId){
        var appointment = appointmentService.pickUpEquipment(appointmentId);
//        return new ResponseEntity<>(appointment, HttpStatus.OK);
        return new ResponseEntity<>(mapper.mapToDto(appointment, AppointmentDTO.class), HttpStatus.OK);
    }
}
