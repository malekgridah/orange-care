package com.billcom.authentication.domains;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private Boolean isSuccessful;
    private String comment;
    private String token;
}
