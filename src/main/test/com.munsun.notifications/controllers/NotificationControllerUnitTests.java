package com.munsun.notifications.controllers;

import com.munsun.notifications.dto.in.*;
import com.munsun.notifications.dto.out.*;
import com.munsun.notifications.model.OthersParameters;
import com.munsun.notifications.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@Import(ObjectMapper.class)
public class NotificationControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NotificationService service;
    @Autowired
    private ObjectMapper mapper;

    private FindDtoIn findDtoIn;
    private NotificationDtoIn notificationDtoIn;

    @BeforeEach
    public void setFindDtoIn() {
        findDtoIn = new FindDtoIn();
            findDtoIn.setNumberTrain("1111");
            findDtoIn.setSurnameHeadEmployee("TestHead");
            findDtoIn.setSurnameTailEmployee("TestTail");
            findDtoIn.setSurnameMachinist("TestMachinist");
            findDtoIn.setStartPeriodDate(LocalDateTime.of(2023, 8, 22, 12, 0));
            findDtoIn.setEndPeriodDate(LocalDateTime.of(2023, 8, 24, 12, 0));
            findDtoIn.setWagonOncomingNumber("11122333");
            findDtoIn.setWagonTailNumber("44555566");

        notificationDtoIn = new NotificationDtoIn(
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
                "13:00",
                "Исимбаев",
                "Сунчаляев"
        );
    }

    @Test
    public void find() throws Exception {
        mockMvc
                .perform(post("/notifications/find")
                        .content(mapper.writeValueAsString(findDtoIn))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "12345"})
    public void find_InvalidTrainNumber400(String str) throws Exception {
        findDtoIn.setNumberTrain(str);
        var expected = new ErrorWriteRequest("Номер поезда состоит из 4 цифр");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(findDtoIn)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "12345"})
    public void find_InvalidSurnameHeadEmployee400(String str) throws Exception {
        findDtoIn.setSurnameHeadEmployee(str);
        var expected = new ErrorWriteRequest("Фамилия состоит из букв");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(findDtoIn)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "12345"})
    public void find_InvalidSurnameTailEmployee400(String str) throws Exception {
        findDtoIn.setSurnameTailEmployee(str);
        var expected = new ErrorWriteRequest("Фамилия состоит из букв");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(findDtoIn)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "12345"})
    public void find_InvalidSurnameMachinistEmployee400(String str) throws Exception {
        findDtoIn.setSurnameTailEmployee(str);
        var expected = new ErrorWriteRequest("Фамилия состоит из букв");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(findDtoIn)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "1234567", "123456789"})
    public void find_InvalidWagonTailNumber400(String str) throws Exception {
        findDtoIn.setWagonTailNumber(str);
        var expected = new ErrorWriteRequest("Номер хвостового вагона состоит из 8 цифр");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(findDtoIn)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "1234567", "123456789"})
    public void find_InvalidWagonOncomingNumber400(String str) throws Exception {
        findDtoIn.setWagonOncomingNumber(str);
        var expected = new ErrorWriteRequest("Номер встречного вагона состоит из 8 цифр");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(findDtoIn)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "2023.22.10", "2023-22-10 12:12:12"})
    public void find_InvalidStartPeriodDate400(String str) throws Exception {
//        findDtoIn.setStartPeriodDate(str);
        var expected = new ErrorWriteRequest("Формат ввода даты: YYYY-DD-MM HH:mm");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(findDtoIn)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "2023.22.10", "2023-22-10 12:12:12"})
    public void find_InvalidEndPeriodDate400(String str) throws Exception {
//        findDtoIn.setEndPeriodDate(str);
        var expected = new ErrorWriteRequest("Формат ввода даты: YYYY-DD-MM HH:mm");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(findDtoIn)))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @Test
    public void save() throws Exception {
        mockMvc
                .perform(post("/notifications/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(notificationDtoIn)))
                .andExpect(status().isOk());
    }
}
