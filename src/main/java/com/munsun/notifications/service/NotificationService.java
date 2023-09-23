package com.munsun.notifications.service;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.out.NotificationDtoOut;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    NotificationDtoOut add(NotificationDtoIn notificationDtoIn);
    List<NotificationDtoOut> find(FindDtoIn findDto, Pageable pageable);
    List<NotificationDtoOut> getNotifications(Integer page, Integer size);
    NotificationDtoOut getById(Integer id);
}
