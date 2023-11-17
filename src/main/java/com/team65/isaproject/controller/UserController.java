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

import java.util.ArrayList;
import java.util.List;

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

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

        User user = userService.findById(userDTO.getId());
//        user = userService.update(userDTOMapper.);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setProfession(userDTO.getProfession());
        user.setType(userDTO.getType());

        user = userService.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }

    @GetMapping(value = "/byCompanyId/{id}")
    public ResponseEntity<List<UserDTO>> getAllByCompanyId(@PathVariable Integer id){
        List<User> users = userService.getAllUsersByCompanyId(id);

        List<UserDTO> userDTOS = new ArrayList<>();

        for(User u : users){
            userDTOS.add(new UserDTO(u));
        }

        if(userDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }
}
