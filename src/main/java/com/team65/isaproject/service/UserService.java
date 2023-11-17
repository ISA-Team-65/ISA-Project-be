package com.team65.isaproject.service;

import com.team65.isaproject.model.equipment.Equipment;
import com.team65.isaproject.model.user.User;
import com.team65.isaproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return userRepository.save(temp);
        }
        return null;
    }

    public User save(User user){
        return userRepository.save(user);
    }


}
