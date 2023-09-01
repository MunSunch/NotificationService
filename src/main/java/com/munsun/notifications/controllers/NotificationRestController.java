package com.munsun.notifications.controllers;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.out.NotificationDtoOut;
import com.munsun.notifications.service.NotificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationRestController {
    private NotificationService service;

    @PostMapping("/save")
    public NotificationDtoOut save(@RequestBody NotificationDtoIn notificationDtoIn) {
        return service.add(notificationDtoIn);
    }

    @PostMapping("/find")
    public List<NotificationDtoOut> find(@RequestBody @Valid FindDtoIn findDto) {
        return service.find(findDto);
    }

    @GetMapping("/get/all")
    public List<NotificationDtoOut> getAll() {
        return service.getNotifications();
    }
}
