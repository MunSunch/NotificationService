package com.munsun.notifications.dto.out;

public record NotificationDtoOut(
        String station,
        Long stationCode,
        Long pathOnStation,
        LocomotiveDtoOut locomotive,
        TrainDtoOut train,
        ParametersDtoOut parameters,
        String timeLocomotiveTrailer,
        String timeChargingBrakeNetwork,
        String timeIntegrityCheck,
        String timeFinish,
        String timeCreate
) {}