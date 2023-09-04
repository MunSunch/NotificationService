package com.munsun.notifications.repository;

import com.munsun.notifications.Container;
import com.munsun.notifications.dto.in.FindDtoIn;
import com.munsun.notifications.model.*;
import com.munsun.notifications.service.impl.spec.NotificationSpecification;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NotificationTests extends Container {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private StationPathRepository stationPathRepository;
    @Autowired
    private OthersParametersRepository othersParametersRepository;

    private Notification notification;

    @BeforeEach
    public void setNotification() {
        var stationPath = new StationPath();
            var code = new StationCode();
                code.setNumber("62550");
            stationPath.setStationCode(code);
            var station = new Station();
                station.setName("Саратов-2");
            stationPath.setStation(station);
            stationPath.setPath(11);
        int id = stationPathRepository.save(stationPath).getId();

        var k100 = new OthersParameters();
            k100.setId(0);
            k100.setName("K-100");
        var v10 = new OthersParameters();
            v10.setId(0);
            v10.setName("В10");
        k100 = othersParametersRepository.save(k100);
        v10 = othersParametersRepository.save(v10);

        notification = new Notification();
            notification.setStationPath(stationPathRepository.getReferenceById(id));

            notification.setLocomotiveSeries("ВЛ-80С");
            notification.setLocomotiveNumber(1122);

            notification.setTrainType("Грузовой");
            notification.setTrainNumber(5644);
            notification.setTrainAxes(244);
            notification.setTrainWeight(5500);
            notification.setTrainUnits(65);
            notification.setTrainNumberTail("11122233");
            notification.setTrainNumberOncoming("44455666");

            notification.setPressRequired(1333);
            notification.setPressActual(1555);
            notification.setHandBrakesRequired(24);
            notification.setHandBrakesActual(104);
            notification.setUnionMinPress(33);
            notification.setDensityBrakeNetworkSecond(67);
            notification.setDensityBrakeNetworkFourth(77);
            notification.setDensityBrakeNetworkValue(0.5);
            notification.setOthersParameters(List.of(k100, v10));

            notification.setTimeLocomotiveTrailer(LocalTime.of(16, 0));
            notification.setTimeCheckIntegrity(LocalTime.of(16,10));
            notification.setTimeChargeNetwork(LocalTime.of(16, 5));
            notification.setTimeFinish(LocalTime.of(16, 40));
            notification.setTimeCreate(LocalDateTime.now());

            notification.setSurnameHeadEmployee("Исимбаев");
            notification.setSurnameTailEmployee("Сунчаляев");
            notification.setSurnameMachinist("Царёв");
    }

//    @Transactional
    @Test
    public void createNotification() {
        var actualNotificationId = notificationRepository.save(notification).getId();
        var actual = notificationRepository.getReferenceById(actualNotificationId.longValue());
        assertNotNull(actual);
    }

    @Test
    public void deleteNotificationById() {
        var id = notificationRepository.save(notification).getId();
        notificationRepository.deleteById(id.longValue());

        assertThrowsExactly(JpaObjectRetrievalFailureException.class, () -> {
            notificationRepository.getReferenceById(id.longValue());
        });
    }

    @Test
    public void findNotificationBySurnameHeadEmployee() {
        notificationRepository.save(notification);
        var actual =
                notificationRepository.findNotificationBySurnameHeadEmployee(notification.getSurnameHeadEmployee());

        assertTrue(actual.isPresent());
    }

    @Test
    public void findNotificationBySurnameHeadEmployee_NotExistsEmployee() {
        notificationRepository.save(notification);
        var actual =
                notificationRepository.findNotificationBySurnameHeadEmployee("test");

        assertTrue(actual.isEmpty());
    }

    @Test
    public void findNotificationBySurnameTailEmployee() {
        notificationRepository.save(notification);
        var actual =
                notificationRepository.findNotificationBySurnameTailEmployee(notification.getSurnameTailEmployee());

        assertTrue(actual.isPresent());
    }

    @Test
    public void findNotificationBySurnameTailEmployee_NotExistsEmployee() {
        notificationRepository.save(notification);
        var actual =
                notificationRepository.findNotificationBySurnameTailEmployee("test");

        assertTrue(actual.isEmpty());
    }

    @Test
    public void findNotificationBySurnameMachinist() {
        notificationRepository.save(notification);
        var actual =
                notificationRepository.findNotificationBySurnameMachinist(notification.getSurnameMachinist());

        assertTrue(actual.isPresent());
    }

    @Test
    public void findNotificationBySurnameMachinist_NotExistsEmployee() {
        notificationRepository.save(notification);
        var actual =
                notificationRepository.findNotificationBySurnameMachinist("test");

        assertTrue(actual.isEmpty());
    }

    @Test
    public void findNotificationByTrainNumber() {
        notificationRepository.save(notification);
        var actual =
                notificationRepository.findNotificationByTrainNumber(notification.getTrainNumber());

        assertTrue(actual.isPresent());
    }

    @Test
    public void findNotificationByTrainNumber_NotExistsTrain() {
        notificationRepository.save(notification);
        var actual =
                notificationRepository.findNotificationByTrainNumber(1111);

        assertTrue(actual.isEmpty());
    }

    @Test
    public void updateNotification() {
        var temp = notificationRepository.save(notification);
        temp.setTrainNumber(1010);
        var actual = notificationRepository.getReferenceById(temp.getId().longValue());
        assertEquals(1010, actual.getTrainNumber());
    }

    @Test
    public void findAllByTrainNumber() {
        var dto = new FindDtoIn();
            dto.setNumberTrain(String.valueOf(notification.getTrainNumber()));
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(1, actual.size());
    }

    @Test
    public void findAllByTrainNumber_IsNotExists() {
        var dto = new FindDtoIn();
            dto.setNumberTrain("98765");
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(0, actual.size());
    }

    @Test
    public void findAllByWagonTailNumber() {
        var dto = new FindDtoIn();
            dto.setWagonTailNumber(String.valueOf(notification.getTrainNumberTail()));
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(1, actual.size());
    }

    @Test
    public void findAllByWagonTailNumber_IsNotExists() {
        var dto = new FindDtoIn();
            dto.setWagonTailNumber("99988877");
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(0, actual.size());
    }

    @Test
    public void findAllByWagonOncomingNumber() {
        var dto = new FindDtoIn();
            dto.setWagonOncomingNumber(String.valueOf(notification.getTrainNumberOncoming()));
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(1, actual.size());
    }

    @Test
    public void findAllByWagonOncomingNumber_IsNotExists() {
        var dto = new FindDtoIn();
            dto.setWagonOncomingNumber("99988777");
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(0, actual.size());
    }

    @Test
    public void findAllBySurnameHeadEmployee() {
        var dto = new FindDtoIn();
            dto.setSurnameHeadEmployee(String.valueOf(notification.getSurnameHeadEmployee()));
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(1, actual.size());
    }

    @Test
    public void findAllBySurnameHeadEmployee_IsNotExists() {
        var dto = new FindDtoIn();
            dto.setSurnameHeadEmployee("test");
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(0, actual.size());
    }

    @Test
    public void findAllBySurnameTailEmployee() {
        var dto = new FindDtoIn();
            dto.setSurnameTailEmployee(String.valueOf(notification.getSurnameTailEmployee()));
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(1, actual.size());
    }

    @Test
    public void findAllBySurnameTailEmployee_IsNotExists() {
        var dto = new FindDtoIn();
            dto.setSurnameTailEmployee("test");
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(0, actual.size());
    }

    @Test
    public void findAllBySurnameMachinist() {
        var dto = new FindDtoIn();
            dto.setSurnameMachinist(String.valueOf(notification.getSurnameMachinist()));
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(1, actual.size());
    }

    @Test
    public void findAllBySurnameMachinist_IsNotExists() {
        var dto = new FindDtoIn();
            dto.setSurnameMachinist("test");
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(0, actual.size());
    }

    @Test
    public void findAllByStartPeriodDate() {
        var dto = new FindDtoIn();
            dto.setStartPeriodDate(notification.getTimeCreate().minusMonths(2));
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(1, actual.size());
    }

    @Test
    public void findAllByStartPeriodDate_IsNotExists() {
        var dto = new FindDtoIn();
            dto.setStartPeriodDate(notification.getTimeCreate().plusMonths(2));
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(0, actual.size());
    }

    @Test
    public void findAllByEndPeriodDate() {
        var dto = new FindDtoIn();
            dto.setEndPeriodDate(notification.getTimeCreate().plusMonths(2));
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(1, actual.size());
    }

    @Test
    public void findAllByEndPeriodDate_IsNotExists() {
        var dto = new FindDtoIn();
            dto.setEndPeriodDate(notification.getTimeCreate().minusMonths(2));
        notificationRepository.save(notification);

        var actual = notificationRepository.findAll(NotificationSpecification.getSpecEquals(dto));
        assertEquals(0, actual.size());
    }
}


















