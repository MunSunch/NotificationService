package com.munsun.notifications.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TrainDtoOut(
        Integer numberTrain,
        String type,
        Integer weight,
        Integer axes,
        Integer units,
        @JsonProperty("tail")
        String tailWagonNumber,
        @JsonProperty("oncoming")
        String oncomingWagonNumber
) {}