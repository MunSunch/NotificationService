package com.munsun.notifications.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stations_paths", schema = "wn45")
public class StationPath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "stations_id")
    private Station station;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "station_codes_id")
    private StationCode stationCode;

    @Column(name="path")
    private Integer path;
}
