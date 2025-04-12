package com.project.webIT.exceptions;

import com.project.webIT.dtos.response.ObjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) //500-error group
    public ResponseEntity<ObjectResponse<Void>> handGeneralException (Exception exception){
        return ResponseEntity.badRequest().body(
                ObjectResponse.<Void>builder()
                        .message(exception.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .build()
        );
    }

//    @ExceptionHandler(DataNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<ObjectResponse<Void>> handGeneralDataNotFoundException (Exception exception) {
//        return ResponseHelper.badRequest(exception.getMessage());
//    }
//
//    @ExceptionHandler(UsernameNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<ObjectResponse<Void>> handGeneralUsernameNotFoundException (Exception exception) {
//        return ResponseHelper.badRequest(exception.getMessage());
//    }
}
