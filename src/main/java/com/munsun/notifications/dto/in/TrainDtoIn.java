package com.munsun.notifications.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record TrainDtoIn(
        @Pattern(regexp = "^\\w+$")
        String type,

        @Positive(message = "Вес-это положительное число")
        Long weight,

        @Positive(message = "Кол-во осей-это положительное число")
        Long axes,

        @Positive(message = "Кол-во единиц-это положительное число")
        Long units,

        @Pattern(regexp = "^\\d{8}$")
        @JsonProperty("tail")
        String tailWagonNumber,

        @Pattern(regexp = "^\\d{8}$")
        @JsonProperty("onComing")
        String onComingWagonNumber
) {}
