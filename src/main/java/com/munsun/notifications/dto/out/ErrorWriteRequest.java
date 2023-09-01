package com.munsun.notifications.dto.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorWriteRequest {
    private String message;
}
