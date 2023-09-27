package com.munsun.notifications.service;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.exceptions.NotificationEmployeeException;

public interface NotificationCheckerProvider {
    void checkNotification(NotificationDtoIn dto) throws Exception;
    void checkFindDto(FindDtoIn findDto) throws Exception;
}
