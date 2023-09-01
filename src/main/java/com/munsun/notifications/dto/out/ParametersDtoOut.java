package com.munsun.notifications.dto.out;

import java.util.List;

public record ParametersDtoOut(
        Integer pressRequired,
        Integer pressActual,
        Integer minPress,
        Integer handBrakesRequired,
        Integer handBrakesActual,
        Integer brakeNetworkWithSecond,
        Integer brakeNetworkWithFourth,
        Double brakeNetworkValue,
        List<String> other
) {}