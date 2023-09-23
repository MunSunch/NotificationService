package com.munsun.notifications.model.embeddable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class TimeEmbeddable implements Serializable {
    @Column(name="time_locomotive_trailer")
    private LocalTime timeLocomotiveTrailer;

    @Column(name="time_charge_network")
    private LocalTime timeChargeNetwork;

    @Column(name="time_check_integrity", nullable = false)
    private LocalTime timeCheckIntegrity;

    @Column(name="datetime_finish", nullable = false)
    private LocalDateTime datetimeFinish;
}
