package com.team65.isaproject.service;

import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.model.equipment.Equipment;
import com.team65.isaproject.model.user.User;
import com.team65.isaproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Integer id){
        return userRepository.findById(id).orElse(null);
    }

    public User update(User user){
        User temp = findById(user.getId());
        if(temp != null){
            temp.setAddress(user.getAddress());
            temp.setEmail(user.getEmail());
            temp.setPassword(user.getPassword());
            temp.setFirstName(user.getFirstName());
            temp.setLastName(user.getLastName());
            temp.setPhoneNumber(user.getPhoneNumber());
            temp.setProfession(user.getProfession());
            temp.setType(user.getType());

            return userRepository.save(temp);
        }
        return null;
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsersByCompanyId(Integer id){

        ArrayList<User> users = new ArrayList<>();

        for(User u : findAll()){
            if(u.getCompany_id().equals(id)){
                users.add(u);
            }
        }

        return users;
    }

}
