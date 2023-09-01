package com.munsun.notifications.mapping;

import com.munsun.notifications.dto.in.LocomotiveDtoIn;
import com.munsun.notifications.dto.in.NotificationDtoIn;
import com.munsun.notifications.dto.in.ParametersDtoIn;
import com.munsun.notifications.dto.in.TrainDtoIn;
import com.munsun.notifications.dto.out.LocomotiveDtoOut;
import com.munsun.notifications.dto.out.NotificationDtoOut;
import com.munsun.notifications.dto.out.ParametersDtoOut;
import com.munsun.notifications.dto.out.TrainDtoOut;
import com.munsun.notifications.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest(classes = NotificationMapper.class)
public class NotificationMapperUnitTests {
    @Autowired
    private NotificationMapper mapper;

    @Test
    public void notificationDtoIn2notification() {
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
                "12:00",
                "12:10",
                "12:20",
                "12:30",
                "Исимбаев",
                "Сунчаляев",
                "Машинист"
        );
        var expected = new Notification();
            expected.setTrainNumber(1234);
            var temp = new StationPath();
                temp.setPath(12);
                var st = new Station();
                    st.setName("test");
                temp.setStation(st);
                var stc = new StationCode();
                    stc.setNumber("12345");
                temp.setStationCode(stc);
            expected.setStationPath(temp);

            expected.setSurnameHeadEmployee("Исимбаев");
            expected.setSurnameTailEmployee("Сунчаляев");
            expected.setSurnameMachinist("Машинист");

            expected.setTimeLocomotiveTrailer(LocalTime.of(12, 0));
            expected.setTimeChargeNetwork(LocalTime.of(12, 10));
            expected.setTimeCheckIntegrity(LocalTime.of(12,20));
            expected.setTimeFinish(LocalTime.of(12,30));

            expected.setLocomotiveSeries("ВЛ-80С");
            expected.setLocomotiveNumber(1234);

            expected.setTrainType("Грузовой");
            expected.setTrainWeight(1234);
            expected.setTrainAxes(224);
            expected.setTrainUnits(24);
            expected.setTrainNumberTail("12345678");
            expected.setTrainNumberOncoming("23144532");

            expected.setPressRequired(1234);
            expected.setPressActual(1234);
            expected.setUnionMinPress(12);
            expected.setHandBrakesRequired(12);
            expected.setHandBrakesActual(123);
            expected.setDensityBrakeNetworkSecond(12);
            expected.setDensityBrakeNetworkFourth(23);
            expected.setDensityBrakeNetworkValue(0.5);
            var temp1 = new OthersParameters();
                temp1.setName("К-100");
            var temp2 = new OthersParameters();
                temp2.setName("В10");
            expected.setOthersParameters(List.of(temp1, temp2));

        var actual = mapper.map(dto);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void notification2notificationDtoOut() {
        var notification = new Notification();
            notification.setTrainNumber(1234);
            var temp = new StationPath();
            temp.setPath(12);
            var st = new Station();
            st.setName("test");
            temp.setStation(st);
            var stc = new StationCode();
            stc.setNumber("12345");
            temp.setStationCode(stc);
            notification.setStationPath(temp);

            notification.setSurnameHeadEmployee("Исимбаев");
            notification.setSurnameTailEmployee("Сунчаляев");
            notification.setSurnameMachinist("Машинист");

            notification.setTimeLocomotiveTrailer(LocalTime.of(12, 0));
            notification.setTimeChargeNetwork(LocalTime.of(12, 10));
            notification.setTimeCheckIntegrity(LocalTime.of(12,20));
            notification.setTimeFinish(LocalTime.of(12,30));
            notification.setTimeCreate(LocalDateTime.of(2023, 10,8, 19, 40));

            notification.setLocomotiveSeries("ВЛ-80С");
            notification.setLocomotiveNumber(1234);

            notification.setTrainType("Грузовой");
            notification.setTrainWeight(1234);
            notification.setTrainAxes(224);
            notification.setTrainUnits(24);
            notification.setTrainNumberTail("12345678");
            notification.setTrainNumberOncoming("23144532");

            notification.setPressRequired(1234);
            notification.setPressActual(1234);
            notification.setUnionMinPress(12);
            notification.setHandBrakesRequired(12);
            notification.setHandBrakesActual(123);
            notification.setDensityBrakeNetworkSecond(12);
            notification.setDensityBrakeNetworkFourth(23);
            notification.setDensityBrakeNetworkValue(0.5);
            var temp1 = new OthersParameters();
            temp1.setName("К-100");
            var temp2 = new OthersParameters();
            temp2.setName("В10");
            notification.setOthersParameters(List.of(temp1, temp2));

        var expected = new NotificationDtoOut(
                "test",
                12345L,
                12L,
                new LocomotiveDtoOut("ВЛ-80С", 1234),
                new TrainDtoOut("Грузовой", 1234L, 224L, 24L, "12345678", "23144532"),
                new ParametersDtoOut(1234, 1234, 12, 12, 123, 12,23,0.5,
                        List.of("К-100", "В10")),
                "12:00",
                "12:10",
                "12:20",
                "12:30",
                "2023-10-08T19:40"
        );

        var actual = mapper.map(notification);

        Assertions.assertEquals(expected, actual);
    }
}
