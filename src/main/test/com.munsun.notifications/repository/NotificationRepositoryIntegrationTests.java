package com.munsun.notifications.repository;

import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.model.Employee;
import com.munsun.notifications.model.Notification;
import com.munsun.notifications.model.OthersParameters;
import com.munsun.notifications.model.embeddable.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NotificationRepositoryIntegrationTests {
    @Autowired
    private NotificationRepository repository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OthersParametersRepository othersParametersRepository;

    @Test
    public void save() {
        var model = new Notification();
            model.setStation(new StationEmbeddable(62305, 11));
            model.setTimes(new TimeEmbeddable(
                    LocalTime.of(12, 0),
                    LocalTime.of(12, 10),
                    LocalTime.of(12, 15),
                    LocalDateTime.of(2023, 9, 10, 12, 40)));
            model.setLocomotive(new LocomotiveEmbeddable(
                    "ВЛ-80С",
                    1124));
            model.setTrain(new TrainEmdeddable(
                    5804,
                    "грузовой",
                    4555,
                    244,
                    61,
                    "12345678",
                    "87654321"
            ));
            model.setParameters(new ParametersEmbeddable(
                    1333,
                    1700,
                    33,
                    24,
                    100,
                    56,
                    67,
                    0.5,
                    List.of(new OthersParameters(null, "k100"),
                            new OthersParameters(null, "v10")
            )));
            model.setEmployees(new EmployeesEmbeddable(
                    new Employee(null, "головной", "орв", "вагонник"),
                    new Employee(null, "хвостовой", "орв", "вагонник"),
                    new Employee(null, "машинист", "тчм", "машинист")
            ));
            model.setTimeCreate(LocalDateTime.of(2023, 9, 10, 12, 0));

            repository.save(model);

            Assertions.assertTrue(repository.existsById(model.getId()));
    }
}
