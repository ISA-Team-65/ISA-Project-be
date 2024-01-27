package com.team65.isaproject.controller;

import com.team65.isaproject.dto.AddressDTO;
import com.team65.isaproject.mapper.Mapper;
import com.team65.isaproject.model.Address;
import com.team65.isaproject.service.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="api/address")
@RequiredArgsConstructor
@Tag(name = "Address")
public class AddressController {

    private final AddressService addressService;
    private final Mapper<Address, AddressDTO> mapper;

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO){
        if(addressDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Address address = mapper.mapToModel(addressDTO, Address.class);
        address = addressService.save(address);
        return new ResponseEntity<>(mapper.mapToDto(address, AddressDTO.class), HttpStatus.CREATED);

    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'USER', 'COMPANY_ADMIN')")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Integer id){
        Address address = addressService.findById(id);

        if(address == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(mapper.mapToDto(address, AddressDTO.class), HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasAnyRole('SYSTEM_ADMIN', 'COMPANY_ADMIN')")
    public ResponseEntity<AddressDTO> updateAddress(@RequestBody AddressDTO addressDTO){
        Address address = addressService.findById(addressDTO.getId());

        if(address == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        address.setCity(addressDTO.getCity());
        address.setStreet(addressDTO.getStreet());
        address.setCountry(addressDTO.getCountry());
        address.setStreetNumber(addressDTO.getStreetNumber());
        address.setLatitude(addressDTO.getLatitude());
        address.setLongitude(addressDTO.getLongitude());

        address = addressService.save(address);
        return new ResponseEntity<>(mapper.mapToDto(address, AddressDTO.class), HttpStatus.OK);
    }

}
