package com.munsun.notifications.service.impl;

import com.munsun.notifications.dto.in.EmployeeDtoIn;
import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.exceptions.*;
import com.munsun.notifications.service.NotificationCheckerProvider;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class NotificationCheckerProviderImpl implements NotificationCheckerProvider {
    private static final int MIN_PRESS_CARGO = 28;
    private static final int MAX_PRESS_CARGO = 33;

    @Override
    public void checkNotification(NotificationDtoIn dto) throws Exception{
        checkTimesNotification(dto);
        checkParametersNotification(dto);
        checkExecutors(dto);
    }

    private void checkExecutors(NotificationDtoIn dto) throws NotificationEmployeeException {
        var head = dto.headEmployee();
        var tail = dto.tailEmployee();
        var machinist = dto.machinist();

        if(equalsExecutors(head, machinist) || equalsExecutors(machinist, tail))
            throw new NotificationEmployeeException("хвостовой/головной ОРВ и машинист один и тот же человек");
        if(equalsExecutors(head, tail))
            throw new NotificationEmployeeException("Ошибка ввода исполнителя: хвостовой ОРВ и головной ОРВ один и тот же человек");
    }

    private boolean equalsExecutors(EmployeeDtoIn employee, EmployeeDtoIn anotherEmployee) {
        if(!employee.getName().equals(anotherEmployee.getName()))
            return false;
        if(!employee.getSurname().equals(anotherEmployee.getSurname()))
            return false;
        return employee.getPatronymic().equals(anotherEmployee.getPatronymic());
    }

    private void checkParametersNotification(NotificationDtoIn dto) throws NotificationParametersException {
        var pressRequired = dto.parameters().pressRequired();
        var pressActual = dto.parameters().pressActual();
        if(pressRequired > pressActual)
            throw new NotificationParametersException(String.format("%d > %d! Не обеспечивается наличие тормозной силы", pressRequired, pressActual));

        var handBrakesRequired = dto.parameters().handBrakesRequired();
        var handBrakesActual = dto.parameters().handBrakesActual();
        if(handBrakesRequired > handBrakesActual)
            throw new NotificationParametersException(String.format("%d > %d! Не обеспечивается наличие тормозной силы для закрепления состава", handBrakesRequired, handBrakesActual));

        var minPress = dto.parameters().minPress();
        if(minPress < MIN_PRESS_CARGO || minPress > MAX_PRESS_CARGO)
            throw new NotificationParametersException(String.format("%d<%d or %d>%d! Неверный коэффициент наименьшего тормозного нажатия", minPress, MIN_PRESS_CARGO, minPress, MAX_PRESS_CARGO));

        var brakeNetworkWithSecond = dto.parameters().brakeNetworkWithSecond();
        var brakeNetworkWithFourth = dto.parameters().brakeNetworkWithFourth();
        if(brakeNetworkWithSecond*0.9 > brakeNetworkWithFourth)
            throw new NotificationParametersException((String.format("%d*0.9 > %d! Плотность в 4 положении отличается от плотности во 2 положении в сторону уменьшения",
                    brakeNetworkWithSecond, brakeNetworkWithFourth)));
    }

    private void checkTimesNotification(NotificationDtoIn dto) throws NotificationDateTimeException {
        var timeTrailer = dto.timeLocomotiveTrailer();
        var timeCharging = dto.timeChargingBrakeNetwork();
        var timeIntegrity = dto.timeIntegrityCheck();
        var timeFinish = dto.dateTimeFinish().toLocalTime();

        var list = List.of(timeTrailer, timeCharging, timeIntegrity, timeFinish);
        for (int i = 0; i < list.size(); i++) {
            for (int j = i+1; j < list.size(); j++) {
                if(list.get(i).isAfter(list.get(j)))
                    throw new NotificationDateTimeException(String.format("Ошибка следования временных дат: %s должно следовать перед %s!", list.get(j), list.get(i)));
            }
        }

        var currentDate = LocalDate.now();
        var dateFinish = dto.dateTimeFinish().toLocalDate();
        if(!dateFinish.equals(currentDate))
            throw new NotificationDateTimeException(String.format("Ошибка следования даты: %d != %d! Справка может быть добавлена только в день её готовности", currentDate, dateFinish));
    }

    @Override
    public void checkFindDto(FindDtoIn findDto) throws Exception {
        if(!ObjectUtils.isEmpty(findDto.getWagonTailNumber()) && !ObjectUtils.isEmpty(findDto.getWagonOncomingNumber()))
            checkNumbersWagonFindDto(findDto);
        if(!ObjectUtils.isEmpty(findDto.getStartPeriodDate()) && !ObjectUtils.isEmpty(findDto.getEndPeriodDate()))
            checkDateFindDto(findDto);
    }

    private void checkDateFindDto(FindDtoIn findDto) throws FindNotificationDateException {
        if(findDto.getStartPeriodDate().isAfter(findDto.getEndPeriodDate()))
            throw new FindNotificationDateException(String.format("Дата начала периода следует после даты конца периода! %s следует после %s",
                    findDto.getStartPeriodDate(), findDto.getEndPeriodDate()));
    }

    private void checkNumbersWagonFindDto(FindDtoIn findDto) throws FindNotificationNumbersWagonException {
        if(findDto.getWagonOncomingNumber().equals(findDto.getWagonTailNumber()))
            throw new FindNotificationNumbersWagonException(String.format("Хвостовой вагон и встречный одинаковы! %s == %s",
                    findDto.getWagonOncomingNumber(), findDto.getWagonTailNumber()));
    }
}
