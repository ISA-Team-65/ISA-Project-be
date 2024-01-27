package com.team65.isaproject.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.team65.isaproject.model.appointment.Appointment;
import com.team65.isaproject.repository.CompanyRepository;
import com.team65.isaproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    public void sendEmailWithQRCode(String receiver, Appointment appointment) {
        // Sender's email ID needs to be mentioned
        String from = "isaproject96@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("isaproject96@gmail.com", "camx qqtr puak dbrl");

            }

        });

//         Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

            // Set Subject: header field
            message.setSubject("QR Code");

            String msg = "QR Code";

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            var qrCode = generateQRCode(generateQRCodeData(appointment));
            File outputfile = new File("image.jpg");
            ImageIO.write(qrCode, "jpg", outputfile);
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File("image.jpg"));

            multipart.addBodyPart(attachmentBodyPart);

            // Now set the actual message
            message.setContent(multipart);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BufferedImage generateQRCode(String data) throws WriterException {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    private String generateQRCodeData(Appointment appointment) {
        // var equipment = equipmentRepository.findByAppointmentId(appointment.getId()).orElseThrow().getName();
        var receiver = userRepository.findById(appointment.getUserId()).orElseThrow().getFirstName();
        var administrator = appointment.getAdminName() + appointment.getAdminLastname();
        var company = companyRepository.findById(appointment.getCompanyId()).orElseThrow().getCompanyName();

        return String.format("{Date & time: %s,\n" +
                        /*"equipment: %s," +*/
                        "receiver: %s,\n" +
                        "administrator: %s,\n" +
                        "company: %s\n" +
                        "}",
                appointment.getDateTime().toString(),
                //equipment,
                receiver,
                administrator,
                company);
    }
}
