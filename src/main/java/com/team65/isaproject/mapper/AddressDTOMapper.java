//package com.team65.isaproject.mapper;
//
//import com.team65.isaproject.dto.AddressDTO;
//import com.team65.isaproject.model.Address;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AddressDTOMapper {
//    private static ModelMapper modelMapper;
//
//    @Autowired
//
//    public AddressDTOMapper(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }
//
//    public static Address fromDTOtoAddress(AddressDTO addressDTO){
//        return modelMapper.map(addressDTO, Address.class);
//    }
//
//    public static AddressDTO fromAddresstoDTO(Address addressDTO){
//        return modelMapper.map(addressDTO, AddressDTO.class);
//    }
//
//}
