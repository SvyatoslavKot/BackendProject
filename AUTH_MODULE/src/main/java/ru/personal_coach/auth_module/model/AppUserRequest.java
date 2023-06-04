package ru.personal_coach.auth_module.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties
public class AppUserRequest {
    private String mail;
    private String password;
    private Status status;
    private Role role;
}
