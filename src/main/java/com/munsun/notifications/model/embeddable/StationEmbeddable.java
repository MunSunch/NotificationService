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
public class StationEmbeddable implements Serializable {
    @Column(name="station_code", nullable = false)
    private Integer codeStation;

    @Column(name = "path", nullable = false)
    private Integer path;
}
