package com.team65.isaproject.service;

import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;


    public List<Appointment> findAll(){
        return appointmentRepository.findAll();
    }

    public Appointment findById(Integer id){
        return appointmentRepository.findById(id).orElse(null);
    }

    public Appointment update(Appointment appointment){
        Appointment temp = findById(appointment.getId());
        if(temp != null){

            temp.setAdminLastname(appointment.getAdminLastname());
            temp.setAdminName(appointment.getAdminName());
            temp.setDuration(appointment.getDuration());
            temp.setReserved(appointment.isReserved());
            temp.setDateTime(appointment.getDateTime());
            temp.setStatus(appointment.getStatus());
            temp.setUser_id(appointment.getUser_id());
            temp.setCompany_id(appointment.getCompany_id());

            return appointmentRepository.save(temp);
        }
        return null;
    }

    public Appointment save(Appointment appointment){
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointmentsByCompanyId(Integer id){

        ArrayList<Appointment> appointments = new ArrayList<>();

        for(Appointment a : findAll()){
            if(a.getCompany_id().equals(id)){
                appointments.add(a);
            }
        }

        return appointments;
    }
}
