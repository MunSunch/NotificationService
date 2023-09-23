package com.munsun.notifications.dto.out;

public record NotificationDtoOut(
        Integer id,
        Integer stationCode,
        Integer pathOnStation,
        LocomotiveDtoOut locomotive,
        TrainDtoOut train,
        ParametersDtoOut parameters,

        String timeLocomotiveTrailer,
        String timeChargingBrakeNetwork,
        String timeIntegrityCheck,
        String timeFinish,
        String timeCreate,

        EmployeeDtoOut tailEmployee,
        EmployeeDtoOut headEmployee,
        EmployeeDtoOut machinist
) {}