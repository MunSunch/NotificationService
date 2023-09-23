package com.munsun.notifications.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindDtoIn {
    private String numberTrain;
    private EmployeeDtoIn headEmployee;
    private EmployeeDtoIn tailEmployee;
    private EmployeeDtoIn machinist;
    private String wagonTailNumber;
    private String wagonOncomingNumber;
    private LocalDateTime startPeriodDate;
    private LocalDateTime endPeriodDate;
}
