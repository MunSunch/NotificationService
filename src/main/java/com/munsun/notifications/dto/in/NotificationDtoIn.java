package com.munsun.notifications.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record NotificationDtoIn(
        Integer stationCode,

        @Positive(message = "Номер пути - это положительное число")
        Integer pathOnStation,

        @Valid
        LocomotiveDtoIn locomotive,

        @Valid
        TrainDtoIn train,

        @Valid
        ParametersDtoIn parameters,

        @JsonFormat(pattern = "HH:mm")
        LocalTime timeLocomotiveTrailer,

        @JsonFormat(pattern = "HH:mm")
        LocalTime timeChargingBrakeNetwork,

        @JsonFormat(pattern = "HH:mm")
        LocalTime timeIntegrityCheck,

        LocalDateTime dateTimeFinish,

        @Valid
        EmployeeDtoIn headEmployee,

        @Valid
        EmployeeDtoIn tailEmployee,

        @Valid
        EmployeeDtoIn machinist
) {}
