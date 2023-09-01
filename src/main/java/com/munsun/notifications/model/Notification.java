package com.munsun.notifications.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "notifications", schema = "wn45")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "stations_paths_id")
    private StationPath stationPath;

    @Column(name="time_locomotive_trailer")
    private LocalTime timeLocomotiveTrailer;
    @Column(name="time_charge_network")
    private LocalTime timeChargeNetwork;
    @Column(name="time_check_integrity")
    private LocalTime timeCheckIntegrity;
    @Column(name="time_finish")
    private LocalTime timeFinish;

    @Column(name = "locomotive_number")
    private Integer locomotiveNumber;
    @Column(name = "locomotive_series")
    private String locomotiveSeries;

    @Column(name = "train_number")
    private Integer trainNumber;
    @Column(name="train_type")
    private String trainType;
    @Column(name="train_weight")
    private Integer trainWeight;
    @Column(name="train_axes")
    private Integer trainAxes;
    @Column(name="train_units")
    private Integer trainUnits;
    @Column(name="train_number_tail")
    private String trainNumberTail;
    @Column(name="train_number_oncoming")
    private String trainNumberOncoming;

    @Column(name="press_required")
    private Integer pressRequired;
    @Column(name="press_actual")
    private Integer pressActual;
    @Column(name="union_min_press")
    private Integer unionMinPress;
    @Column(name="hand_brakes_required")
    private Integer handBrakesRequired;
    @Column(name="hand_brakes_actual")
    private Integer handBrakesActual;
    @Column(name="density_brake_network_second")
    private Integer densityBrakeNetworkSecond;
    @Column(name="density_brake_network_fourth")
    private Integer densityBrakeNetworkFourth;
    @Column(name="density_brake_network_value")
    private Double densityBrakeNetworkValue;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "notifications_others_parameters", schema = "wn45",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name="others_parameters_id")
    )
    private List<OthersParameters> othersParameters;

    @Column(name="surname_tail_employee")
    private String surnameTailEmployee;
    @Column(name="surname_head_employee")
    private String surnameHeadEmployee;
    @Column(name="surname_machinist")
    private String surnameMachinist;

    @Column(name="time_create")
    private LocalDateTime timeCreate;
}
