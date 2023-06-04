package com.example.users_module.controller;


import com.example.users_module.httpRequester.Requester;
import com.example.users_module.httpRequester.ServiceException;
import com.example.users_module.model.AppUserClient;
import com.example.users_module.model.dto.AppUserClientDTO;
import com.example.users_module.service.UserClientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@CrossOrigin
public class UserController {
    private final UserClientService service;
    private final Requester requester;
    private final UserClientService userClientService;

    @GetMapping(value = "/test")
    public ResponseEntity currentUserFromToken(){
        AppUserClient user = service.addUser();
        ResponseEntity response = new ResponseEntity(user, HttpStatus.OK);
        return response;
    }

    @GetMapping(value = "/user", produces = "application/json", consumes = "application/json")
    public ResponseEntity currentUserFromToken(@RequestHeader("Authorization") String token){
        System.out.println("Current " + token);

        try{
            ResponseEntity responseAuth = requester.getUserMailByToken(token);
            if (responseAuth.getStatusCode().equals(HttpStatus.OK)){
                String mailUser = (String) responseAuth.getBody();
                AppUserClient appUserClient = userClientService.findeByMail(mailUser);
                AppUserClientDTO userDTO = new AppUserClientDTO(appUserClient);

                ResponseEntity response = new ResponseEntity(userDTO, HttpStatus.OK);
                return response;

            }else {
                return responseAuth;
            }
        }catch (ServiceException e) {
            ResponseEntity response = new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
            return response;
        }

    }
}
