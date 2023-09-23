package com.munsun.notifications.model.embeddable;

import com.munsun.notifications.model.OthersParameters;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class ParametersEmbeddable implements Serializable {
    @Column(name="press_required", nullable = false)
    private Integer pressRequired;
    @Column(name="press_actual", nullable = false)
    private Integer pressActual;
    @Column(name="union_min_press", nullable = false)
    private Integer unionMinPress;
    @Column(name="hand_brakes_required", nullable = false)
    private Integer handBrakesRequired;
    @Column(name="hand_brakes_actual", nullable = false)
    private Integer handBrakesActual;
    @Column(name="density_brake_network_second", nullable = false)
    private Integer densityBrakeNetworkSecond;
    @Column(name="density_brake_network_fourth", nullable = false)
    private Integer densityBrakeNetworkFourth;
    @Column(name="density_brake_network_value", nullable = false)
    private Double densityBrakeNetworkValue;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "notifications_others", schema = "wn45",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name="others_parameters_id")
    )
    private List<OthersParameters> othersParameters;
}
