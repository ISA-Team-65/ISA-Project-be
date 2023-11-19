//package com.team65.isaproject.service;
//
//import com.team65.isaproject.model.Address;
//import com.team65.isaproject.repository.AddressRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AddressService {
//    @Autowired
//    private AddressRepository addressRepository;
//
//    public List<Address> findAll(){
//        return addressRepository.findAll();
//    }
//
//    public Address findById(Integer id){
//        return addressRepository.findById(id).orElse(null);
//    }
//
//    public Address save(Address address){
//        return addressRepository.save(address);
//    }
//
//}
