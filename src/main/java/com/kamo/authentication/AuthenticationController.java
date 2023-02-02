package com.kamo.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping(path="api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service; 

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    };
    
    @PostMapping("/authenticate/email")
    public ResponseEntity<?> authenticateWithEmail(@RequestBody AuthenticationRequest request) throws javax.security.sasl.AuthenticationException {
        try {
            return ResponseEntity.ok(service.authenticateWithEmail(request));
        } catch (AuthenticationException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage()); 
        }
    };
    @PostMapping("/authenticate/username")
    public ResponseEntity<?> authenticateWithUsername(@RequestBody AuthenticationRequest request) throws AuthenticationException, javax.security.sasl.AuthenticationException {
        try {
            return ResponseEntity.ok(service.authenticateWithUsername(request));
        } catch (AuthenticationException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage()); 
        }
    };

};
