package ru.personal_coach.auth_module.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResponseException extends ResponseStatusException {
    public ResponseException(HttpStatus status, String reason) {
        super(status, reason);
    }
}
