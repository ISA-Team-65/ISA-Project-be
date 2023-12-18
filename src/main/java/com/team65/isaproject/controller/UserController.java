package com.team65.isaproject.controller;

import com.team65.isaproject.dto.UserDTO;
import com.team65.isaproject.mapper.Mapper;
import com.team65.isaproject.model.user.User;
import com.team65.isaproject.service.UserService;
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
@RequestMapping(value = "/api/users")
@Tag(name = "User")
public class UserController {

    private final UserService userService;
    private final Mapper<User, UserDTO> mapper;

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        //ovde bi isla validacija
        if(userDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = mapper.MapToModel(userDTO, User.class);
        user = userService.save(user);
        return new ResponseEntity<>(mapper.MapToDto(user, UserDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'USER', 'SYSTEM_ADMIN')")
    public User loadById(@PathVariable Integer userId) {
        return this.userService.findById(userId);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {

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

        user = userService.save(user);
        return new ResponseEntity<>(mapper.MapToDto(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/byCompanyId/{id}")
    public ResponseEntity<List<UserDTO>> getAllByCompanyId(@PathVariable Integer id){
        List<User> users = userService.getAllUsersByCompanyId(id);

        List<UserDTO> userDTOS = new ArrayList<>();

        for(User u : users){
            userDTOS.add(mapper.MapToDto(u, UserDTO.class));
        }

        if(userDTOS.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/getByUsername/{username}")
    @PreAuthorize("hasAnyRole('USER', 'COMPANY_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username)
    {
        User user = userService.findByUsername(username);

        if (user == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.MapToDto(user, UserDTO.class), HttpStatus.OK);
    }

    @PutMapping(value = "/user", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

        User user = userService.findById(userDTO.getId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setAddress(userDTO.getAddress());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setProfession(userDTO.getProfession());

        user = userService.save(user);
        return new ResponseEntity<>(mapper.MapToDto(user, UserDTO.class), HttpStatus.OK);
    }

    @PutMapping(value = "/userPassword", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUserPassword(@RequestBody UserDTO userDTO) {

        User user = userService.findById(userDTO.getId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setPassword(userDTO.getPassword());

        user = userService.save(user);
        return new ResponseEntity<>(mapper.MapToDto(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/checkLogging/{username}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'USER', 'SYSTEM_ADMIN')")
    public User isUserFirstTimeLogging(@PathVariable String username) {
        User user = userService.findByUsername(username);

        return user;
    }
}
