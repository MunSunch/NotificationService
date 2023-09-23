package com.munsun.notifications.service;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;

public interface NotificationCheckerProvider {
    void checkNotification(NotificationDtoIn dto);
    void checkFindDto(FindDtoIn findDto);
}
