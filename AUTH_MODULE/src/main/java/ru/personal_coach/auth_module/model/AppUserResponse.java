package ru.personal_coach.auth_module.model;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AppUserResponse {

    private String mail;
    private String firstName;
    private String lastName;
    private String status;

    public AppUserResponse(AppUser user) {
        this.mail = user.getMail();
    }
}
