package com.munsun.notifications.service.impl;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.out.NotificationDtoOut;
import com.munsun.notifications.mapping.NotificationMapper;
import com.munsun.notifications.model.Notification;
import com.munsun.notifications.repository.NotificationRepository;
import com.munsun.notifications.service.NotificationService;
import com.munsun.notifications.service.impl.spec.NotificationSpecification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository repository;
    private NotificationMapper mapper;

    @Override
    public NotificationDtoOut add(NotificationDtoIn notificationDtoIn) {
        Notification newNotification = mapper.map(notificationDtoIn);
        repository.save(newNotification);
        return mapper.map(newNotification);
    }

    @Override
    public List<NotificationDtoOut> find(FindDtoIn findDto) {
        return repository.findAll(NotificationSpecification.getSpecEquals(findDto))
                .stream()
                .map(x -> mapper.map(x))
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDtoOut> getNotifications() {
        return repository.findAll().stream()
                .map(x -> mapper.map(x))
                .collect(Collectors.toList());
    }
}
