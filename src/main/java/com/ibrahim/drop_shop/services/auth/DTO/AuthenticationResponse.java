package com.ibrahim.drop_shop.services.auth.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}
