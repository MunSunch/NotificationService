package com.munsun.notifications.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of="id")
@Entity
@Table(name = "employees", schema = "wn45")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name="surname", nullable = false)
    String surname;

    @Column(name="patronymic", nullable = true)
    String patronymic;
}