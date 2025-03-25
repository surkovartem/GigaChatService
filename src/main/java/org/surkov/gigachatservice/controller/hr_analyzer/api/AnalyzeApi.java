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
import org.surkov.gigachatservice.dto.hr_analyzer.request.AnalyzeRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.request.ClarityRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.request.HighlightsRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.request.MatchRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.request.StabilityRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.request.StructureRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.AnalyzeResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.response.ClarityResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.response.HighlightsResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.response.MatchResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.response.StabilityResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.response.StructureResponse;

/**
 * Swagger API интерфейс, определяющий конечные точки анализа резюме.
 * Содержит аннотации для генерации документации по каждому эндпоинту.
 *
 * @author surkov
 */
public interface AnalyzeApi {

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
     * Комплексный анализ резюме.
     * Принимает резюме и опционально вакансию, возвращает агрегированные метрики качества резюме.
     *
     * @param request Объект {@link AnalyzeRequest}, содержащий данные для анализа.
     * @return Ответ {@link ResponseEntity}, содержащий объект {@link AnalyzeResponse} с результатами анализа.
     */
    @Operation(
            summary = "Комплексный анализ резюме",
            description = "Агрегированный анализ по структуре, ясности, стабильности, соответствию и достижениям."
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
    @PostMapping(
            value = "/complex",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<AnalyzeResponse> analyze(
            @RequestBody
            @Parameter(description = "Параметры для общего анализа резюме")
            AnalyzeRequest request
    );

    /**
     * Анализ структуры резюме.
     * Проверяет наличие всех ключевых разделов в тексте резюме.
     *
     * @param request Объект {@link StructureRequest}, содержащий данные для анализа структуры.
     * @return Ответ {@link ResponseEntity}, содержащий объект {@link StructureResponse} с результатами анализа структуры.
     */
    @Operation(
            summary = "Анализ структуры резюме",
            description = "Проверяет наличие ключевых разделов в резюме и оценивает полноту структуры."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = SUCCESS_MESSAGE,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StructureResponse.class)
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
            value = "/structure",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<StructureResponse> structure(
            @RequestBody
            @Parameter(description = "Текст резюме для анализа структуры")
            StructureRequest request
    );

    /**
     * Анализ ясности изложения.
     * Находит нечеткие, клишированные фразы и дает рекомендации по их улучшению.
     *
     * @param request Объект {@link ClarityRequest}, содержащий данные для анализа ясности изложения.
     * @return Ответ {@link ResponseEntity}, содержащий объект {@link ClarityResponse} с результатами анализа ясности.
     */
    @Operation(
            summary = "Анализ ясности изложения",
            description = "Выявляет шаблонные и размытые фразы в резюме, оценивает общую четкость текста."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = SUCCESS_MESSAGE,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClarityResponse.class)
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
            value = "/clarity",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<ClarityResponse> clarity(
            @RequestBody
            @Parameter(description = "Текст резюме для анализа ясности")
            ClarityRequest request
    );

    /**
     * Анализ стабильности карьерного пути.
     * Оценивает хронологию карьеры и выявляет потенциальные риски в стабильности.
     *
     * @param request Объект {@link StabilityRequest}, содержащий данные для анализа стабильности карьерного пути.
     * @return Ответ {@link ResponseEntity}, содержащий объект {@link StabilityResponse} с результатами анализа стабильности.
     */
    @Operation(
            summary = "Анализ стабильности карьеры",
            description = "Оценивает стабильность карьерного пути: частоту смен работы, прогресс и риски."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = SUCCESS_MESSAGE,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = StabilityResponse.class)
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
            value = "/stability",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<StabilityResponse> stability(
            @RequestBody
            @Parameter(description = "Текст резюме для анализа стабильности карьеры")
            StabilityRequest request
    );

    /**
     * Проверка соответствия вакансии.
     * Сравнивает резюме с описанием вакансии или желаемой должностью.
     *
     * @param request Объект {@link MatchRequest}, содержащий данные для проверки соответствия вакансии.
     * @return Ответ {@link ResponseEntity}, содержащий объект {@link MatchResponse} с результатами проверки соответствия.
     */
    @Operation(
            summary = "Проверка соответствия вакансии",
            description = "Сравнивает навыки и опыт из резюме с требованиями указанной вакансии."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = SUCCESS_MESSAGE,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MatchResponse.class)
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
            value = "/match",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<MatchResponse> match(
            @RequestBody
            @Parameter(description = "Резюме и вакансия для проверки соответствия")
            MatchRequest request
    );

    /**
     * Выявление ключевых достижений.
     * Извлекает из резюме 2-5 главных достижений кандидата.
     *
     * @param request Объект {@link HighlightsRequest}, содержащий данные для выявления ключевых достижений.
     * @return Ответ {@link ResponseEntity}, содержащий объект {@link HighlightsResponse} с ключевыми достижениями.
     */
    @Operation(
            summary = "Выделение ключевых достижений",
            description = "Извлекает 2-5 главных достижений кандидата из текста резюме."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = SUCCESS_MESSAGE,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = HighlightsResponse.class)
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
            value = "/highlights",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<HighlightsResponse> highlights(
            @RequestBody
            @Parameter(description = "Текст резюме для выделения достижений")
            HighlightsRequest request
    );
}
