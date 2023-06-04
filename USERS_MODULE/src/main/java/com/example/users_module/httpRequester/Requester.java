package com.example.users_module.httpRequester;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class Requester {

    private RestTemplate restTemplate;
    URI uri = null;
    private final String URI_CURRENT_USER = "http://localhost:8081/api/v1/auth/current";
    private MultiValueMap<String, String> headers;
    private RequestEntity request;
    private ResponseEntity response;

    public Requester(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity getUserMailByToken(String token) {
        try{
            uri = new URI(URI_CURRENT_USER);
            headers = new HttpHeaders();
            headers.add("Authorization", token);
            headers.add( "Content-type", "application/json; charset=UTF-8");

            request = new RequestEntity(headers, HttpMethod.GET, uri);
            response = restTemplate.exchange(request, String.class);
            return response;
        }catch (URISyntaxException e) {
            response = new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
            return response;
        }catch (ResourceAccessException e) {
            response = new ResponseEntity(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
            return response;
        }catch (Exception e){
            response = new ResponseEntity(e.getMessage(), HttpStatus.FORBIDDEN);
            return response;
        }
    }
}
