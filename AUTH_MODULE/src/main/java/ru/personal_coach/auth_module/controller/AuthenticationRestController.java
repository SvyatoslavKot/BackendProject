package ru.personal_coach.auth_module.controller;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.personal_coach.auth_module.exception.ResponseException;
import ru.personal_coach.auth_module.model.AppUser;
import ru.personal_coach.auth_module.model.AppUserRequest;
import ru.personal_coach.auth_module.model.AppUserResponse;
import ru.personal_coach.auth_module.model.Role;
import ru.personal_coach.auth_module.model.dto.AuthenticationRequestDTO;
import ru.personal_coach.auth_module.repositories.UserRepository;
import ru.personal_coach.auth_module.security.JwtAuthenticationException;
import ru.personal_coach.auth_module.security.JwtTokenProvider;
import ru.personal_coach.auth_module.service.AppUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@CrossOrigin
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppUserService appUserService;

    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> athenticate(@RequestBody AuthenticationRequestDTO request){
        System.out.println(request);

        try{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getMail(), request.getPassword()));
        AppUser user = appUserService.findByMail(request.getMail());


        String token = jwtTokenProvider.createToken(request.getMail(), user.getRole().name());
        System.out.println("prov:" + jwtTokenProvider.getUsername(token));


        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", token);
        ResponseEntity response = new ResponseEntity(headers, HttpStatus.OK);
        return response;

        }catch (
        AuthenticationException e){
            //clearAuthToken(response);
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("Content-type", "application/json; charset=UTF-8");
            ResponseEntity response = new ResponseEntity("Invalid email/password combination",headers,HttpStatus.FORBIDDEN);
            System.out.println(response);
            return response;
        }catch (ResponseException e) {
            return new ResponseEntity<>(e.getMessage(),e.getStatus());
        }

        }

    @Transactional
    @PostMapping(value = "/registration", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> registration(@RequestBody AppUserRequest request, HttpServletResponse response){
        try{
            AppUser user = appUserService.createUser(request);

            String token = jwtTokenProvider.createToken(user.getMail(), user.getRole().name());
            Map<Object,Object> map = new HashMap<>();
            map.put("token", token);

            ResponseEntity res = new ResponseEntity( map, HttpStatus.OK);

            return res;

        }catch (AuthenticationException e){
            clearAuthToken(response);
            return new ResponseEntity<>("Something went wrong " + e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/current", produces = "application/json", consumes = "application/json")
    public ResponseEntity currentUserFromToken(@RequestHeader("Authorization") String token){
        System.out.println("Current " + token);
        try{
            if (jwtTokenProvider.validateToken(token)){

                String appUserMail = jwtTokenProvider.getUsername(token);
                MultiValueMap<String, String> headers = new HttpHeaders();
                headers.add("Authorisation", token);
                ResponseEntity response = new ResponseEntity(appUserMail, headers, HttpStatus.OK);
                return response;

            }else {
                MultiValueMap<String, String> headers = new HttpHeaders();
                headers.add("Authorisation", "");
                ResponseEntity response = new ResponseEntity( headers, HttpStatus.FORBIDDEN);
                return response;
            }
        }catch (JwtAuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }

    }

    @GetMapping(value = "/validation", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token){
        System.out.println("validation => " + token);
        try{
            if (jwtTokenProvider.validateToken(token)){

                MultiValueMap<String, String> headers = new HttpHeaders();
                headers.add("Authorisation", token);
                ResponseEntity response = new ResponseEntity(headers, HttpStatus.OK);
                return response;

            }else {
                MultiValueMap<String, String> headers = new HttpHeaders();
                headers.add("Authorisation", "");
                ResponseEntity response = new ResponseEntity(headers, HttpStatus.UNAUTHORIZED);
                return response;
            }
        }catch (JwtAuthenticationException e) {
            return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
        }

    }


    private ResponseEntity<?> buildUserResponse(AppUser user){
        return ResponseEntity.ok(new AppUserResponse(user));
    }

    private void clearAuthToken( HttpServletResponse response){
        Cookie authCookie = new Cookie("AUTH-TOKEN", "-");
        authCookie.setPath("/");
        response.addCookie(authCookie);
    }
}
