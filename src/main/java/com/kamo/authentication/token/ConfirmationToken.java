package com.kamo.authentication.token;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {
    
    private Long id;
    private String token; 
    private LocalDateTime createdAt; 
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt; 

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, LocalDateTime confirmedAt) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt; 
        this.confirmedAt = confirmedAt;
    };

};
