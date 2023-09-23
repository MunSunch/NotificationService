package com.munsun.notifications.model.embeddable;

import com.munsun.notifications.model.Employee;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class EmployeesEmbeddable implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tail_employee_id")
    private Employee tailEmployee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "head_employee_id")
    private Employee headEmployee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "machinist_id")
    private Employee machinist;
}
