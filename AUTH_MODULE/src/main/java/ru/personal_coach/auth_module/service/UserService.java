package ru.personal_coach.auth_module.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.personal_coach.auth_module.model.AppUser;
import ru.personal_coach.auth_module.model.AppUserRequest;

public interface UserService {

    AppUser createUser(AppUserRequest request);
    AppUser findByMail(String mail) throws UsernameNotFoundException;
    void updateUser(AppUserRequest dto);
}
