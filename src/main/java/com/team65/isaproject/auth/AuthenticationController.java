package com.team65.isaproject.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@Tag(name = "Authorization", description = "Log In and Register")
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(summary = "Register new regular user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Email or username already exist",
                    content = @Content) })
    @PostMapping(value="/registerUser", consumes="application/json")
    public ResponseEntity<AuthenticationResponse> registerUser(
            @RequestBody RegisterRequest request
    ) {
        var result = service.register(request, 0);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Register new company admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Email or username already exist",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized",
                    content = @Content) })
    @PostMapping(value = "/registerAdmin", consumes = "application/json")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody RegisterRequest request
    ) {
        var result = service.register(request, 1);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Register new system admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registered",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Email or username already exist",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Unauthorized",
                    content = @Content) })
    @PostMapping(value = "/registerSystemAdmin", consumes = "application/json")
    @PreAuthorize("hasRole('SYSTEM_ADMIN')")
    public ResponseEntity<AuthenticationResponse> registerSystemAdmin(
            @RequestBody RegisterRequest request
    ) {
        var result = service.register(request, 2);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Authenticate user by username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged in",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Username or password incorrect",
                    content = @Content) })
    @PostMapping(value = "/authenticate", consumes = "application/json")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        var result = service.authenticate(request);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
