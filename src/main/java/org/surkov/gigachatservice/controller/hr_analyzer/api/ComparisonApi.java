package org.surkov.gigachatservice.controller.hr_analyzer.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.surkov.gigachatservice.dto.hr_analyzer.response.CompareRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.CompareResponse;

/**
 * Интерфейс для предоставления API для сравнения двух резюме кандидатов.
 * Позволяет пользователям отправлять два резюме для анализа и получать результаты сравнения.
 * Операции реализуются с использованием аннотаций Swagger для описания API.
 *
 * @author surkov
 */
public interface ComparisonApi {

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
     * Сравнение двух резюме.
     * Сравнивает профили двух кандидатов и определяет, кто более предпочтителен.
     *
     * @param request Объект {@link CompareRequest}, содержащий данные для сравнения двух резюме.
     * @return Ответ {@link ResponseEntity}, содержащий объект {@link CompareResponse} с результатом сравнения.
     */
    @Operation(
            summary = "Сравнение двух резюме",
            description = "Сравнивает профили двух кандидатов и возвращает структурированное сравнение по ключевым параметрам."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = SUCCESS_MESSAGE,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CompareResponse.class)
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
    @PostMapping(
            value = "/compare",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<CompareResponse> compare(
            @RequestBody
            @Parameter(description = "Данные двух резюме (и опционально вакансии) для сравнения")
            CompareRequest request
    );
}
