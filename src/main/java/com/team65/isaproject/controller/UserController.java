package com.team65.isaproject.controller;

import com.team65.isaproject.auth.AuthenticationResponse;
import com.team65.isaproject.dto.UserDTO;
import com.team65.isaproject.mapper.Mapper;
import com.team65.isaproject.model.user.User;
import com.team65.isaproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
@Tag(name = "User")
public class UserController {

    private final UserService userService;
    private final Mapper<User, UserDTO> mapper;

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        //ovde bi isla validacija
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = mapper.mapToModel(userDTO, User.class);
        user = userService.save(user);
        return new ResponseEntity<>(mapper.mapToDto(user, UserDTO.class), HttpStatus.CREATED);
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

//        user.setAddress(userDTO.getAddress());
        user.setAddressId(userDTO.getAddressId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setProfession(userDTO.getProfession());

        user = userService.save(user);
        return new ResponseEntity<>(mapper.mapToDto(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping(value = "/byCompanyId/{id}")
    public ResponseEntity<List<UserDTO>> getAllByCompanyId(@PathVariable Integer id) {
        List<User> users = userService.getAllUsersByCompanyId(id);

        List<UserDTO> userDTOS = new ArrayList<>();

        for (User u : users) {
            userDTOS.add(mapper.mapToDto(u, UserDTO.class));
        }

//        if(userDTOS.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/getByUsername/{username}")
    @PreAuthorize("hasAnyRole('USER', 'COMPANY_ADMIN', 'SYSTEM_ADMIN')")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(mapper.mapToDto(user, UserDTO.class), HttpStatus.OK);
    }

    @PutMapping(value = "/user", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {

        User user = userService.findById(userDTO.getId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

//        user.setAddress(userDTO.getAddress());
        user.setAddressId(userDTO.getAddressId());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setProfession(userDTO.getProfession());

        user = userService.save(user);
        return new ResponseEntity<>(mapper.mapToDto(user, UserDTO.class), HttpStatus.OK);
    }

    @Operation(summary = "Activate user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)})
    @PutMapping(value = "/activate")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserDTO> activateUser(
            @RequestParam Integer userId
    ) {
        var result = userService.activateUserAccount(userId);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/userPassword", consumes = "application/json")
    public ResponseEntity<UserDTO> updateUserPassword(@RequestBody UserDTO userDTO) {

        User user = userService.findById(userDTO.getId());

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setPassword(userDTO.getPassword());

        user = userService.save(user);
        return new ResponseEntity<>(mapper.mapToDto(user, UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/checkLogging/{username}")
    @PreAuthorize("hasAnyRole('COMPANY_ADMIN', 'USER', 'SYSTEM_ADMIN')")
    public User isUserFirstTimeLogging(@PathVariable String username) {
        User user = userService.findByUsername(username);

        return user;
    }

    @GetMapping(value = "/getUsersThatReservedAppointment/{companyId}")
    @PreAuthorize("hasRole('COMPANY_ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsersThatReservedAppointment(@PathVariable Integer companyId) {
        List<User> users = userService.getUsersThatReservedAppointment(companyId);

        List<UserDTO> userDTOS = new ArrayList<>();

        for (User u : users) {
            userDTOS.add(mapper.mapToDto(u, UserDTO.class));
        }

        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

}