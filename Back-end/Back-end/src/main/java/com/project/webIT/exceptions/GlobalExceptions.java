package com.project.webIT.exceptions;

import com.project.webIT.response.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500-error group
    public ResponseEntity<ResponseObject> handGeneralException (Exception exception){
        return ResponseEntity.internalServerError().body(
                ResponseObject.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseObject> handGeneralDataNotFoundException (Exception exception) {
        return ResponseEntity.badRequest().body(
                ResponseObject.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(exception.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseObject> handGeneralUsernameNotFoundException (Exception exception) {
        return ResponseEntity.badRequest().body(
                ResponseObject.builder()
                        .status(HttpStatus.NOT_FOUND)
                        .message(exception.getMessage())
                        .build()
        );
    }
}
