package com.munsun.notifications.controllers;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.out.NotificationDtoOut;
import com.munsun.notifications.service.NotificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/notifications")
public class NotificationRestController {
    private NotificationService service;

    @PostMapping("/save")
    public NotificationDtoOut save(@RequestBody NotificationDtoIn notificationDtoIn) {
        log.info("POST /notifications/save");
        return service.add(notificationDtoIn);
    }

    @PostMapping("/find")
    public List<NotificationDtoOut> find(@RequestBody @Valid FindDtoIn findDto) {
        log.info("GET /notifications/find");
        return service.find(findDto);
    }

    @GetMapping("/get/all")
    public List<NotificationDtoOut> getAll() {
        log.info("GET /notifications/get/all");
        return service.getNotifications();
    }
}
