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
import java.util.Optional;

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
                .address(request.getAddress())
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
        repository.save(user);
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
