package com.munsun.notifications.controllers;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.out.NotificationDtoOut;
import com.munsun.notifications.service.NotificationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/notifications")
public class NotificationRestController {
    private NotificationService service;

    @PostMapping("/save")
    public NotificationDtoOut save(@RequestBody @Valid NotificationDtoIn notificationDtoIn) throws Exception {
        return service.add(notificationDtoIn);
    }

    @PostMapping("/find")
    public List<NotificationDtoOut> find(@RequestBody @Valid FindDtoIn findDto,
                                         @RequestParam Integer page,
                                         @RequestParam Integer size) throws Exception {
        return service.find(findDto, PageRequest.of(page, size));
    }

    @GetMapping("/all")
    public List<NotificationDtoOut> getAll(@RequestParam Integer page,
                                           @RequestParam Integer size)
    {
        return service.getNotifications(page, size);
    }
}
