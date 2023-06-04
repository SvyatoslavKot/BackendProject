package ru.personal_coach.auth_module.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class AuthenticationRequestDTO {

    private String mail;
    private String password;
}
