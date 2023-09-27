package com.munsun.notifications.controllers;

import com.munsun.notifications.dto.out.ErrorWriteRequest;
import com.munsun.notifications.exceptions.NotificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerRestControllerAdvice {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorWriteRequest> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorWriteRequest(message));
    }

    @ExceptionHandler(value = NotificationException.class)
    public ResponseEntity<ErrorWriteRequest> notificationExceptionHandler(NotificationException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorWriteRequest(e.getMessage()));
    }
}
