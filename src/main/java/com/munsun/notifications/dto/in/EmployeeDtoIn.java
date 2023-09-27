package com.munsun.notifications.dto.in;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDtoIn {
    @Pattern(regexp = "^[А-Яа-я]+$", message = "недопустимые символы")
    String name;

    @Pattern(regexp = "^[А-Яа-я]+$", message = "недопустимые символы")
    String surname;

    @Pattern(regexp = "^[А-Яа-я]+$", message = "недопустимые символы")
    String patronymic;
}
