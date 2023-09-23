package com.munsun.notifications.mapping;

import com.munsun.notifications.Container;
import com.munsun.notifications.dto.in.*;
import com.munsun.notifications.dto.out.*;
import com.munsun.notifications.mapping.impl.NotificationMapperImpl;
import com.munsun.notifications.model.*;
import static org.junit.jupiter.api.Assertions.*;

import com.munsun.notifications.model.embeddable.*;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class NotificationMapperIntegrationTests extends Container {
    @Autowired
    private NotificationMapperImpl mapper;

    @RepeatedTest(value = 3)
    public void dto2model() {
        var dto = new NotificationDtoIn(
                62305,
                11,
                new LocomotiveDtoIn("ВЛ-80С", 1124),
                new TrainDtoIn(
                        5804,
                        "грузовой",
                        4555,
                        244,
                        61,
                        "12345678",
                        "87654321"
                ),
                new ParametersDtoIn(
                        1333,
                        1700,
                        33,
                        24,
                        100,
                        56,
                        67,
                        0.5,
                        List.of("К-100", "В10")
                ),
                LocalTime.of(12, 0),
                LocalTime.of(12, 10),
                LocalTime.of(12, 15),
                LocalDateTime.of(2023, 9, 10, 12, 40),

                new EmployeeDtoIn("головной", "орв", "вагонник"),
                new EmployeeDtoIn("хвостовой", "орв", "вагонник"),
                new EmployeeDtoIn("машинист", "тчм", "машинист")
        );

        var expected = new Notification();
            expected.setStation(new StationEmbeddable(62305, 11));
            expected.setTimes(new TimeEmbeddable(
                    LocalTime.of(12, 0),
                    LocalTime.of(12, 10),
                    LocalTime.of(12, 15),
                    LocalDateTime.of(2023, 9, 10, 12, 40)));
            expected.setLocomotive(new LocomotiveEmbeddable(
                    "ВЛ-80С",
                    1124));
            expected.setTrain(new TrainEmdeddable(
                    5804,
                    "грузовой",
                    4555,
                    244,
                    61,
                    "12345678",
                    "87654321"
            ));
            expected.setParameters(new ParametersEmbeddable(
                    1333,
                    1700,
                    33,
                    24,
                    100,
                    56,
                    67,
                    0.5,
                    List.of(new OthersParameters(0, "К-100"),
                            new OthersParameters(0, "В10"))
            ));
            expected.setEmployees(new EmployeesEmbeddable(
                    new Employee(0, "головной", "орв", "вагонник"),
                    new Employee(0, "хвостовой", "орв", "вагонник"),
                    new Employee(0, "машинист", "тчм", "машинист")
            ));

        var actual = mapper.map(dto);
        assertAll(() -> {
            assertNull(actual.getId());
            assertNull(actual.getTimeCreate());
            assertEquals(expected.getStation(), actual.getStation());
            assertEquals(expected.getTimeCreate(), actual.getTimeCreate());
            assertEquals(expected.getLocomotive(), actual.getLocomotive());
            assertEquals(expected.getTimes(), actual.getTimes());
            assertEquals(expected.getTrain(), actual.getTrain());
            assertEquals(expected.getParameters().getPressRequired(), actual.getParameters().getPressRequired());
            assertEquals(expected.getParameters().getPressActual(), actual.getParameters().getPressActual());
            assertEquals(expected.getParameters().getUnionMinPress(), actual.getParameters().getUnionMinPress());
            assertEquals(expected.getParameters().getHandBrakesRequired(), actual.getParameters().getHandBrakesRequired());
            assertEquals(expected.getParameters().getHandBrakesActual(), actual.getParameters().getHandBrakesActual());
            assertEquals(expected.getParameters().getDensityBrakeNetworkSecond(), actual.getParameters().getDensityBrakeNetworkSecond());
            assertEquals(expected.getParameters().getDensityBrakeNetworkFourth(), actual.getParameters().getDensityBrakeNetworkFourth());
            assertEquals(expected.getParameters().getDensityBrakeNetworkValue(), actual.getParameters().getDensityBrakeNetworkValue());
            assertEquals(expected.getParameters().getOthersParameters().stream().map(OthersParameters::getName).collect(Collectors.toList()),
                    actual.getParameters().getOthersParameters().stream().map(OthersParameters::getName).collect(Collectors.toList()));
            assertEquals(expected.getEmployees().getHeadEmployee().getName(),
                    actual.getEmployees().getHeadEmployee().getName());
            assertEquals(expected.getEmployees().getHeadEmployee().getSurname(),
                    actual.getEmployees().getHeadEmployee().getSurname());
            assertEquals(expected.getEmployees().getHeadEmployee().getPatronymic(),
                    actual.getEmployees().getHeadEmployee().getPatronymic());

            assertEquals(expected.getEmployees().getTailEmployee().getName(),
                    actual.getEmployees().getTailEmployee().getName());
            assertEquals(expected.getEmployees().getTailEmployee().getSurname(),
                    actual.getEmployees().getTailEmployee().getSurname());
            assertEquals(expected.getEmployees().getTailEmployee().getPatronymic(),
                    actual.getEmployees().getTailEmployee().getPatronymic());

            assertEquals(expected.getEmployees().getMachinist().getName(),
                    actual.getEmployees().getMachinist().getName());
            assertEquals(expected.getEmployees().getMachinist().getSurname(),
                    actual.getEmployees().getMachinist().getSurname());
            assertEquals(expected.getEmployees().getMachinist().getPatronymic(),
                    actual.getEmployees().getMachinist().getPatronymic());
        });
    }

    @Test
    public void model2dto() {
        var model = new Notification();
            model.setId(1);
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
                    List.of(new OthersParameters(0, "К-100"),
                            new OthersParameters(0, "В10"))
            ));
            model.setEmployees(new EmployeesEmbeddable(
                    new Employee(0, "головной", "орв", "вагонник"),
                    new Employee(0, "хвостовой", "орв", "вагонник"),
                    new Employee(0, "машинист", "тчм", "машинист")
            ));
            model.setTimeCreate(LocalDateTime.of(2023, 9, 10, 12, 0));

        var expected = new NotificationDtoOut(
                model.getId(),
                model.getStation().getCodeStation(),
                model.getStation().getPath(),
                new LocomotiveDtoOut(model.getLocomotive().getLocomotiveSeries(),
                        model.getLocomotive().getLocomotiveNumber()),
                new TrainDtoOut(
                        model.getTrain().getTrainNumber(),
                        model.getTrain().getTrainType(),
                        model.getTrain().getTrainWeight(),
                        model.getTrain().getTrainAxes(),
                        model.getTrain().getTrainUnits(),
                        model.getTrain().getTrainNumberTail(),
                        model.getTrain().getTrainNumberOncoming()
                ),
                new ParametersDtoOut(
                        model.getParameters().getPressRequired(),
                        model.getParameters().getPressActual(),
                        model.getParameters().getUnionMinPress(),
                        model.getParameters().getHandBrakesRequired(),
                        model.getParameters().getHandBrakesActual(),
                        model.getParameters().getDensityBrakeNetworkSecond(),
                        model.getParameters().getDensityBrakeNetworkFourth(),
                        model.getParameters().getDensityBrakeNetworkValue(),
                        model.getParameters().getOthersParameters().stream().map(OthersParameters::getName).collect(Collectors.toList())
                ),
                model.getTimes().getTimeLocomotiveTrailer().toString(),
                model.getTimes().getTimeChargeNetwork().toString(),
                model.getTimes().getTimeCheckIntegrity().toString(),
                model.getTimes().getDatetimeFinish().toString(),
                model.getTimeCreate().toString(),
                new EmployeeDtoOut(model.getEmployees().getHeadEmployee().getName(),
                        model.getEmployees().getHeadEmployee().getSurname(),
                        model.getEmployees().getHeadEmployee().getPatronymic()),
                new EmployeeDtoOut(model.getEmployees().getTailEmployee().getName(),
                        model.getEmployees().getTailEmployee().getSurname(),
                        model.getEmployees().getTailEmployee().getPatronymic()),
                new EmployeeDtoOut(model.getEmployees().getMachinist().getName(),
                        model.getEmployees().getMachinist().getSurname(),
                        model.getEmployees().getMachinist().getPatronymic())
        );

        var actual = mapper.map(model);

        assertEquals(expected, actual);
    }

}
