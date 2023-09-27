package com.munsun.notifications.service;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.out.NotificationDtoOut;
import com.munsun.notifications.exceptions.NotificationEmployeeException;
import com.munsun.notifications.exceptions.NotificationNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    NotificationDtoOut add(NotificationDtoIn notificationDtoIn) throws Exception;
    List<NotificationDtoOut> find(FindDtoIn findDto, Pageable pageable) throws Exception;
    List<NotificationDtoOut> getNotifications(Integer page, Integer size);
    NotificationDtoOut getById(Integer id) throws NotificationNotFoundException;
}
