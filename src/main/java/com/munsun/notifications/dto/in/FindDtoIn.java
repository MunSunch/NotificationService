package com.munsun.notifications.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindDtoIn {
    @Pattern(regexp = "^\\d{4}$", message = "Номер поезда состоит из 4 цифр")
    private String numberTrain;

    @Pattern(regexp = "^\\D+$", message = "Фамилия состоит из букв")
    private String surnameHeadEmployee;

    @Pattern(regexp = "^\\D+$", message = "Фамилия состоит из букв")
    private String surnameTailEmployee;

    @Pattern(regexp = "^\\D+$", message = "Фамилия состоит из букв")
    private String surnameMachinist;

    @Pattern(regexp = "^\\d{8}$", message = "Номер хвостового вагона состоит из 8 цифр")
    private String wagonTailNumber;

    @Pattern(regexp = "^\\d{8}$", message = "Номер встречного вагона состоит из 8 цифр")
    private String wagonOncomingNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startPeriodDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endPeriodDate;
}
