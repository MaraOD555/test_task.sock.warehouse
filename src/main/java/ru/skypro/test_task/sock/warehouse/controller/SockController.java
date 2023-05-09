package ru.skypro.test_task.sock.warehouse.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.test_task.sock.warehouse.dto.SockDto;
import ru.skypro.test_task.sock.warehouse.entity.Operation;
import ru.skypro.test_task.sock.warehouse.mapping.SockMapper;
import ru.skypro.test_task.sock.warehouse.service.SockService;

/**
 * Контроллер для работы с запросами
 */

@RestController
@RequestMapping("/api/socks")
@RequiredArgsConstructor
public class SockController {
    private final SockService sockService;
    private final SockMapper sockMapper;

    @io.swagger.v3.oas.annotations.Operation(
            summary = "incomeSocks",
            description = "Добавление носков на склад",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Добавление пар носков на склад",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SockDto.class)
                    )),

            responses = {
                    @ApiResponse(responseCode = "200", description = "удалось добавить приход"),
                    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат"),
                    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны (например, база данных недоступна")
            })

    @PostMapping("/income")
    public ResponseEntity incomeSocks(@RequestBody SockDto sockDto) {
        sockService.income(sockMapper.toEntity(sockDto));
        return ResponseEntity.ok().build();
    }

    @io.swagger.v3.oas.annotations.Operation(
            summary = "outcomeSocks",
            description = "Отпуск носков со склада",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Отпуск пар носков со склада",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SockDto.class)
                    )),

            responses = {
                    @ApiResponse(responseCode = "200", description = "удалось отпустить носки"),
                    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат"),
                    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны (например, база данных недоступна")
            })
    @PostMapping("/outcome")
    public ResponseEntity outcomeSocks(@RequestBody SockDto sockDto) {
        sockService.outcome(sockMapper.toEntity(sockDto));
        return ResponseEntity.ok().build();
    }

    @io.swagger.v3.oas.annotations.Operation(
            summary = "getSocks",
            description = "Возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса",
            responses = {
                    @ApiResponse(responseCode = "200", description = "запрос выполнен, результат в теле ответа в виде строкового представления целого числа;"),
                    @ApiResponse(responseCode = "400", description = "параметры запроса отсутствуют или имеют некорректный формат"),
                    @ApiResponse(responseCode = "500", description = "произошла ошибка, не зависящая от вызывающей стороны (например, база данных недоступна)")
            })
    @GetMapping("")
    public ResponseEntity<String> getSocks(@RequestParam(value = "color") String color,
                                           @RequestParam(value = "operation") Operation operation,
                                           @RequestParam(value = "cottonPart") int cottonPart) {
        String amountSocks = sockService.countSocks(color, cottonPart, operation);
        return ResponseEntity.ok(amountSocks);
    }

}
