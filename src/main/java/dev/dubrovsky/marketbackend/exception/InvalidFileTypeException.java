package dev.dubrovsky.marketbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid file type")
public class InvalidFileTypeException extends RuntimeException {

    public InvalidFileTypeException() {
    }

    public InvalidFileTypeException(String message) {
        super(message);
    }

}
