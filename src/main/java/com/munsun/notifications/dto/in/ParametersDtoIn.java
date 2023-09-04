package com.munsun.notifications.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ParametersDtoIn(
        @Positive(message = "Требуемое нажатие-это положительное число")
        Integer pressRequired,

        @Positive(message = "Фактическое нажатие-это положительное число")
        Integer pressActual,

        @Positive(message = "Мин.нажатие-это положительное число")
        Integer minPress,

        @Positive(message = "Требуемое кол-во ручных осей-это положительное число")
        Integer handBrakesRequired,

        @Positive(message = "Фактическое кол-во ручных осей-то положительное число")
        Integer handBrakesActual,

        @Positive(message = "Плотность при 2 положении-это положительное число")
        Integer brakeNetworkWithSecond,

        @Positive(message = "Плотность при 4 положении-это положительное число")
        Integer brakeNetworkWithFourth,

        @Positive(message = "Величина утечки-это положительное число")
        Double brakeNetworkValue,

        @JsonProperty
        List<String> other
) {}