package com.munsun.notifications.dto.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record NotificationDtoIn(
        @Positive(message = "Номер поезда - это положительное число")
        Integer numberTrain,

        @Pattern(regexp = "^\\s+$", message = "Название станции состоит из букв")
        String station,

        @Pattern(regexp = "^\\d{5}$", message = "Код станции состоит из 5 цифр")
        Long stationCode,

        @Positive(message = "Номер пути - это положительное число")
        Integer pathOnStation,

        @Valid
        LocomotiveDtoIn locomotive,

        @Valid
        TrainDtoIn train,

        @Valid
        ParametersDtoIn parameters,

        @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Формат ввода времени: HH:mm")
        String timeLocomotiveTrailer,

        @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Формат ввода времени: HH:mm")
        String timeChargingBrakeNetwork,

        @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Формат ввода времени: HH:mm")
        String timeIntegrityCheck,

        @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Формат ввода времени: HH:mm")
        String timeFinish,

        @Pattern(regexp = "^\\D$", message = "Фамилия состоит из букв")
        String surnameHeadEmployee,

        @Pattern(regexp = "^\\D$", message = "Фамилия состоит из букв")
        String surnameTailEmployee,

        @Pattern(regexp = "^\\D$", message = "Фамилия состоит из букв")
        String surnameMachinist
) {}
