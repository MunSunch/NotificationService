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
        String findDtoInJson = "{\"numberTrain\":\"1111\"," +
                "\"surnameHeadEmployee\":\"TestHead\"," +
                "\"surnameTailEmployee\":\"TestTail\"," +
                "\"surnameMachinist\":\"TestMachinist\"," +
                "\"wagonTailNumber\":\"11122333\"," +
                "\"wagonOncomingNumber\":\"44555566\"," +
                "\"startPeriodDate\":\"2023-10-08 12:00\"," +
                "\"endPeriodDate\":\"2023-10-10 12:00\"}";
    }

    @Test
    public void find() throws Exception {
        String findDtoInJson = "{\"numberTrain\":\"1111\"," +
                "\"surnameHeadEmployee\":\"TestHead\"," +
                "\"surnameTailEmployee\":\"TestTail\"," +
                "\"surnameMachinist\":\"TestMachinist\"," +
                "\"wagonTailNumber\":\"11122333\"," +
                "\"wagonOncomingNumber\":\"44555566\"," +
                "\"startPeriodDate\":\"2023-10-08 12:00\"," +
                "\"endPeriodDate\":\"2023-10-10 12:00\"}";

        mockMvc
                .perform(post("/notifications/find")
                        .content(findDtoInJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "12345"})
    public void find_InvalidTrainNumber400(String str) throws Exception {
        String findDtoInJson = "{\"numberTrain\":\"" + str +"\"," +
                "\"surnameHeadEmployee\":\"TestHead\"," +
                "\"surnameTailEmployee\":\"TestTail\"," +
                "\"surnameMachinist\":\"TestMachinist\"," +
                "\"wagonTailNumber\":\"11122333\"," +
                "\"wagonOncomingNumber\":\"44555566\"," +
                "\"startPeriodDate\":\"2023-10-08 12:00\"," +
                "\"endPeriodDate\":\"2023-10-10 12:00\"}";

        var expected = new ErrorWriteRequest("Номер поезда состоит из 4 цифр");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(findDtoInJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "12345"})
    public void find_InvalidSurnameHeadEmployee400(String str) throws Exception {
        String findDtoInJson = "{\"numberTrain\":\"1111\"," +
                "\"surnameHeadEmployee\":\"" + str + "\"," +
                "\"surnameTailEmployee\":\"TestTail\"," +
                "\"surnameMachinist\":\"TestMachinist\"," +
                "\"wagonTailNumber\":\"11122333\"," +
                "\"wagonOncomingNumber\":\"44555566\"," +
                "\"startPeriodDate\":\"2023-10-08 12:00\"," +
                "\"endPeriodDate\":\"2023-10-10 12:00\"}";

        var expected = new ErrorWriteRequest("Фамилия состоит из букв");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(findDtoInJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "12345"})
    public void find_InvalidSurnameTailEmployee400(String str) throws Exception {
        String findDtoInJson = "{\"numberTrain\":\"1111\"," +
                "\"surnameHeadEmployee\":\"TestHead\"," +
                "\"surnameTailEmployee\":\"" + str + "\"," +
                "\"surnameMachinist\":\"TestMachinist\"," +
                "\"wagonTailNumber\":\"11122333\"," +
                "\"wagonOncomingNumber\":\"44555566\"," +
                "\"startPeriodDate\":\"2023-10-08 12:00\"," +
                "\"endPeriodDate\":\"2023-10-10 12:00\"}";
        var expected = new ErrorWriteRequest("Фамилия состоит из букв");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(findDtoInJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "12345"})
    public void find_InvalidSurnameMachinistEmployee400(String str) throws Exception {
        String findDtoInJson = "{\"numberTrain\":\"1111\"," +
                "\"surnameHeadEmployee\":\"TestHead\"," +
                "\"surnameTailEmployee\":\"TestTail\"," +
                "\"surnameMachinist\":\"" + str + "\"," +
                "\"wagonTailNumber\":\"11122333\"," +
                "\"wagonOncomingNumber\":\"44555566\"," +
                "\"startPeriodDate\":\"2023-10-08 12:00\"," +
                "\"endPeriodDate\":\"2023-10-10 12:00\"}";

        var expected = new ErrorWriteRequest("Фамилия состоит из букв");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(findDtoInJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "1234567", "123456789"})
    public void find_InvalidWagonTailNumber400(String str) throws Exception {
        String findDtoInJson = "{\"numberTrain\":\"1111\"," +
                "\"surnameHeadEmployee\":\"TestHead\"," +
                "\"surnameTailEmployee\":\"TestTail\"," +
                "\"surnameMachinist\":\"TestMachinist\"," +
                "\"wagonTailNumber\":\"" + str + "\"," +
                "\"wagonOncomingNumber\":\"44555566\"," +
                "\"startPeriodDate\":\"2023-10-08 12:00\"," +
                "\"endPeriodDate\":\"2023-10-10 12:00\"}";
        var expected = new ErrorWriteRequest("Номер хвостового вагона состоит из 8 цифр");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(findDtoInJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "123", "qwq12", "1234567", "123456789"})
    public void find_InvalidWagonOncomingNumber400(String str) throws Exception {
        String findDtoInJson = "{\"numberTrain\":\"1111\"," +
                "\"surnameHeadEmployee\":\"TestHead\"," +
                "\"surnameTailEmployee\":\"TestTail\"," +
                "\"surnameMachinist\":\"TestMachinist\"," +
                "\"wagonTailNumber\":\"11122333\"," +
                "\"wagonOncomingNumber\":\"" + str + "\"," +
                "\"startPeriodDate\":\"2023-10-08 12:00\"," +
                "\"endPeriodDate\":\"2023-10-10 12:00\"}";
        var expected = new ErrorWriteRequest("Номер встречного вагона состоит из 8 цифр");

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(findDtoInJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(expected)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "qwq12", "2023.22.10", "2023-22-10 12:12:12"})
    public void find_InvalidStartPeriodDate400(String str) throws Exception {
        String findDtoInJson = "{\"numberTrain\":\"1111\"," +
                "\"surnameHeadEmployee\":\"TestHead\"," +
                "\"surnameTailEmployee\":\"TestTail\"," +
                "\"surnameMachinist\":\"TestMachinist\"," +
                "\"wagonTailNumber\":\"11122333\"," +
                "\"wagonOncomingNumber\":\"44555566\"," +
                "\"startPeriodDate\":\"" + str + "\"," +
                "\"endPeriodDate\":\"2023-10-10 12:00\"}";

        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(findDtoInJson))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "qwq12", "2023.22.10", "2023-22-10 12:12:12"})
    public void find_InvalidEndPeriodDate400(String str) throws Exception {
        String findDtoInJson = "{\"numberTrain\":\"1111\"," +
                "\"surnameHeadEmployee\":\"TestHead\"," +
                "\"surnameTailEmployee\":\"TestTail\"," +
                "\"surnameMachinist\":\"TestMachinist\"," +
                "\"wagonTailNumber\":\"11122333\"," +
                "\"wagonOncomingNumber\":\"44555566\"," +
                "\"startPeriodDate\":\"2023-10-08 12:00\"," +
                "\"endPeriodDate\":\"" + str + "\"}";
        mockMvc
                .perform(post("/notifications/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(findDtoInJson))
                .andExpect(status().isBadRequest());
    }
}