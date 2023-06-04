package ru.personal_coach.auth_module.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "app_user_client")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mail")
    private String mail;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public AppUser(AppUserRequest userRequest) {
        this.mail = userRequest.getMail();
        this.password = userRequest.getPassword();
        this.status = userRequest.getStatus();
        this.role = userRequest.getRole();
    }
}
