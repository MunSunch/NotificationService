package com.munsun.notifications.service;

import com.munsun.notifications.Container;
import com.munsun.notifications.dto.in.*;

import static org.junit.jupiter.api.Assertions.*;

import com.munsun.notifications.dto.out.LocomotiveDtoOut;
import com.munsun.notifications.dto.out.NotificationDtoOut;
import com.munsun.notifications.dto.out.ParametersDtoOut;
import com.munsun.notifications.dto.out.TrainDtoOut;
import com.munsun.notifications.exceptions.NotificationAfterDateException;
import com.munsun.notifications.exceptions.NotificationEqualsFieldException;
import com.munsun.notifications.exceptions.NotificationParametersException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootTest
public class NotificationServiceIntegrationTests extends Container {
    @Autowired
    private NotificationService service;

    @Test
    public void addNotification() {
        var dto = new NotificationDtoIn(
                1234,
                "test",
                12345L,
                12,
                new LocomotiveDtoIn("ВЛ-80С", 1234L),
                new TrainDtoIn("Грузовой", 1234L, 224L, 24L,
                        "12345678", "23144532"),
                new ParametersDtoIn(1234, 1234, 12,
                        12, 123, 12,
                        23, 0.5, List.of("К-100", "В10")),
                LocalTime.of(12, 0),
                LocalTime.of(12, 10),
                LocalTime.of(12, 20),
                LocalTime.of(12, 30),
                "Исимбаев",
                "Сунчаляев",
                "Машинист"
        );

        AtomicLong id = new AtomicLong();
        assertDoesNotThrow(()-> {
                    id.set(service.add(dto).id());
        });

        var expected = new NotificationDtoOut(
                1,
                "test",
                12345L,
                12L,
                new LocomotiveDtoOut("ВЛ-80С", 1234),
                new TrainDtoOut("Грузовой", 1234L, 224L, 24L,
                        "12345678", "23144532"),
                new ParametersDtoOut(1234, 1234, 12,
                        12, 123, 12,
                        23, 0.5, List.of("К-100", "В10")),
                "12:00",
                "12:10",
                "12:20",
                "12:30",
                "",
                "Сунчаляев",
                "Исимбаев",
                "Машинист"
        );
        var actual = service.getById(id.get());

        assertAll(()->{
            assertEquals(expected.id(), actual.id());
            assertEquals(expected.station(), actual.station());
            assertEquals(expected.stationCode(), actual.stationCode());
            assertEquals(expected.pathOnStation(), actual.pathOnStation());
            assertEquals(expected.locomotive(), actual.locomotive());
            assertEquals(expected.train(), actual.train());
            assertEquals(expected.parameters(), actual.parameters());
            assertEquals(expected.timeLocomotiveTrailer(), actual.timeLocomotiveTrailer());
            assertEquals(expected.timeChargingBrakeNetwork(), actual.timeChargingBrakeNetwork());
            assertEquals(expected.timeIntegrityCheck(), actual.timeIntegrityCheck());
            assertEquals(expected.timeFinish(), actual.timeFinish());
            assertNotEquals(expected.timeCreate(), actual.timeCreate());
            assertEquals(expected.surnameHeadEmployee(), actual.surnameHeadEmployee());
            assertEquals(expected.surnameTailEmployee(), actual.surnameTailEmployee());
            assertEquals(expected.surnameMachinist(), actual.surnameMachinist());
        });
    }

    @Test
    public void addNotification_InvalidParametersPress() {
        var dto = new NotificationDtoIn(
                1234,
                "test",
                12345L,
                12,
                new LocomotiveDtoIn("ВЛ-80С", 1234L),
                new TrainDtoIn("Грузовой", 1234L, 224L, 24L,
                        "12345678", "23144532"),
                new ParametersDtoIn(2000, 1234, 12,
                        12, 123, 12,
                        23, 0.5, List.of("К-100", "В10")),
                LocalTime.of(12, 0),
                LocalTime.of(12, 10),
                LocalTime.of(12, 20),
                LocalTime.of(12, 30),
                "Исимбаев",
                "Сунчаляев",
                "Машинист"
        );

        assertThrowsExactly(NotificationParametersException.class, ()-> {
            service.add(dto);
        });
    }

