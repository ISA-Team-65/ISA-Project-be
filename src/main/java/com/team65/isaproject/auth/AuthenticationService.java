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

    public Optional<AuthenticationResponse> register(RegisterRequest request, boolean isCompanyAdmin) {
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
                .role(isCompanyAdmin ? Role.COMPANY_ADMIN : Role.USER)
                .enabled(isCompanyAdmin)
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
}
