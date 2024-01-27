package com.team65.isaproject.service;

import com.team65.isaproject.model.Address;
import com.team65.isaproject.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public List<Address> findAll() { return addressRepository.findAll(); }

    public Address findById(Integer id) { return addressRepository.findById(id).orElse(null); }

    public Address update(Address address){
        Address temp = findById(address.getId());
        if(temp != null){
            return addressRepository.save(temp);
        }
        return null;
    }

    public Address save(Address address) { return addressRepository.save(address); }

}
