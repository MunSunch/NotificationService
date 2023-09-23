package com.munsun.notifications.mapping;

import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.out.NotificationDtoOut;
import com.munsun.notifications.model.Notification;

public interface NotificationMapper {
    Notification map(NotificationDtoIn dto);
    NotificationDtoOut map(Notification notification);
}
