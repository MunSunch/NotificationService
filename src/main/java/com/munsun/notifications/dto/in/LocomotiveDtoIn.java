package com.munsun.notifications.dto.in;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record LocomotiveDtoIn(
        @Pattern(regexp = "^[А-Я0-9]+$", message = "Серия локомотива состоит из символов А-Я и 0-9")
        String series,

        @Positive(message = "Номер локомотива-то положительное число")
        Integer number
) {}