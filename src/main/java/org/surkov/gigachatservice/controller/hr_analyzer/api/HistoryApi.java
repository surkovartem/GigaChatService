package org.surkov.gigachatservice.controller.hr_analyzer.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.surkov.gigachatservice.dto.hr_analyzer.entry.HistoryEntryDto;

/**
 * Интерфейс для предоставления API для получения истории выполненных анализов резюме.
 * Позволяет пользователям запрашивать историю ранее выполненных анализов резюме, хранящуюся в базе данных.
 * Поддерживаются фильтры по типу анализа и дате.
 *
 * @author surkov
 */
public interface HistoryApi {

    /**
     * Сообщение об успешном выполнении запроса.
     */
    String SUCCESS_MESSAGE = "Запрос выполнен успешно";
    /**
     * Описание ошибки при неверных входных данных.
     */
    String BAD_REQUEST_MESSAGE = "Некорректные входные данные";
    /**
     * Описание внутренней ошибки сервера.
     */
    String INTERNAL_ERROR_MESSAGE = "Внутренняя ошибка сервера";

    /**
     * Получение истории выполненных анализов.
     * Возвращает список сохранённых записей анализа резюме (с возможностью фильтрации по дате и типу операции).
     *
     * @param endpoint Тип конечной точки (эндпоинт), для которой запрашивается история анализа (например, '/chat/match').
     *                 Используется для фильтрации результатов.
     * @param from     Начало временного интервала для фильтрации результатов (в формате ISO 8601).
     * @param to       Окончание временного интервала для фильтрации результатов (в формате ISO 8601).
     * @return Ответ {@link ResponseEntity}, содержащий коллекцию объектов типа {@link HistoryEntryDto},
     * представляющую записи истории анализа резюме.
     */
    @Operation(
            summary = "Получение истории анализов",
            description = "Возвращает историю ранее выполненных анализов резюме, сохраненную в базе данных. Поддерживает фильтрацию по типу анализа и дате."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = SUCCESS_MESSAGE,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HistoryEntryDto[].class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = INTERNAL_ERROR_MESSAGE,
                    content = @Content
            )
    })
    @GetMapping(
            value = "/history",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<Iterable<HistoryEntryDto>> history(
            @RequestParam(name = "endpoint", required = false)
            @Parameter(description = "Фильтр по типу эндпоинта (например, /chat/match)")
            String endpoint,
            @RequestParam(name = "from", required = false)
            @Parameter(description = "Начало диапазона дат (ISO 8601)")
            String from,
            @RequestParam(name = "to", required = false)
            @Parameter(description = "Окончание диапазона дат (ISO 8601)")
            String to
    );
}
