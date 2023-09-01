package com.munsun.notifications.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "stations", schema = "wn45")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;
}