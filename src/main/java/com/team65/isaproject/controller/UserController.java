package com.team65.isaproject.controller;

import com.team65.isaproject.dto.AppointmentDTO;
import com.team65.isaproject.dto.UserDTO;
import com.team65.isaproject.mapper.AppointmentDTOMapper;
import com.team65.isaproject.mapper.UserDTOMapper;
import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.model.user.User;
import com.team65.isaproject.service.AppointmentService;
import com.team65.isaproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDTOMapper userDTOMapper;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        //ovde bi isla validacija
        if(userDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = UserDTOMapper.fromDTOtoUser(userDTO);
        user = userService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id)
    {
        User user = userService.findById(id);

        if (user == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }
}