    @Test
    public void addNotification_InvalidParametersHandBrakes() {
        var dto = new NotificationDtoIn(
                1234,
                "test",
                12345L,
                12,
                new LocomotiveDtoIn("ВЛ-80С", 1234L),
                new TrainDtoIn("Грузовой", 1234L, 224L, 24L,
                        "12345678", "23144532"),
                new ParametersDtoIn(1000, 1234, 12,
                        200, 123, 12,
                        23, 0.5, List.of("К-100", "В10")),
                LocalTime.of(12, 0),
                LocalTime.of(12, 10),
                LocalTime.of(12, 20),
                LocalTime.of(12, 30),
                "Исимбаев",
                "Сунчаляев",
                "Машинист"
        );

        assertThrowsExactly(NotificationParametersException.class, ()-> {
            service.add(dto);
        });
    }

    @Test
    public void addNotification_InvalidTime() {
        var dto = new NotificationDtoIn(
                1234,
                "test",
                12345L,
                12,
                new LocomotiveDtoIn("ВЛ-80С", 1234L),
                new TrainDtoIn("Грузовой", 1234L, 224L, 24L,
                        "12345678", "23144532"),
                new ParametersDtoIn(1000, 1234, 12,
                        100, 123, 12,
                        23, 0.5, List.of("К-100", "В10")),
                LocalTime.of(12, 0),
                LocalTime.of(12, 1),
                LocalTime.of(12, 20),
                LocalTime.of(12, 10),
                "Исимбаев",
                "Сунчаляев",
                "Машинист"
        );

        assertThrowsExactly(NotificationAfterDateException.class, ()-> {
            service.add(dto);
        });
    }

    @Test
    public void addNotification_InvalidSurname() {
        var dto = new NotificationDtoIn(
                1234,
                "test",
                12345L,
                12,
                new LocomotiveDtoIn("ВЛ-80С", 1234L),
                new TrainDtoIn("Грузовой", 1234L, 224L, 24L,
                        "12345678", "23144532"),
                new ParametersDtoIn(1000, 1234, 12,
                        100, 123, 12,
                        23, 0.5, List.of("К-100", "В10")),
                LocalTime.of(12, 0),
                LocalTime.of(12, 1),
                LocalTime.of(12, 20),
                LocalTime.of(12, 40),
                "Исимбаев",
                "Сунчаляев",
                "Исимбаев"
        );

        assertThrowsExactly(NotificationEqualsFieldException.class, ()-> {
            service.add(dto);
        });
    }

    @Test
    public void findNotifications_NoResults() {
        var dto = new FindDtoIn();
            dto.setSurnameMachinist("Машинист");
        var actual = service.find(dto);
        assertEquals(0, actual.size());
    }

    @Test
    public void findNotifications() {
        var dto = new NotificationDtoIn(
                1234,
                "test",
                12345L,
                12,
                new LocomotiveDtoIn("ВЛ-80С", 1234L),
                new TrainDtoIn("Грузовой", 1234L, 224L, 24L,
                        "12345678", "23144532"),
                new ParametersDtoIn(1234, 1234, 12,
                        12, 123, 12,
                        23, 0.5, List.of("К-100", "В10")),
                LocalTime.of(12, 0),
                LocalTime.of(12, 10),
                LocalTime.of(12, 20),
                LocalTime.of(12, 30),
                "Исимбаев",
                "Сунчаляев",
                "Машинист"
        );
        service.add(dto);
        service.add(dto);
        service.add(dto);

        var findDtoIn = new FindDtoIn();
            findDtoIn.setSurnameMachinist("Машинист");
        var actual = service.find(findDtoIn);
        assertEquals(3, actual.size());
    }
}























