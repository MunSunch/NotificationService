package com.munsun.notifications.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class LocomotiveEmbeddable implements Serializable {
    @Column(name = "locomotive_series", nullable = false)
    private String locomotiveSeries;

    @Column(name = "locomotive_number", nullable = false)
    private Integer locomotiveNumber;
}
