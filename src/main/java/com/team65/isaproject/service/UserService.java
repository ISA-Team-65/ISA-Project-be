package com.team65.isaproject.service;

import com.team65.isaproject.dto.UserDTO;
import com.team65.isaproject.mapper.Mapper;
import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.model.user.Role;
import com.team65.isaproject.model.user.User;
import com.team65.isaproject.repository.AppointmentRepository;
import com.team65.isaproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final Mapper<User, UserDTO> mapper;
    private final AppointmentRepository appointmentRepository;


    public User update(User user) {
        User temp = repository.findById(user.getId()).orElseGet(null);
        if(temp != null){
//            temp.setAddress(user.getAddress());
            temp.setAddressId(user.getAddressId());
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

    public Optional<UserDTO> activateUserAccount(int id) {
        try {
            var user = repository.findById(id).orElseThrow();
            if (user.getRole() != Role.USER) throw new Exception();
            user.setEnabled(true);
            repository.save(user);
            return Optional.ofNullable(mapper.mapToDto(user, UserDTO.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void penalize(Integer id, Integer points) {
        var user = repository.findById(id);
        user.orElseThrow().setPenaltyPoints(user.get().getPenaltyPoints() + points);
        repository.save(user.get());
    }

    public List<User> getUsersThatReservedAppointment(Integer companyId){

        ArrayList<User> users = new ArrayList<>();

        for(User u : repository.findAll()){
            for(Appointment a : appointmentRepository.findAll()){
                if(Objects.equals(a.getUserId(), u.getId()) && !users.contains(u)){
                    users.add(u);
                }
            }
        }

        return users;
    }

    public List<User> findAll() { return repository.findAll(); }
}
