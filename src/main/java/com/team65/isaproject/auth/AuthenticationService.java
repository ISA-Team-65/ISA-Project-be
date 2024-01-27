package com.team65.isaproject.auth;

import com.team65.isaproject.model.user.Role;
import com.team65.isaproject.model.user.User;
import com.team65.isaproject.repository.UserRepository;
import com.team65.isaproject.security.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Optional<AuthenticationResponse> register(RegisterRequest request, int userRole) {

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .addressId(request.getAddressId())
                .phoneNumber(request.getPhoneNumber())
                .profession(request.getProfession())
                .companyId(request.getCompanyId())
                .role(getUserRole(userRole))
                .enabled(userRole != 0)
                .lastPasswordResetDate(null)
                .build();
        var usernameOrEmailExists = (
                repository.findByEmail(request.getEmail()).isPresent() ||
                repository.findByUsername(request.getUsername()).isPresent());
        if (usernameOrEmailExists) {
            return Optional.empty();
        }
        var createdUser = repository.save(user);
        if (userRole == 0) {
            sendEmail(user.getEmail(), createdUser.getId());
        }
        var jwtToken = jwtService.generateToken(user);
        return Optional.ofNullable(AuthenticationResponse.builder()
                .token(jwtToken)
                .build());
    }

    public Optional<AuthenticationResponse> changePassword (AuthenticationRequest request, int userRole) {
        var usernameOrEmailExists = (
                        repository.findByUsername(request.getUsername()).isPresent());
        if (!usernameOrEmailExists) {
            return Optional.empty();
        }

        Optional<User> existingUser = repository.findByUsername(request.getUsername());

        User user = existingUser.get();

        // Update the password and any other fields if needed
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        // Update other fields as needed...

        // Save the updated user
        repository.save(user);

        // Generate and return the JWT token
        var jwtToken = jwtService.generateToken(user);
        return Optional.ofNullable(AuthenticationResponse.builder()
                .token(jwtToken)
                .build());
    }

    public Optional<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            var user = repository.findByUsername(request.getUsername()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return Optional.ofNullable(AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build());
        }
        catch (Exception e) {
            return Optional.empty();
        }
    }

    private void sendEmail(String receiver, Integer userId) {
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

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

            // Set Subject: header field
            message.setSubject("Thank you for registering an account!");

            // Now set the actual message
            message.setContent(
                    "<h3>Click this link to activate your account <a href=\"http://localhost:3000/activate/" + userId + "\">link</a></h3>",
                    "text/html");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private Role getUserRole(int userRole) {
        switch (userRole) {
            case 1:
                return Role.COMPANY_ADMIN;
            case 2:
                return Role.SYSTEM_ADMIN;
            default:
                return Role.USER;
        }
    }
}
