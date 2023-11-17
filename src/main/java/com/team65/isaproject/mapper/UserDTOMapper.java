package com.team65.isaproject.mapper;

import com.team65.isaproject.dto.AppointmentDTO;
import com.team65.isaproject.dto.UserDTO;
import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.model.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {
    private static ModelMapper modelMapper;

    @Autowired
    public UserDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static User fromDTOtoUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }

    public static UserDTO fromUsertoDTO(User user){
        return modelMapper.map(user, UserDTO.class);
    }
}
