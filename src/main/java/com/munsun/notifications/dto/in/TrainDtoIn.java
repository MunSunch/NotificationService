package com.munsun.notifications.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record TrainDtoIn(
        @Positive(message = "Номер поезда - это положительное число")
        Integer numberTrain,

        @Pattern(regexp = "^[А-Яа-я]+$")
        String type,

        @Positive(message = "Вес-это положительное число")
        Integer weight,

        @Positive(message = "Кол-во осей-это положительное число")
        Integer axes,

        @Positive(message = "Кол-во единиц-это положительное число")
        Integer units,

        @Pattern(regexp = "^\\d{8}$")
        @JsonProperty("tail")
        String tailWagonNumber,

        @Pattern(regexp = "^\\d{8}$")
        @JsonProperty("onComing")
        String onComingWagonNumber
) {}
