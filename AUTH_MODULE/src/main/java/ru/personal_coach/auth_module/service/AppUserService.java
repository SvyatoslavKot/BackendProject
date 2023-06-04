package ru.personal_coach.auth_module.service;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.personal_coach.auth_module.exception.ResponseException;
import ru.personal_coach.auth_module.model.AppUser;
import ru.personal_coach.auth_module.model.AppUserRequest;
import ru.personal_coach.auth_module.model.Role;
import ru.personal_coach.auth_module.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AppUserService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser createUser(AppUserRequest request) {
        if (!userRepository.findByMail(request.getMail()).isPresent()){

            AppUser user = new AppUser(request);
            user.setRole(Role.USER);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);

            return user;
        }
        throw new ResponseException(HttpStatus.FORBIDDEN, "This email already exist!");
    }

    @Override
    public AppUser findByMail(String mail) throws ResponseException {
        return userRepository.findByMail(mail).orElseThrow(() -> new ResponseException(HttpStatus.FORBIDDEN, "This email doesn't exist!"));
    }

    @Override
    public void updateUser(AppUserRequest dto) {
        AppUser user = userRepository.findByMail(dto.getMail()).orElseThrow(() -> new RuntimeException("User not found"));
        if (user != null){
            System.out.println("dto -> " + user);
            userRepository.save(user);
        }
    }
}
