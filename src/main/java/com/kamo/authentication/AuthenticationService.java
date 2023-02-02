package com.kamo.authentication;

import javax.security.sasl.AuthenticationException;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kamo.kamouser.KamoUser;
import com.kamo.kamouser.KamoUserRepository;
import com.kamo.kamouser.KamoUserRole;
import com.kamo.security.config.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final KamoUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService; 
    private final AuthenticationManager authenticationManager;
    
    public AuthenticationResponse register(RegisterRequest request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            return AuthenticationResponse.builder().token("ERROR").build();
        } else {
            var user = KamoUser.builder() 
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .kamoUserRole(KamoUserRole.USER)
            .build();
            repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
            .token(jwtToken)
            .username(request.getUsername())
            .email(request.getEmail())
            .id(user.getId())
            .build();
        }
    };

    public AuthenticationResponse authenticateWithEmail(AuthenticationRequest request) throws AuthenticationException {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), 
                request.getPassword())
        );
        KamoUser user = repository.findByEmail(request.getUsername())
            .orElseThrow( () -> new AuthenticationException() );
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .username(user.getUsername())
            .email(user.getEmail())
            .id(user.getId())
            .build();
    };
    
    public AuthenticationResponse authenticateWithUsername(AuthenticationRequest request) throws AuthenticationException {
        System.out.println("info here" + request.getUsername() + request.getPassword());
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), 
                request.getPassword())
        );
        KamoUser user = repository.findByUsername(request.getUsername())
            .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .token(jwtToken)
            .username(user.getUsername())
            .email(user.getEmail())
            .id(user.getId())
            .build();
    };
    
};
