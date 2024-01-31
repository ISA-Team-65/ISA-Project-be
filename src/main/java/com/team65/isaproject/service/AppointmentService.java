package com.team65.isaproject.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.team65.isaproject.dto.AppointmentDTO;
import com.team65.isaproject.dto.EquipmentDTO;
import com.team65.isaproject.mapper.Mapper;
import com.team65.isaproject.model.Company;
import com.team65.isaproject.model.DeliveryData;
import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.model.appointment.AppointmentStatus;
import com.team65.isaproject.model.equipment.Equipment;
import com.team65.isaproject.model.user.User;
import com.team65.isaproject.repository.AppointmentRepository;
import com.team65.isaproject.repository.CompanyRepository;
import com.team65.isaproject.repository.EquipmentRepository;
import com.team65.isaproject.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final Mapper<Appointment, AppointmentDTO> mapper;
    private final EmailService emailService;
    private final EquipmentService equipmentService;
    private final UserService userService;
    private final CompanyRepository companyRepository;
    private final AddressService addressService;
    private static final Logger log = LoggerFactory.getLogger(AppointmentService.class);
    private final RabbitTemplate rabbitTemplate;

    @Getter
    private final List<DeliveryData> deliveryData = new ArrayList<>();


    public List<Appointment> findAll(){
        return appointmentRepository.findAll();
    }

    public Appointment findById(Integer id){
        return appointmentRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = false)
    public AppointmentDTO update(AppointmentDTO appointmentDto) {
        try {
            var appointment = mapper.mapToModel(appointmentDto, Appointment.class);

            var existingAppointment = appointmentRepository.findById(appointment.getId());
            if (existingAppointment.isPresent() && existingAppointment.get().isReserved()) {
                throw new Exception("Appointment already reserved");
            }

            var temp = appointmentRepository.save(appointment);
            for (Equipment item :
                    appointment.getEquipmentList()) {
                item.setAppointment(appointment);
                equipmentService.save(item);
            }


            emailService.sendEmailWithQRCode(userRepository.findById(appointment.getUserId()).orElseThrow().getEmail(), temp);

            return mapper.mapToDto(temp, AppointmentDTO.class);
        } catch (Exception e) {
            return new AppointmentDTO();
        }
    }

    @Transactional(readOnly = false)
    public int update(String decodedQR) {
        var appointmentId = extractAppointmentId(decodedQR);
        var appointment = findById(appointmentId);
        appointment.setStatus(AppointmentStatus.PICKEDUP);
        appointment.setPickUpDateTime(LocalDateTime.now());

        emailService.sendEmailAfterDoneAppointment(userService.findById(appointment.getUserId()).getEmail(), "<div>\n" +
                "    <p>Congratulations, you've successfully picked up your reservation!</p>\n" +
                "</div>\n");

        var temp = appointmentRepository.save(appointment);

        return appointmentId;
    }

    public boolean checkIfPickUpDatePassed(String decodedQR) {
        var appointmentId = extractAppointmentId(decodedQR);
        var appointment = findById(appointmentId);

        LocalDateTime currentDateAndTime = LocalDateTime.now();
        LocalDateTime appointmentStartDateTime = appointment.getDateTime();

        LocalDateTime appointmentEndDateTime = appointmentStartDateTime.plus((int)appointment.getDuration(), ChronoUnit.MINUTES);
        var provera = currentDateAndTime.isBefore(appointmentEndDateTime);

        return provera;
    }

    public boolean checkIfPickUpDateDidntCome(String decodedQR) {
        var appointmentId = extractAppointmentId(decodedQR);
        var appointment = findById(appointmentId);

        LocalDateTime currentDateAndTime = LocalDateTime.now();

        return currentDateAndTime.toLocalDate().isBefore(appointment.getDateTime().toLocalDate());
    }

    @Transactional(readOnly = false)
    public int penaliseAppointment(String decodedQR) {
        var appointmentId = extractAppointmentId(decodedQR);
        var appointment = findById(appointmentId);

        for(Equipment e : appointment.getEquipmentList()){
            e.setAppointment(null);
            equipmentService.save(e);
        }

        appointment.setStatus(AppointmentStatus.PENALISED);
        appointmentRepository.save(appointment);

        return appointment.getUserId();
    }

    private static int extractAppointmentId(String decodedQR) {
        // Define the regular expression pattern for extracting appointmentId
        String pattern = "appointmentId: (-?\\d+)";
        Pattern regex = Pattern.compile(pattern);

        // Create a Matcher object
        Matcher matcher = regex.matcher(decodedQR);

        // Check if the pattern is found
        if (matcher.find()) {
            // Extract and parse the appointmentId from the matched group
            String appointmentIdStr = matcher.group(1);
            return Integer.parseInt(appointmentIdStr);
        } else {
            // Handle the case when appointmentId is not found
            throw new IllegalArgumentException("AppointmentId not found in the decodedQR string");
        }
    }

    public Appointment save(Appointment appointment){
        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointmentsByCompanyId(Integer id){

        ArrayList<Appointment> appointments = new ArrayList<>();

        for(Appointment a : findAll()){
            if(a.getCompanyId().equals(id)){
                appointments.add(a);
            }
        }

        return appointments;
    }

    @Transactional(readOnly = false)
    public Optional<AppointmentDTO> createAppointmentWithoutMail(AppointmentDTO appointmentDTO) {
        try {
            var appointment = mapper.mapToModel(appointmentDTO, Appointment.class);

            if (!isAdminAvailable(appointment)) {
                throw new Exception("Admin is not available in at this time");
            };

            var newAppointment = appointmentRepository.save(appointment);
            return Optional.ofNullable(
                    mapper.mapToDto(
                            newAppointment,
                            AppointmentDTO.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional(readOnly = false)
    public Optional<AppointmentDTO> create(AppointmentDTO appointmentDTO) {
        try {
            var appointment = mapper.mapToModel(appointmentDTO, Appointment.class);

            if (!isAdminAvailable(appointment)) {
                throw new Exception("Admin is not available in at this time");
            };

            var newAppointment = appointmentRepository.save(appointment);

            if (!appointment.getEquipmentList().isEmpty()) {
                for (Equipment item :
                        appointment.getEquipmentList()) {
                    item.setAppointment(appointment);
                    equipmentService.save(item);
                }
            }

            emailService.sendEmailWithQRCode(userRepository.findById(appointment.getUserId()).orElseThrow().getEmail(), newAppointment);
            return Optional.ofNullable(
                    mapper.mapToDto(
                            newAppointment,
                            AppointmentDTO.class));

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private boolean isAdminAvailable(Appointment appointment) {
        var appointmentsWithSameAdmin = appointmentRepository.findAllByAdminId(appointment.getAdminId());
        if (appointmentsWithSameAdmin.isPresent()) {
            for (Appointment existingAppointment :
                    appointmentsWithSameAdmin.get()) {
                var existingDateTimeStart = existingAppointment.getDateTime();
                var existingDateTimeEnd = existingDateTimeStart.plusMinutes((int)existingAppointment.getDuration());
                if ((appointment.getDateTime().isBefore(existingDateTimeEnd) || appointment.getDateTime().isEqual(existingDateTimeEnd)) &&
                        (appointment.getDateTime().plusMinutes((int)appointment.getDuration()).isAfter(existingDateTimeStart) ||
                                appointment.getDateTime().plusMinutes((int)appointment.getDuration()).isEqual(existingDateTimeStart))) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Appointment> getAllAppointmentsByUserId(Integer id) {

        ArrayList<Appointment> appointments = new ArrayList<>();

        for (Appointment a : findAll()) {
            if (a.getUserId() != null && a.getUserId().equals(id)) {
                appointments.add(a);
            }
        }

        return appointments;
    }

    @Transactional(readOnly = false)
    public String cancel(Integer id, Integer userId) {
        try {
            equipmentService.removeAppointment(id);
            var appointment = appointmentRepository.findById(id);
            if (!Objects.equals(appointment.orElseThrow().getUserId(), userId)) throw new Exception("Invalid user");

            LocalDateTime appointmentDateTime = appointment.orElseThrow().getDateTime();
            Duration timeUntilAppointment = Duration.between(LocalDateTime.now(), appointmentDateTime);
            boolean isLessThan24Hours = timeUntilAppointment.compareTo(Duration.ofHours(24)) < 0;

            if (isLessThan24Hours) {
                userService.penalize(userId, 2);
            } else {
                userService.penalize(userId, 1);
            }
            appointment.get().setReserved(false);
            appointment.get().setUserId(null);
            appointmentRepository.save(appointment.get());
            return "Appointment canceled";
        } catch (Exception e) {
            return "Cancellation unsuccessful";
        }
    }

    @Transactional(readOnly = false)
    public String delete(Integer id) {
        try {
            equipmentService.removeAppointment(id);
            appointmentRepository.deleteById(id);
            return "Appointment deleted";
        } catch (Exception e) {
            return "Deletion unsuccessful";
        }
    }

    public List<Appointment> getAllAppointmentsByAdminId(Integer id) {

        ArrayList<Appointment> appointments = new ArrayList<>();

        for (Appointment a : findAll()) {
            if (a.getAdminId() != null && a.getAdminId().equals(id) && a.isReserved()) {
                appointments.add(a);
            }
        }

        return appointments;
    }

    @Transactional(readOnly = false)
    public Appointment penaliseAfterReservation(Integer userId, Integer appointmentId){
        Appointment appointment = findById(appointmentId);

        for(Equipment e : appointment.getEquipmentList()){
            e.setAppointment(null);
            equipmentService.save(e);
        }

        userService.penalize(userId, 2);

        appointment.setStatus(AppointmentStatus.PENALISED);

        return appointmentRepository.save(appointment);
    }
    @Transactional(readOnly = false)
    public Appointment pickUpEquipment(Integer appointmentId) {
        Appointment appointment = findById(appointmentId);

        for (Equipment e : appointment.getEquipmentList()) {
            equipmentService.delete(e.getId());
        }

        appointment.setStatus(AppointmentStatus.PICKEDUP);
        appointment.setPickUpDateTime(LocalDateTime.now());

        emailService.sendEmailAfterDoneAppointment(userService.findById(appointment.getUserId()).getEmail(), "<div>\n" +
                "    <p>Congratulations, you've successfully picked up your reservation!</p>\n" +
                "</div>\n");

        return appointmentRepository.save(appointment);
    }
    public List<AppointmentDTO> findAvailableAppointments(LocalDateTime dateTime, Integer companyId) {
        List<AppointmentDTO> availableAppointments = new ArrayList<AppointmentDTO>();
        for (int i = 0; i < 8; i++) {
            var dateForAppointment = dateTime.withHour(8 + i + 1).withMinute(0);
            var potentialAdmins = userService.findByCompanyId(companyId);
            for (User admin :
                    potentialAdmins) {
                var potentialAppointment = AppointmentDTO
                        .builder()
                        .id(0)
                        .dateTime(dateForAppointment)
                        .duration(60.0)
                        .status(AppointmentStatus.NEW)
                        .companyId(companyId)
                        .isReserved(false)
                        .userId(null)
                        .adminId(admin.getId())
                        .equipmentList(new ArrayList<EquipmentDTO>())
                        .build();

                if (isAdminAvailable(mapper.mapToModel(potentialAppointment, Appointment.class))) {
                    availableAppointments.add(potentialAppointment);
                    break;
                }
            }
        }
        return availableAppointments;
    }

    public String startDelivery(Integer appointmentId, Integer intervalInSeconds) {
        try {
            deliveryData.clear();

            var appointment = appointmentRepository.findById(appointmentId);
            var company = companyRepository.findById(appointment.orElseThrow().getCompanyId());
            var user = userRepository.findById(appointment.get().getUserId());
            var userAddress = addressService.findById(user.orElseThrow().getAddressId());
            String message = buildMessage(company, userAddress, intervalInSeconds);
            log.info("Sending> ... Message=[ " + message + " ] RoutingKey=[ delivery ]");
            rabbitTemplate.convertAndSend("delivery", message);
            return "Delivery started";
        } catch (Exception e) {
            return "Error occurred";
        }
    }

    private String buildMessage(Optional<Company> company, com.team65.isaproject.model.Address userAddress, Integer intervalInSeconds) {
        return String.format(
                """
                    
                    {
                        lat: %s,
                        lng: %s,
                    },
                    {
                        lat: %s,
                        lng: %s,
                    },
                    %s
                """,
                company.orElseThrow().getAddress().getLatitude(),
                company.orElseThrow().getAddress().getLongitude(),
                userAddress.getLatitude(),
                userAddress.getLongitude(),
                intervalInSeconds);
    }

    @RabbitListener(queues = "deliveryResponse")
    public void deliveryHandler(String message) {
        DeliveryData data = parseDeliveryMessage(message);
        deliveryData.add(data);
    }

    private DeliveryData parseDeliveryMessage(String message) {
        String pattern = "lat: (-?\\d+\\.\\d+),\\s+lng: (-?\\d+\\.\\d+)";

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(message);

        double lat = 0;
        double lng = 0;

        if (matcher.find()) {
            lat = Double.parseDouble(matcher.group(1));
            lng = Double.parseDouble(matcher.group(2));
        }
        return new DeliveryData(lat, lng);
    }
}
