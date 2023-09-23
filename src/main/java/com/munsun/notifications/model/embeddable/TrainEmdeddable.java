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
public class TrainEmdeddable implements Serializable {
    @Column(name = "train_number", nullable = false)
    private Integer trainNumber;

    @Column(name="train_type", nullable = false)
    private String trainType;

    @Column(name="train_weight", nullable = false)
    private Integer trainWeight;

    @Column(name="train_axes", nullable = false)
    private Integer trainAxes;

    @Column(name="train_units", nullable = false)
    private Integer trainUnits;

    @Column(name="train_number_tail", nullable = false)
    private String trainNumberTail;

    @Column(name="train_number_oncoming")
    private String trainNumberOncoming;
}
