package ru.skypro.test_task.sock.warehouse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.test_task.sock.warehouse.dto.SockDto;
import ru.skypro.test_task.sock.warehouse.entity.Operation;
import ru.skypro.test_task.sock.warehouse.entity.Sock;
import ru.skypro.test_task.sock.warehouse.service.SockService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class SockControllerTest {

    @MockBean
    private SockService sockService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void incomeSocks() throws Exception {
        SockDto sockDto = new SockDto();
        sockDto.setColor("red");
        sockDto.setCottonPart(80);
        sockDto.setQuantity(10);

        mockMvc.perform(post("/api/socks/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sockDto)))
                .andExpect(status().isOk());

        verify(sockService, times(1)).income(any(Sock.class));
    }

    @Test
    public void testOutcomeSocks() throws Exception {
        SockDto sockDto = new SockDto();
        sockDto.setColor("red");
        sockDto.setCottonPart(80);
        sockDto.setQuantity(5);

        mockMvc.perform(post("/api/socks/outcome")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sockDto)))
                .andExpect(status().isOk());

        verify(sockService, times(1)).outcome(any(Sock.class));
    }

    @Test
    public void testGetSocks() throws Exception {
        String color = "red";
        Operation operation = Operation.LESS_THAN;
        int cottonPart = 80;
        String amountSocks = "25";

        when(sockService.countSocks(color, cottonPart, operation)).thenReturn(amountSocks);

        mockMvc.perform(get("/api/socks")
                        .param("color", color)
                        .param("operation", operation.name())
                        .param("cottonPart", String.valueOf(cottonPart)))
                .andExpect(status().isOk())
                .andExpect(content().string(amountSocks));

        verify(sockService, times(1)).countSocks(color, cottonPart, operation);
    }
    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}