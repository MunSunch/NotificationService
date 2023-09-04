package com.munsun.notifications.service;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.out.NotificationDtoOut;

import java.util.List;

public interface NotificationService {
    NotificationDtoOut add(NotificationDtoIn notificationDtoIn);
    List<NotificationDtoOut> find(FindDtoIn findDto);
    List<NotificationDtoOut> getNotifications();
    NotificationDtoOut getById(Long id);
}
