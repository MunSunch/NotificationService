package com.munsun.notifications.mapping;

import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.out.LocomotiveDtoOut;
import com.munsun.notifications.dto.out.NotificationDtoOut;
import com.munsun.notifications.dto.out.ParametersDtoOut;
import com.munsun.notifications.dto.out.TrainDtoOut;
import com.munsun.notifications.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.stream.Collectors;

@Component
public class NotificationMapper {
    public Notification map(NotificationDtoIn dtoIn) {
        var notification = new Notification();
            notification.setTrainNumber(dtoIn.numberTrain());
            notification.setLocomotiveNumber(dtoIn.locomotive().number().intValue());
            notification.setLocomotiveSeries(dtoIn.locomotive().series());

            var stationPath = new StationPath();
                var station = new Station();
                    station.setName(dtoIn.station());
                stationPath.setStation(station);
                var stationCode = new StationCode();
                    stationCode.setNumber(dtoIn.stationCode().toString());
                stationPath.setStationCode(stationCode);
                stationPath.setPath(dtoIn.pathOnStation());
            notification.setStationPath(stationPath);

            notification.setSurnameMachinist(dtoIn.surnameMachinist());
            notification.setSurnameTailEmployee(dtoIn.surnameTailEmployee());
            notification.setSurnameHeadEmployee(dtoIn.surnameHeadEmployee());

            var clock = dtoIn.timeLocomotiveTrailer().split(":");
            notification.setTimeLocomotiveTrailer(LocalTime.of(Integer.parseInt(clock[0]), Integer.parseInt(clock[1])));
            clock = dtoIn.timeChargingBrakeNetwork().split(":");
            notification.setTimeChargeNetwork(LocalTime.of(Integer.parseInt(clock[0]), Integer.parseInt(clock[1])));
            clock = dtoIn.timeIntegrityCheck().split(":");
            notification.setTimeCheckIntegrity(LocalTime.of(Integer.parseInt(clock[0]), Integer.parseInt(clock[1])));
            clock = dtoIn.timeFinish().split(":");
            notification.setTimeFinish(LocalTime.of(Integer.parseInt(clock[0]), Integer.parseInt(clock[1])));

            notification.setTrainNumber(dtoIn.numberTrain());
            notification.setTrainNumberTail(dtoIn.train().tailWagonNumber());
            notification.setTrainNumberOncoming(dtoIn.train().onComingWagonNumber());
            notification.setTrainWeight(dtoIn.train().weight().intValue());
            notification.setTrainAxes(dtoIn.train().axes().intValue());
            notification.setTrainUnits(dtoIn.train().units().intValue());
            notification.setTrainType(dtoIn.train().type());

            notification.setPressRequired(dtoIn.parameters().pressRequired());
            notification.setPressActual(dtoIn.parameters().pressActual());
            notification.setUnionMinPress(dtoIn.parameters().minPress());
            notification.setHandBrakesRequired(dtoIn.parameters().handBrakesRequired());
            notification.setHandBrakesActual(dtoIn.parameters().handBrakesActual());
            notification.setDensityBrakeNetworkSecond(dtoIn.parameters().brakeNetworkWithSecond());
            notification.setDensityBrakeNetworkFourth(dtoIn.parameters().brakeNetworkWithFourth());
            notification.setDensityBrakeNetworkValue(dtoIn.parameters().brakeNetworkValue());
            notification.setOthersParameters(dtoIn.parameters().other().stream()
                    .map(x -> {
                        var temp = new OthersParameters();
                            temp.setName(x);
                        return temp;
                    }).collect(Collectors.toList()));
        return notification;
    }

    public NotificationDtoOut map(Notification notification) {
        return new NotificationDtoOut(
                notification.getStationPath().getStation().getName(),
                Long.parseLong(notification.getStationPath().getStationCode().getNumber()),
                notification.getStationPath().getPath().longValue(),
                new LocomotiveDtoOut(
                        notification.getLocomotiveSeries(),
                        notification.getLocomotiveNumber()
                ),
                new TrainDtoOut(
                        notification.getTrainType(),
                        notification.getTrainWeight().longValue(),
                        notification.getTrainAxes().longValue(),
                        notification.getTrainUnits().longValue(),
                        notification.getTrainNumberTail(),
                        notification.getTrainNumberOncoming()
                ),
                new ParametersDtoOut(
                        notification.getPressRequired(),
                        notification.getPressActual(),
                        notification.getUnionMinPress(),
                        notification.getHandBrakesRequired(),
                        notification.getHandBrakesActual(),
                        notification.getDensityBrakeNetworkSecond(),
                        notification.getDensityBrakeNetworkFourth(),
                        notification.getDensityBrakeNetworkValue(),
                        notification.getOthersParameters().stream()
                                .map(OthersParameters::getName)
                                .collect(Collectors.toList())
                ),
                notification.getTimeLocomotiveTrailer().toString(),
                notification.getTimeChargeNetwork().toString(),
                notification.getTimeCheckIntegrity().toString(),
                notification.getTimeFinish().toString(),
                notification.getTimeCreate().toString()
        );
    }
}
