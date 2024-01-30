package com.team65.isaproject.repository;

import com.team65.isaproject.model.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    public Optional<List<Appointment>> findAllByAdminId(Integer adminId);
}