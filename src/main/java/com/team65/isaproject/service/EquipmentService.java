package com.team65.isaproject.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team65.isaproject.dto.ContractDTO;
import com.team65.isaproject.dto.EquipmentDTO;
import com.team65.isaproject.mapper.Mapper;
import com.team65.isaproject.model.DeliveryData;
import com.team65.isaproject.model.equipment.Equipment;
import com.team65.isaproject.model.equipment.EquipmentType;
import com.team65.isaproject.repository.EquipmentRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.lang.Math.round;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private static final Logger log = LoggerFactory.getLogger(EquipmentService.class);
    private Timer timer;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final EquipmentRepository equipmentRepository;
    private final Mapper<Equipment, EquipmentDTO> mapper;

    @Getter
    private ContractDTO activeContract = new ContractDTO();

    public List<Equipment> findAll(){
        return equipmentRepository.findAll();
    }

    public EquipmentDTO findById(Integer id){
        return mapper.mapToDto(equipmentRepository.findById(id).orElse(null), EquipmentDTO.class);
    }

//    public Equipment update(Equipment equipment){
//        Equipment temp = findById(equipment.getId());
//        if(temp != null){
//            return equipmentRepository.save(temp);
//        }
//        return null;
//    }

    public EquipmentDTO save(EquipmentDTO equipmentDto){

        return mapper.mapToDto(equipmentRepository.save(mapper.mapToModel(equipmentDto, Equipment.class)), EquipmentDTO.class);
    }

    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public List<Equipment> getAllEquipmentByCompanyId(Integer id){

        ArrayList<Equipment> equipment = new ArrayList<>();

        for(Equipment e : findAll()){
            if(e.getCompanyId().equals(id)){
                equipment.add(e);
            }
        }

        return equipment;
    }

    public List<Equipment> findAllByName(String name) {

        return equipmentRepository.findAllByNameContainingIgnoreCase(name);
    }

    public void delete(Integer id){
        equipmentRepository.deleteById(id);
    }

    public void removeAppointment(Integer appointmentId) {
        var equipment = equipmentRepository.findAllByAppointmentId(appointmentId);
        if (equipment.isPresent()) {
            for (Equipment item :
                    equipment.get()) {
                item.setAppointment(null);
                equipmentRepository.save(item);
            }
        }
    }

    public Optional<List<Equipment>> findAllByAppointmentId(Integer id) {
        return equipmentRepository.findAllByAppointmentId(id);
    }

    public void removeAllByAppointmentId(Integer id) {
        var equipment = findAllByAppointmentId(id);
        if (equipment.isPresent()) {
            for (Equipment item :
                    equipment.get()) {
                equipmentRepository.deleteById(item.getId());
            }
        }
    }

    @RabbitListener(queues = "contract")
    public void contractHandler(String message) {
        log.info("Consumer> " + message);

        ObjectMapper objectMapper = new ObjectMapper();
        String contractJson = message;

        try {
            activeContract = objectMapper.readValue(contractJson, ContractDTO.class);
            try {
                LocalDate deliveryDate = LocalDate.parse(activeContract.getDeliveryDate());

                if (timer != null) {
                    timer.cancel();
                }

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (deliveryDate.minusDays(3).getDayOfMonth() == LocalDate.now().getDayOfMonth()) {
                            for (var eq : activeContract.getEquipment()) {
                                if (equipmentRepository.findAll().stream()
                                        .noneMatch(type -> eq.getType().equals(type.toString()))){
                                    log.info("Sending> ... Message=[ Agreed equipment quantity is unavailable for the upcoming delivery. ] RoutingKey=[ contractResponse ]");
                                    rabbitTemplate.convertAndSend("contractResponse", "Agreed equipment quantity is unavailable for the upcoming delivery.");
                                    break;
                                }
                            }
                        }
                        else if (deliveryDate.getDayOfMonth() == LocalDate.now().getDayOfMonth()) {
                            log.info("Sending> ... Message=[ Commencing equipment delivery. We're on our way! ] RoutingKey=[ contractResponse ]");
                            rabbitTemplate.convertAndSend("contractResponse", "Commencing equipment delivery. We're on our way!");
                        }
                        System.out.println(activeContract);
                    }
                }, 0, 86400000);
            } catch (Exception e) {
                log.error("Error parsing delivery date: " + activeContract.getDeliveryDate(), e);
            }

        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON", e);
        }
    }
}
