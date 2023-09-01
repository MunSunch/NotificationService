package com.munsun.notifications.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LocomotiveDtoOut(
        @JsonProperty(value = "locomotiveSeries")
        String series,

        @JsonProperty(value = "locomotiveNumber")
        Integer number
) {}