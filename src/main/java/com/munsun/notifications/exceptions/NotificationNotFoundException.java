package com.munsun.notifications.exceptions;

public class NotificationNotFoundException extends NotificationException {
    public NotificationNotFoundException(Integer id) {
        super("Справка с id="+id+" не найдена");
    }
}
