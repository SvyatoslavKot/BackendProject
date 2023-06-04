package com.example.users_module.model.dto;

import com.example.users_module.model.AppUserClient;
import lombok.*;

import javax.persistence.Column;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserClientDTO {
    private int age;
    private String gender;
    private String mail;
    private String name;
    private String lastName;

    public AppUserClientDTO(AppUserClient appUserClient) {
        this.age = appUserClient.getAge();
        this.gender = appUserClient.getGender();
        this.mail = appUserClient.getMail();
        this.name = appUserClient.getName();
        this.lastName = appUserClient.getLastName();
    }
}
