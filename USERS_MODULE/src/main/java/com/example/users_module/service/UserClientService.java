package com.example.users_module.service;

import com.example.users_module.httpRequester.ServiceException;
import com.example.users_module.model.AppUserClient;
import com.example.users_module.model.Role;
import com.example.users_module.model.Status;
import com.example.users_module.repository.UserClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserClientService {
    private final UserClientRepository userClientRepository;


    public AppUserClient addUser(){
        AppUserClient user = new AppUserClient(1l, 22, "male","mail", "password", "name", "lastName", Role.USER, Status.ACTIVE );
        userClientRepository.save(user);
        return user;
    }

    public AppUserClient findeByMail(String mail) throws ServiceException {
        AppUserClient user = userClientRepository.findAppUserClientByMail(mail).orElseThrow(() -> new ServiceException("User not Found!"));
        return user;
    }
}
