//package com.team65.isaproject.controller;
//
//import com.team65.isaproject.dto.AddressDTO;
//import com.team65.isaproject.dto.CompanyDTO;
//import com.team65.isaproject.mapper.AddressDTOMapper;
//import com.team65.isaproject.model.Address;
//import com.team65.isaproject.model.Company;
//import com.team65.isaproject.service.AddressService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping(value = "api/addresses")
//public class AddressController {
//
//    @Autowired
//    private AddressService addressService;
//
//    @Autowired
//    private AddressDTOMapper addressDTOMapperd;
//
//    @GetMapping(value = "/{id}")
//    public ResponseEntity<AddressDTO> getAddress(@PathVariable Integer id)
//    {
//        Address address = addressService.findById(id);
//
//        if (address == null)
//        {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return new ResponseEntity<>(new AddressDTO(address), HttpStatus.OK);
//    }
//}
