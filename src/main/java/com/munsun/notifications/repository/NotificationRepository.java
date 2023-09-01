package com.munsun.notifications.repository;

import com.munsun.notifications.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    Optional<Notification> findNotificationBySurnameHeadEmployee(String surname);
    Optional<Notification> findNotificationBySurnameTailEmployee(String surname);
    Optional<Notification> findNotificationBySurnameMachinist(String surname);
    Optional<Notification> findNotificationByTrainNumber(Integer number);
}