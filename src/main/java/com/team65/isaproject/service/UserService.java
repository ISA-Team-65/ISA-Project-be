package com.team65.isaproject.service;

import com.team65.isaproject.model.user.User;
import com.team65.isaproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;


    public User update(User user) {
        User temp = repository.findById(user.getId()).orElseGet(null);
        if(temp != null){
            temp.setAddress(user.getAddress());
            temp.setEmail(user.getEmail());
            temp.setPassword(user.getPassword());
            temp.setFirstName(user.getFirstName());
            temp.setLastName(user.getLastName());
            temp.setPhoneNumber(user.getPhoneNumber());
            temp.setProfession(user.getProfession());

            return repository.save(temp);
        }
        return null;
    }

    public User save(User user){
        return repository.save(user);
    }

    public List<User> getAllUsersByCompanyId(Integer id){

        ArrayList<User> users = new ArrayList<>();

        for(User u : repository.findAll()){
            if(u.getCompanyId() != null){
                if(u.getCompanyId().equals(id)){
                    users.add(u);
                }
            }
        }

        return users;
    }

    public User findById(Integer id) throws AccessDeniedException {
        return repository.findById(id).orElseGet(null);
    }

    public User findByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseGet(null);
    }
}
