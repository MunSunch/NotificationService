package com.munsun.notifications.service.impl;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.out.NotificationDtoOut;
import com.munsun.notifications.exceptions.NotificationEqualsFieldException;
import com.munsun.notifications.exceptions.NotificationAfterDateException;
import com.munsun.notifications.exceptions.NotificationParametersException;
import com.munsun.notifications.mapping.NotificationMapper;
import com.munsun.notifications.model.Notification;
import com.munsun.notifications.repository.NotificationRepository;
import com.munsun.notifications.service.NotificationService;
import com.munsun.notifications.service.impl.spec.NotificationSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepository repository;
    private NotificationMapper mapper;

    @Transactional
    @Override
    public NotificationDtoOut add(NotificationDtoIn notificationDtoIn) {
        log.info("save new notification...");
        checkNotification(notificationDtoIn);
        Notification newNotification = mapper.map(notificationDtoIn);
        newNotification.setTimeCreate(LocalDateTime.now());
        repository.save(newNotification);
        return mapper.map(newNotification);
    }

    private void checkNotification(NotificationDtoIn dtoIn) {
        log.info("check notificationDtoIn");
        if(dtoIn.parameters().pressRequired() > dtoIn.parameters().pressActual()) {
            log.error("check failed: required press={}>actual press={}", dtoIn.parameters().pressRequired(), dtoIn.parameters().pressActual());
            throw new NotificationParametersException(String.format("Required press(%d)>actual press(%d)!",
                    dtoIn.parameters().pressRequired(), dtoIn.parameters().pressActual()));
        }
        if(dtoIn.parameters().handBrakesRequired() > dtoIn.parameters().handBrakesActual()) {
            log.error("check failed: required hand brakes={}>actual hand brakes={}", dtoIn.parameters().handBrakesRequired(), dtoIn.parameters().handBrakesActual());
            throw new NotificationParametersException(String.format("Required hand brakes(%d)>actual hand brakes press(%d)!",
                    dtoIn.parameters().handBrakesRequired(), dtoIn.parameters().handBrakesActual()));
        }
        var times = List.of(dtoIn.timeLocomotiveTrailer(), dtoIn.timeChargingBrakeNetwork(),
                dtoIn.timeIntegrityCheck(), dtoIn.timeFinish());
        for (int i = 0; i < times.size(); i++) {
            for (int j = i+1; j < times.size(); j++) {
                if(times.get(i).isAfter(times.get(j))) {
                    log.error("check failed: time={} is after time={}", times.get(i), times.get(j));
                    throw new NotificationAfterDateException(String.format("Invalid time! time %s is after %s!", times.get(i), times.get(j)));
                }
            }
        }
        checkSurnamesNotification(dtoIn.surnameHeadEmployee(), dtoIn.surnameTailEmployee(), dtoIn.surnameMachinist());
    }

    @Override
    public List<NotificationDtoOut> find(FindDtoIn findDto) {
        log.info("find notifications by findDto");
        checkFindDtoIn(findDto);
        return repository.findAll(NotificationSpecification.getSpecEquals(findDto))
                .stream()
                .map(x -> mapper.map(x))
                .collect(Collectors.toList());
    }

    private void checkFindDtoIn(FindDtoIn dtoIn) {
        log.info("check findDtoIn...");
        var surnameTail = dtoIn.getSurnameTailEmployee();
        var surnameHead = dtoIn.getSurnameHeadEmployee();
        var surnameMachinist = dtoIn.getSurnameMachinist();
        var tailWagon = dtoIn.getWagonTailNumber();
        var oncomingWagon = dtoIn.getWagonOncomingNumber();
        var startDate = dtoIn.getStartPeriodDate();
        var endDate = dtoIn.getEndPeriodDate();
        checkSurnamesNotification(surnameHead, surnameTail, surnameMachinist);
        if(!ObjectUtils.isEmpty(tailWagon) && !ObjectUtils.isEmpty(oncomingWagon) && tailWagon.equals(oncomingWagon)) {
            log.error("check failed: tail wagon number={} is equals on coming wagon number={}", tailWagon, oncomingWagon);
            throw new NotificationEqualsFieldException("Tail wagon's number wagon is equals on coming wagon's number!");
        }
        if(!ObjectUtils.isEmpty(startDate) && !ObjectUtils.isEmpty(endDate) && startDate.isAfter(endDate)) {
            log.error("check failed: start date={} after end date={}", startDate, endDate);
            throw new NotificationAfterDateException("Start date after end date!");
        }
    }

    private void checkSurnamesNotification(String surnameHead, String surnameTail,String surnameMachinist) {
        if(!ObjectUtils.isEmpty(surnameHead) && !ObjectUtils.isEmpty(surnameMachinist) && surnameHead.equals(surnameMachinist)) {
            log.error("check failed: Surname head={} is equals surname machinist={}", surnameHead, surnameMachinist);
            throw new NotificationEqualsFieldException("Head employee is equals Machinist!");
        }
        if(!ObjectUtils.isEmpty(surnameTail) && !ObjectUtils.isEmpty(surnameMachinist) && surnameTail.equals(surnameMachinist)) {
            log.error("check failed: Surname tail={} is equals surname machinist={}", surnameTail, surnameMachinist);
            throw new NotificationEqualsFieldException("Tail employee is equals Machinist!");
        }
        if(!ObjectUtils.isEmpty(surnameHead) && !ObjectUtils.isEmpty(surnameTail) && surnameHead.equals(surnameTail)) {
            log.error("check failed: Surname head={} is equals surname tail={}", surnameHead, surnameTail);
            throw new NotificationEqualsFieldException("Head employee is equals tail employee!");
        }
    }

    @Override
    public List<NotificationDtoOut> getNotifications() {
        log.info("get all notifications");
        var list =  repository.findAll().stream()
                .map(x -> mapper.map(x))
                .collect(Collectors.toList());
        return list;
    }

    @Transactional
    @Override
    public NotificationDtoOut getById(Long id) {
        log.info("get by id={}", id);
        return mapper.map(repository.getReferenceById(id));
    }
}
