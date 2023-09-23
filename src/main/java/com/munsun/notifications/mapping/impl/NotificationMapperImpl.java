package com.munsun.notifications.mapping.impl;

import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.out.*;
import com.munsun.notifications.mapping.EmployeeMapper;
import com.munsun.notifications.mapping.NotificationMapper;
import com.munsun.notifications.model.*;
import com.munsun.notifications.model.embeddable.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Component
public class NotificationMapperImpl implements NotificationMapper {
    private EmployeeMapper employeeMapper;

    public Notification map(NotificationDtoIn dto) {
        log.info("mapping NotificationDtoIn in Notification");
        var notification = new Notification();
            notification.setStation(new StationEmbeddable(dto.stationCode(), dto.pathOnStation()));
            notification.setTimes(new TimeEmbeddable(dto.timeLocomotiveTrailer(), dto.timeChargingBrakeNetwork(),
                    dto.timeIntegrityCheck(), dto.dateTimeFinish()));
            notification.setLocomotive(new LocomotiveEmbeddable(dto.locomotive().series(), dto.locomotive().number()));
            notification.setTrain(new TrainEmdeddable(
                    dto.train().numberTrain(),
                    dto.train().type(),
                    dto.train().weight(),
                    dto.train().axes(),
                    dto.train().units(),
                    dto.train().tailWagonNumber(),
                    dto.train().onComingWagonNumber()
            ));
            var parameters = new ParametersEmbeddable();
                parameters.setPressRequired(dto.parameters().pressRequired());
                parameters.setPressActual(dto.parameters().pressActual());
                parameters.setUnionMinPress(dto.parameters().minPress());
                parameters.setHandBrakesRequired(dto.parameters().handBrakesRequired());
                parameters.setHandBrakesActual(dto.parameters().handBrakesActual());
                parameters.setDensityBrakeNetworkSecond(dto.parameters().brakeNetworkWithSecond());
                parameters.setDensityBrakeNetworkFourth(dto.parameters().brakeNetworkWithFourth());
                parameters.setDensityBrakeNetworkValue(dto.parameters().brakeNetworkValue());
            notification.setParameters(parameters);
            notification.setEmployees(new EmployeesEmbeddable(
                    employeeMapper.map(dto.tailEmployee()),
                    employeeMapper.map(dto.headEmployee()),
                    employeeMapper.map(dto.machinist())
            ));
        return notification;
    }

    public NotificationDtoOut map(Notification notification) {
        log.info("mapping Notification in NotificationDtoOut");
        return new NotificationDtoOut(
                notification.getId(),
                notification.getStation().getCodeStation(),
                notification.getStation().getPath(),
                new LocomotiveDtoOut(
                        notification.getLocomotive().getLocomotiveSeries(),
                        notification.getLocomotive().getLocomotiveNumber()
                ),
                new TrainDtoOut(
                        notification.getTrain().getTrainNumber(),
                        notification.getTrain().getTrainType(),
                        notification.getTrain().getTrainWeight(),
                        notification.getTrain().getTrainAxes(),
                        notification.getTrain().getTrainUnits(),
                        notification.getTrain().getTrainNumberTail(),
                        notification.getTrain().getTrainNumberOncoming()
                ),
                new ParametersDtoOut(
                        notification.getParameters().getPressRequired(),
                        notification.getParameters().getPressActual(),
                        notification.getParameters().getUnionMinPress(),
                        notification.getParameters().getHandBrakesRequired(),
                        notification.getParameters().getHandBrakesActual(),
                        notification.getParameters().getDensityBrakeNetworkSecond(),
                        notification.getParameters().getDensityBrakeNetworkFourth(),
                        notification.getParameters().getDensityBrakeNetworkValue(), notification.getParameters().getOthersParameters().stream().map(OthersParameters::getName).collect(Collectors.toList())
                ),
                notification.getTimes().getTimeLocomotiveTrailer().toString(),
                notification.getTimes().getTimeChargeNetwork().toString(),
                notification.getTimes().getTimeCheckIntegrity().toString(),
                notification.getTimes().getDatetimeFinish().toString(),
                notification.getTimeCreate().toString(),

                employeeMapper.map(notification.getEmployees().getHeadEmployee()),
                employeeMapper.map(notification.getEmployees().getTailEmployee()),
                employeeMapper.map(notification.getEmployees().getMachinist())
        );
    }
}