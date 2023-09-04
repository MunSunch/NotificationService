package com.munsun.notifications.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalTime;

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

        @JsonFormat(pattern = "HH:mm")
//        @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Формат ввода времени: HH:mm")
        LocalTime timeLocomotiveTrailer,

        @JsonFormat(pattern = "HH:mm")
//        @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Формат ввода времени: HH:mm")
        LocalTime timeChargingBrakeNetwork,

        @JsonFormat(pattern = "HH:mm")
//        @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Формат ввода времени: HH:mm")
        LocalTime timeIntegrityCheck,

        @JsonFormat(pattern = "HH:mm")
//        @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Формат ввода времени: HH:mm")
        LocalTime timeFinish,

        @Pattern(regexp = "^\\D$", message = "Фамилия состоит из букв")
        String surnameHeadEmployee,

        @Pattern(regexp = "^\\D$", message = "Фамилия состоит из букв")
        String surnameTailEmployee,

        @Pattern(regexp = "^\\D$", message = "Фамилия состоит из букв")
        String surnameMachinist
) {}
