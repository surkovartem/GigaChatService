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
import org.springframework.web.bind.annotation.RequestBody;
import org.surkov.gigachatservice.dto.hr_analyzer.request.InsightsRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.AnalyzeResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.response.InsightsResponse;

/**
 * Интерфейс для предоставления API для получения аналитических рекомендаций по резюме.
 * Аналитика строится на основе различных аспектов резюме, таких как структура, ясность изложения, стабильность карьерного пути и соответствие вакансии.
 * Результаты агрегируются и предоставляются в виде единого отчета.
 *
 * @author surkov
 */
public interface InsightsApi {

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
     * Получение аналитических рекомендаций.
     * Формирует и возвращает аналитические выводы на основе переданных данных.
     *
     * @param request Объект {@link InsightsRequest}, содержащий параметры для генерации рекомендаций.
     * @return Ответ {@link ResponseEntity}, содержащий объект {@link InsightsResponse} с результатами анализа.
     */
    @Operation(
            summary = "Получение аналитических рекомендаций",
            description = "Формирует аналитические рекомендации на основе результатов анализа резюме."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = SUCCESS_MESSAGE,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnalyzeResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = BAD_REQUEST_MESSAGE,
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = INTERNAL_ERROR_MESSAGE,
                    content = @Content
            )
    })
    @GetMapping(
            value = "/insights",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<InsightsResponse> getInsights(
            @RequestBody
            @Parameter(description = "Параметры для получения рекомендаций")
            InsightsRequest request
    );
}
