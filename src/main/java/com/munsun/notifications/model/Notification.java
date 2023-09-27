package com.munsun.notifications.model;

import com.munsun.notifications.model.embeddable.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "notifications", schema = "wn45")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private StationEmbeddable station;

    @Embedded
    private TimeEmbeddable times;

    @Embedded
    private LocomotiveEmbeddable locomotive;

    @Embedded
    private TrainEmdeddable train;

    @Embedded
    private ParametersEmbeddable parameters;

    @Embedded
    private EmployeesEmbeddable employees;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="time_create")
    private LocalDateTime timeCreate;
}
