package com.munsun.notifications.dto.out;

public record NotificationDtoOut(
        Integer id,
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
        String timeCreate,

        String surnameTailEmployee,
        String surnameHeadEmployee,
        String surnameMachinist
) {}