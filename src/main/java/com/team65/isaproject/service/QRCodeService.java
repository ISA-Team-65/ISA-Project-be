package com.team65.isaproject.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.repository.CompanyRepository;
import com.team65.isaproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import com.google.zxing.NotFoundException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class QRCodeService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public BufferedImage generateQRCode(String data) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public String generateQRCodeData(Appointment appointment) {
        //List<Equipment> equipmentAll = equipmentRepository.findByAppointmentId(appointment.getId());
        var receiver = userRepository.findById(appointment.getUserId()).orElseThrow().getFirstName();
        var administrator = userRepository.findById(appointment.getAdminId()).orElseThrow().getFirstName() +
                userRepository.findById(appointment.getAdminId()).orElseThrow().getLastName();
        var company = companyRepository.findById(appointment.getCompanyId()).orElseThrow().getCompanyName();
        //List<String> equipmentNames = new ArrayList<String>();
        //var equipmentAll = appointment.getEquipmentList();
        //String equipmentList = new String();

//        for (Equipment e: equipmentAll
//             ) {
//            equipmentNames.add(e.getName());
//            equipmentList += e.getName() + ", ";
//        }

        return String.format("{Date & time: %s,\n" +
                        //"equipment: %s" +
                        "receiver: %s,\n" +
                        "administrator: %s,\n" +
                        "company: %s,\n" +
                        "appointmentId: %d\n" +
                        "}",
                appointment.getDateTime().toString(),
                //equipmentList,
                receiver,
                administrator,
                company,
                appointment.getId());
    }

    public String decodeQRCode(MultipartFile qrCodeImage) throws IOException, NotFoundException {
        try {
            // Check if the file is an image
            if (!qrCodeImage.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("Uploaded file is not an image.");
            }

            // Obtain an InputStream from the MultipartFile
            try (InputStream inputStream = qrCodeImage.getInputStream()) {
                BufferedImage bufferedImage = ImageIO.read(inputStream);
                LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                Result result = new MultiFormatReader().decode(bitmap);
                return result.getText();
            }
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error decoding QR code", e);
        }
    }
}
