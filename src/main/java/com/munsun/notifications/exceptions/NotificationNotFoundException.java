package com.munsun.notifications.exceptions;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(Integer id) {
        super("Notification not found; id="+id);
    }
}
