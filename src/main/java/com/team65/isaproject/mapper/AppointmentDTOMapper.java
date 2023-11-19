package com.team65.isaproject.mapper;
import com.team65.isaproject.dto.AppointmentDTO;
import com.team65.isaproject.model.appointment.Appointment;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public AppointmentDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Appointment fromDTOtoAppointment(AppointmentDTO appointmentDTO){
        return modelMapper.map(appointmentDTO, Appointment.class);
    }

    public static AppointmentDTO fromAppointmenttoDTO(Appointment appointment){
        return modelMapper.map(appointment, AppointmentDTO.class);
    }
}
