package com.munsun.notifications.controllers;

import com.munsun.notifications.dto.out.ErrorWriteRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerRestControllerAdvice {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorWriteRequest> errorWriteRequestHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorWriteRequest(message));
    }
}
